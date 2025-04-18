package jgfx.javagradlefx.controller;

import jgfx.javagradlefx.model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestionnaire pour les requêtes JSON liées aux recettes et utilisateurs.
 * Fournit des méthodes pour transformer des objets JSON en modèles Java et gérer les données utilisateur.
 *
 * Méthodes principales :
 * - `jsonToRecipe(JSONObject obj)` : Convertit un objet JSON en liste de recettes.
 * - `jsonToRecipeInfo(JSONObject obj, Map<String, List<String>> stepsObj, JSONObject ingredientsObj, JSONObject nutrientsObj)` :
 *   Transforme un objet JSON en détails de recette.
 * - `extractStepsAndIngredients(JSONArray instructionsArray)` : Extrait les étapes et ingrédients d'un tableau JSON.
 * - `jsonToIngredients(JSONObject obj, Long id)` : Convertit un objet JSON en liste d'ingrédients.
 * - `jsonToNutrient(JSONObject obj, long id)` : Convertit un objet JSON en liste de nutriments.
 * - `chargerUtilisateur()` : Charge les données utilisateur depuis un fichier JSON.
 * - `modifierFichierUtilisateur(User user)` : Sauvegarde les données utilisateur dans un fichier JSON.
 * - `reinitialiserFichierUtilisateur()` : Réinitialise les données utilisateur avec des valeurs par défaut.
 *
 * Exceptions :
 * - Lance une `RuntimeException` en cas d'erreur de lecture/écriture ou de format JSON invalide.
 */

public class JsonRequestHandler {

    private static final String USER_FILE_PATH = "src/main/resources/data/User.json";

    // Transforme l'obejt JSON en liste de recettes
    public List<Recipe> jsonToRecipe(JSONObject obj) {
        List<Recipe> recipes = new ArrayList<>();
        for (int i = 0; i < obj.getJSONArray("results").length(); i++) {
            JSONObject recette = obj.getJSONArray("results").getJSONObject(i);
            Recipe rec = new Recipe(recette.getLong("id"), recette.getString("title"), recette.getString("image"));
            recipes.add(rec);
        }
        return recipes;
    }

    // Transforme l'objet JSON en RecetteInfo
    public RecipeDetails jsonToRecipeInfo(JSONObject obj, Map<String, List<String>> stepsObj, JSONObject ingredientsObj, JSONObject nutrientsObj) {
        Long id = obj.getLong("id");
        int portion = obj.getInt("servings");
        double tempsPreparation = obj.getDouble("readyInMinutes");
        String title = obj.getString("title");
        String imageurl = obj.getString("image");
        List<String> steps = new ArrayList<>();
        List<Ingredient> ingredientList = jsonToIngredients(ingredientsObj, id);
        JSONArray dietsArray = obj.getJSONArray("diets");
        List<String> regimesAlimentaires = new ArrayList<>();
        List<Nutrient> nutrients= jsonToNutrient(nutrientsObj, id);
        for (int i = 0; i < dietsArray.length(); i++) {
            regimesAlimentaires.add(dietsArray.getString(i));
        }
        for(String key : stepsObj.keySet()){
            steps.add(key);
        }

        return new RecipeDetails(id, title, imageurl, nutrients, regimesAlimentaires, steps, ingredientList, tempsPreparation, portion );
    }

    // Methode pour extraire les ingrédients et les etapes d'une recette
    public Map<String, List<String>> extractStepsAndIngredients(JSONArray instructionsArray) {
        Map<String, List<String>> stepsAndIngredients = new HashMap<>();

        for (int i = 0; i < instructionsArray.length(); i++) {
            JSONObject instruction = instructionsArray.getJSONObject(i);
            JSONArray stepsArray = instruction.getJSONArray("steps");

            for (int j = 0; j < stepsArray.length(); j++) {
                JSONObject stepObj = stepsArray.getJSONObject(j);
                String step = stepObj.getString("step");

                // Diviser l'étape en phrases
                String[] sentences = step.split("\\.");

                for (String sentence : sentences) {
                    if (!sentence.trim().isEmpty()) {
                        List<String> ingredients = new ArrayList<>();
                        JSONArray ingredientsArray = stepObj.getJSONArray("ingredients");
                        for (int k = 0; k < ingredientsArray.length(); k++) {
                            JSONObject ingredientObj = ingredientsArray.getJSONObject(k);
                            ingredients.add(ingredientObj.getString("name"));
                        }

                        stepsAndIngredients.put(sentence.trim(), ingredients);
                    }
                }
            }
        }

        return stepsAndIngredients;
    }

    // Transforme un objet json en liste d'indredients
    public List<Ingredient> jsonToIngredients(JSONObject obj, Long id) {
        JSONArray ingredientsArray = obj.getJSONArray("ingredients");

        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsArray.length(); i++) {
            JSONObject ingredientObj = ingredientsArray.getJSONObject(i);
            JSONObject amountObj = ingredientObj.getJSONObject("amount").getJSONObject("metric");

            Ingredient ingredient = new Ingredient(
                    id,
                    ingredientObj.getString("name"),
                    amountObj.getString("unit"),
                    amountObj.getInt("value")
            );
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    public RecipeDetails jsonToIngredientSingle(JSONObject recette, String id) {
        List<Nutrient> nutrients = new ArrayList<>();
        for (int i = 0; i < recette.getJSONArray(  "nutrients") .length(); i++) {
            JSONObject nutrientObj = recette.getJSONArray(  "nutrients").getJSONObject(i);

            Nutrient nutrient = new Nutrient(
                    Long.parseLong(id), // Utilisation de l'index comme ID temporaire
                    nutrientObj.getString("name"),
                    nutrientObj.getDouble("quantity"),
                    nutrientObj.getString("unit"),
                    nutrientObj.getDouble("percentOfDailyNeeds")
            );
            nutrients.add(nutrient);
        }

        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < recette.getJSONArray("ingredients").length(); i++) {
            JSONObject ingredientObj = recette.getJSONArray("ingredients").getJSONObject(i);

            Ingredient ingredient = new Ingredient(
                    Long.parseLong(id),
                    ingredientObj.getString("name"),
                    ingredientObj.getString("unit"),
                    ingredientObj.getInt("quantity")
            );
            ingredients.add(ingredient);
        }

        List<String> steps = new ArrayList<>();
        for (int i = 0; i < recette.getJSONArray("etapes").length(); i++) {
            steps.add(recette.getJSONArray("etapes").getString(i));
        }

        List<String> diets = new ArrayList<>();
        for (int i = 0; i < recette.getJSONArray("regimeAlimentaires").length(); i++) {
            diets.add(recette.getJSONArray("regimeAlimentaires").getString(i));
        }

        RecipeDetails infos = new RecipeDetails(Long.parseLong(id), recette.getString("nom"), recette.getString("image"), nutrients,  diets, steps, ingredients , recette.getDouble("tempsPreparaation"), recette.getInt("portion"));

        return infos;

    }

    public List<Nutrient> jsonToNutrient(JSONObject obj, long id) {
        JSONArray nutrientsArray = obj.getJSONArray("nutrients");

        List<Nutrient> nutrients = new ArrayList<>();
        for (int i = 0; i < nutrientsArray.length(); i++) {
            JSONObject nutrientObj = nutrientsArray.getJSONObject(i);

            Nutrient nutrient = new Nutrient(
                    id, // Utilisation de l'index comme ID temporaire
                    nutrientObj.getString("name"),
                    nutrientObj.getDouble("amount"),
                    nutrientObj.getString("unit"),
                    nutrientObj.getDouble("percentOfDailyNeeds")
            );
            nutrients.add(nutrient);
        }
        return nutrients;
    }

    // Lecture de l'utilisateur depuis le fichier JSON
    public User chargerUtilisateur() {
        try {
            Path path = Paths.get(USER_FILE_PATH);
            if (!Files.exists(path)) {
                User user = new User(1L, "UtilisateurTest");
                modifierFichierUtilisateur(user);
                return user;
            }

            String content = Files.readString(path);
            JSONObject obj = new JSONObject(content);

            Long id = obj.getLong("id");
            String nom = obj.getString("nom");

            JSONObject prefJson = obj.getJSONObject("preference");
            Preference preference = new Preference(prefJson.getLong("id"));
            preference.setDiet(prefJson.getString("regimeAlimentaire"));

            JSONArray intolerancesArray = prefJson.getJSONArray("intolerancesAlimentaires");
            for (int i = 0; i < intolerancesArray.length(); i++) {
                preference.addIntolerance(intolerancesArray.getString(i));
            }

            User user = new User(id, nom);
            user.updatePreference(preference);

            return user;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Sauvegarde de l'utilisateur dans le fichier JSON
    public void modifierFichierUtilisateur(User user) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("id", user.getId());
            obj.put("nom", user.getName());

            Preference pref = user.getPreference();
            JSONObject prefJson = new JSONObject();
            prefJson.put("id", pref.getId());
            prefJson.put("regimeAlimentaire", pref.getDiet());
            prefJson.put("intolerancesAlimentaires", pref.getIntolerances());

            obj.put("preference", prefJson);

            Files.createDirectories(Paths.get("src/main/resources/data"));
            Files.writeString(Paths.get(USER_FILE_PATH), obj.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Réinitialisation de l'utilisateur : nom vide, préférences par défaut
    public void reinitialiserFichierUtilisateur() {
        User user = new User(1L, "");
        Preference preference = new Preference(1L);
        preference.setDiet("");
        preference.setIntolerances(new ArrayList<>());
        user.updatePreference(preference);
        modifierFichierUtilisateur(user);
    }

}
