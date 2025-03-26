package jgfx.javagradlefx.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import jgfx.javagradlefx.model.Preference;
import jgfx.javagradlefx.model.Utilisateur;
import org.json.JSONObject;
import jgfx.javagradlefx.model.Recette;

import javax.swing.*;


public class SpoonacularService {
    private String key = "4b292240b23a42c89c878c3baf1ececd";
    private String prefix = "https://api.spoonacular.com/recipes/";

    public void essaie() {
        try {
            // Create a URL object with the API endpoint
            URL url = new URL("https://api.spoonacular.com/recipes/complexSearch?apiKey=4b292240b23a42c89c878c3baf1ececd");

            // Open a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

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

            // Convert the response to a JSON object
            JSONObject obj = new JSONObject(content.toString());

            // Print the response
            System.out.println("essaie requete complexSearch sans parametre");
            System.out.println(obj.getJSONArray("results"));
            System.out.println("Nombre de recettes retournées: " + obj.getJSONArray("results").length() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Recette> JsonResultsToRecettes(JSONObject obj) {
        List<Recette> recettes = new ArrayList<Recette>();
        for (int i = 0; i < obj.getJSONArray("results").length(); i++) {
            JSONObject recette = obj.getJSONArray("results").getJSONObject(i);
            Recette rec = new Recette(recette.getLong("id"), recette.getString("title"), recette.getString("image"));
            recettes.add(rec);
        }
        return recettes;
    }

    public List<Recette> getRecipeByPrefs(String regime, List<String> intolerancesAlimentaires) {
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
            String number = "&number=5";

            // Création de l'URL
            String request = prefix + "complexSearch?apiKey=" + key + diet + intolerances+ number;
            URL url = new URL(request);

            // Ouverture de connexion et définition de la méthode de requete
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Lecture de la réponse et conversion en objet JSON
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            JSONObject obj = new JSONObject(content.toString());

            System.out.println(obj.getJSONArray("results"));
            System.out.println("Nombre de recettes retournées: " + obj.getJSONArray("results").length() + "\n");

            return JsonResultsToRecettes(obj);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public void testRecipeByPreference(){
        Utilisateur utilisateur = new Utilisateur(1L, "John");
        Preference pref1L = new Preference(1L);
        pref1L.setRegimeAlimentaire("omnivore");
        pref1L.ajouterIntoleranceAlimentaire("gluten");
        pref1L.ajouterIntoleranceAlimentaire("dairy");
        utilisateur.mettreAJourPreference(pref1L);

        System.out.println("Essaie requete complexSearch avec les préférences (diet, intolerances)");
        List<Recette> recommendation = getRecipeByPrefs(utilisateur.getPreference().getRegimeAlimentaire(), utilisateur.getPreference().getIntolerancesAlimentaires());
        System.out.println(recommendation.toString());

    }

}