package jgfx.javagradlefx.model;

import jgfx.javagradlefx.controller.JsonFilesHandler;
import jgfx.javagradlefx.controller.JsonRequestHandler;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavoriteRecipe {
    private final String PATH = "src/main/resources/data/FavoriteRecipe.json";
    private JsonFilesHandler jsonFilesHandler = new JsonFilesHandler();
    private JsonRequestHandler jsonRequestHandler = new JsonRequestHandler();

    //methodes pour ajouter une recette dans la liste des favoris
    public void ajouterFavoris(RecipeDetails recette) {
        // Charger le contenu du fichier
        JSONObject fichier = jsonFilesHandler.chargerFichier(PATH);

        // Ajouter la recette
        fichier.put(recette.getId(), recetteInfoToJson(recette));

        // Sauvegarder la modification
        jsonFilesHandler.ecrireDansFichier(fichier, PATH);
    }

    //methodes pour supprimer une recette de la liste des favoris
    public void supprimerFavoris(RecipeDetails recette) {
        // Charger le contenu du fichier
        JSONObject fichier = jsonFilesHandler.chargerFichier(PATH);

        // Ajouter la recette
        fichier.remove(recette.getId());

        // Sauvegarder la modification
        jsonFilesHandler.ecrireDansFichier(fichier, PATH);
    }

    // Transforme une recette en objet JSON
    public JSONObject recetteInfoToJson(RecipeDetails recipeDetails) {
        JSONObject newJon = new JSONObject();
        newJon.put("nom", recipeDetails.getNom());
        newJon.put("image", recipeDetails.getImage());
        newJon.put("nutrients", recipeDetails.getNutrients());
        newJon.put("regimeAlimentaires", recipeDetails.getRegimesAlimentaires());
        newJon.put("etapes", recipeDetails.getEtapes());
        newJon.put("ingredients", recipeDetails.getIngredientList());
        newJon.put("tempsPreparaation", recipeDetails.getTempsPreparation());
        newJon.put("portion", recipeDetails.getPortion());
        return newJon;
    }

    // Méthode pour vérifier si une recette est dans les favoris
    public boolean isInFavorites(String id) {
        JSONObject obj = jsonFilesHandler.chargerFichier(PATH);
        return obj.has(id);
    }

    // Méthode pour récupérer les recettes des favoris en tant que liste de recette
    public List<Recipe> getFavoris() {
        JSONObject obj = jsonFilesHandler.chargerFichier(PATH);
        List<Recipe> recipes = new ArrayList<>();
        for (String key : obj.keySet()) {
            JSONObject recette = obj.getJSONObject(key);
            Recipe rec = new Recipe(Long.parseLong(key), recette.getString("nom"), recette.getString("image"));
            recipes.add(rec);
        }
        return recipes;
    }

    public RecipeDetails getFavoriteById(String id) {
        JSONObject obj = jsonFilesHandler.chargerFichier(PATH);
        if (obj.has(id)) {
            JSONObject recette = obj.getJSONObject(id);
            return jsonRequestHandler.jsonToIngredient_single(recette, id);
        }
        return null;
    }
}