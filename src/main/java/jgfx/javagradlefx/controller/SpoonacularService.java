package jgfx.javagradlefx.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import jgfx.javagradlefx.model.Preference;
import jgfx.javagradlefx.model.RecipeDetails;
import jgfx.javagradlefx.model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import jgfx.javagradlefx.model.Recipe;


public class SpoonacularService {

    private JsonRequestHandler jsonRequestHandler = new JsonRequestHandler();
    private String key = "e8a0cb265d1d4ab79427f52aba817b54";
    private String prefix = "https://api.spoonacular.com/recipes/";

    // Cette méthode permet d'ouvrire une connexion avec l'API de spoonacular
    public StringBuilder connexion(URL url) {
        try {
            // Open a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();


            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Close the connections
            in.close();
            connection.disconnect();
            return  content;
        } catch (RuntimeException e) {
            throw new RuntimeException("Il y a un problème au niveau du query de la fonction connexion");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // Requetes pour récupere les recettes d'apres les recommandations du client
    public List<Recipe> getRecipe(String query) throws RuntimeException {
        try {

            String complex = "complexSearch";

            // intégrer le nombre de recette à retourner
            String number = "&number=24";

            //URL url = new URL("https://api.spoonacular.com/recipes/complexSearch?apiKey=f983acdf88c24b66b7705299529f9032&query=pasta");
            URL url = new URL(prefix + complex + "?apiKey=" + key + "&query=" + URLEncoder.encode(query, "UTF-8") + number);
            // Convert the response to a JSON object
            JSONObject obj = new JSONObject(connexion(url).toString());

            return jsonRequestHandler.jsonToRecipe(obj);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("IL y a un problème au niveau du query de la fonction getRecipe");
        }
    }

    // Requete pour récupérer des recettes d'après les préférences du client
    public List<Recipe> getRecipeByPrefs(String regime, List<String> intolerancesAlimentaires) throws RuntimeException{
        try {
            // intégrer le paramètre de régime alimentaire
            String diet = "";
            if (regime != "") {
                diet = "&diet="+regime;
            }

            // intégrer le paramètre d'intolérances alimentaires
            String intolerances = "";
            if (intolerancesAlimentaires.size() > 0) {
                intolerances = "&intolerances=";
                for (String intolerance : intolerancesAlimentaires) {
                    intolerances += intolerance + ",";
                }
                intolerances = intolerances.substring(0, intolerances.length() - 1);
            }

            // intégrer le nombre de recette à retourner
            String number = "&number=24";

            // Création de l'URL
            String request = prefix + "complexSearch?apiKey=" + key + diet + intolerances+ number;
            URL url = new URL(request);

            JSONObject obj = new JSONObject(connexion(url).toString());
            return jsonRequestHandler.jsonToRecipe(obj);
        } catch (Exception e) {
            e.printStackTrace();

            throw  new RuntimeException("Il y a un problème au niveau du query de la fonction getRecipeByPrefs");
        }
    }

    // Requetes pour récuprérer une recette de manière plus détaillé
    public JSONObject getRecipeInformation(Long id) throws RuntimeException {
        try {
            // Création de l'URL
            URL url = new URL(prefix + id + "/information?apiKey=" + key);

            JSONObject obj = new JSONObject(connexion(url).toString());

            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Il y a un problème au niveau du query de la fonction getRecipeDetails");
        }
    }

    // Requete pour récupérer  les instructions d'une recette de manière plus detaillée
    public JSONArray getAnalyzedRecipeInfomation(Long id) throws RuntimeException {
        try {
            // Création de l'URL
            URL url = new URL(prefix + id + "/analyzedInstructions?apiKey=" + key);

            JSONArray instructionsArray = new JSONArray(connexion(url).toString());
            return instructionsArray;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Il y a un problème au niveau du query de la fonction getAnalyzedRecipeInfomation");
        }
    }

    //Methode pour récupérer les informations des ingredients
    public JSONObject getIngredientById(Long Id) {
        try {
            // Création de l'URL
            URL url = new URL(prefix + Id + "/ingredientWidget.json?apiKey=" + key);
            JSONObject obj = new JSONObject(connexion(url).toString());
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Il y a un problème au niveau du query de la fonction getIngredientById");
        }

    }

    // Methode pour récupérer les informations récupérer les apports nutritionnels des plats
    public JSONObject getNutritionInfo(Long id) {
        try {
            // Création de l'URL
            URL url = new URL(prefix + id + "/nutritionWidget.json?apiKey=" + key);
            JSONObject obj = new JSONObject(connexion(url).toString());
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Il y a un problème au niveau du query de la fonction getNutritionInfo");
        }
    }

    // Méthode pour concevoir la recette detaillée à partir des deux requêtes
    public RecipeDetails getRecipeInfo(long id) {
        JSONObject obj = getRecipeInformation(id);
        JSONArray array = getAnalyzedRecipeInfomation(id);
        Map<String , List<String>> stepsAndIngredients = jsonRequestHandler.extractStepsAndIngredients(array);
        JSONObject ingredientsObj = getIngredientById(id);
        JSONObject nutritionObj = getNutritionInfo(id);
        return jsonRequestHandler.jsonToRecipeInfo(obj, stepsAndIngredients, ingredientsObj, nutritionObj);
    }

    public JSONObject getRepcipeByUrl(String url){
        try {
            URL finalUrl = new URL("https://api.spoonacular.com/recipes/complexSearch?apiKey=" + key + url + "&number=24");
            JSONObject obj = new JSONObject(connexion(finalUrl).toString());
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Il y a un problème au niveau du query de la fonction getRepcipeByUrl");
        }
    }
}