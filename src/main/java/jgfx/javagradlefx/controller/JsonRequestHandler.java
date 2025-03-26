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

public class JsonRequestHandler {

    private static final String USER_FILE_PATH = "src/main/resources/data/utilisateur.json";

    // Transforme l'obejt JSON en liste de recettes
    public List<Recette> jsonToRecipe(JSONObject obj) {
        List<Recette> recettes = new ArrayList<>();
        for (int i = 0; i < obj.getJSONArray("results").length(); i++) {
            JSONObject recette = obj.getJSONArray("results").getJSONObject(i);
            Recette rec = new Recette(recette.getLong("id"), recette.getString("title"), recette.getString("image"));
            recettes.add(rec);
        }
        return recettes;
    }
    // Transforme l'objet JSON en RecetteInfo
    public RecetteInfo jsonToRecipeInfo(JSONObject obj, Map<String, List<String>> stepsObj, JSONObject ingredientsObj, JSONObject nutrientsObj) {
        Long id = obj.getLong("id");
        int portion = obj.getInt("servings");
        double tempsPreparation = obj.getDouble("readyInMinutes");
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

        return new RecetteInfo(id, portion, tempsPreparation, ingredientList, steps, regimesAlimentaires, nutrients);
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

                List<String> ingredients = new ArrayList<>();
                JSONArray ingredientsArray = stepObj.getJSONArray("ingredients");
                for (int k = 0; k < ingredientsArray.length(); k++) {
                    JSONObject ingredientObj = ingredientsArray.getJSONObject(k);
                    ingredients.add(ingredientObj.getString("name"));
                }

                stepsAndIngredients.put(step, ingredients);
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
    public Utilisateur chargerUtilisateur() {
        try {
            Path path = Paths.get(USER_FILE_PATH);
            if (!Files.exists(path)) {
                Utilisateur user = new Utilisateur(1L, "UtilisateurTest");
                modifierFichierUtilisateur(user);
                return user;
            }

            String content = Files.readString(path);
            JSONObject obj = new JSONObject(content);

            Long id = obj.getLong("id");
            String nom = obj.getString("nom");

            JSONObject prefJson = obj.getJSONObject("preference");
            Preference preference = new Preference(prefJson.getLong("id"));
            preference.setRegimeAlimentaire(prefJson.getString("regimeAlimentaire"));

            JSONArray intolerancesArray = prefJson.getJSONArray("intolerancesAlimentaires");
            for (int i = 0; i < intolerancesArray.length(); i++) {
                preference.ajouterIntoleranceAlimentaire(intolerancesArray.getString(i));
            }

            Utilisateur utilisateur = new Utilisateur(id, nom);
            utilisateur.mettreAJourPreference(preference);

            return utilisateur;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Sauvegarde de l'utilisateur dans le fichier JSON
    public void modifierFichierUtilisateur(Utilisateur utilisateur) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("id", utilisateur.getId());
            obj.put("nom", utilisateur.getNom());

            Preference pref = utilisateur.getPreference();
            JSONObject prefJson = new JSONObject();
            prefJson.put("id", pref.getId());
            prefJson.put("regimeAlimentaire", pref.getRegimeAlimentaire());
            prefJson.put("intolerancesAlimentaires", pref.getIntolerancesAlimentaires());

            obj.put("preference", prefJson);

            Files.createDirectories(Paths.get("src/main/resources/data"));
            Files.writeString(Paths.get(USER_FILE_PATH), obj.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Réinitialisation de l'utilisateur : nom vide, préférences par défaut
    public void reinitialiserFichierUtilisateur() {
        Utilisateur utilisateur = new Utilisateur(1L, "");
        Preference preference = new Preference(1L);
        preference.setRegimeAlimentaire("");
        preference.setIntolerancesAlimentaires(new ArrayList<>());
        utilisateur.mettreAJourPreference(preference);
        modifierFichierUtilisateur(utilisateur);
    }

}
