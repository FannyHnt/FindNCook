package jgfx.javagradlefx.controller;

import jgfx.javagradlefx.model.Ingredient;
import jgfx.javagradlefx.model.Nutrient;
import jgfx.javagradlefx.model.Recette;
import jgfx.javagradlefx.model.RecetteInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonHandler {

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
}
