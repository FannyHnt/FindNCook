package jgfx.javagradlefx.model;

import jgfx.javagradlefx.controller.JsonFilesHandler;
import jgfx.javagradlefx.controller.JsonRequestHandler;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Gestionnaire des recettes favorites dans l'application JavaFX.
 * Permet d'ajouter, de supprimer et de récupérer des recettes favorites.
 *
 * Dépendances :
 * - `JsonFilesHandler` : Gestion des fichiers JSON pour stocker les recettes favorites.
 * - `JsonRequestHandler` : Conversion des objets JSON en objets Java et inversement.
 *
 * Fichier JSON associé : `src/main/resources/data/FavoriteRecipe.json`.
 *
 * Méthodes principales :
 * - `addToFavoriteList(RecipeDetails recette)` : Ajoute une recette aux favoris.
 * - `removeFromFavoriteList(RecipeDetails recette)` : Supprime une recette des favoris.
 * - `isInFavorites(String id)` : Vérifie si une recette est dans les favoris.
 * - `getFavoris()` : Récupère toutes les recettes favorites sous forme de liste.
 * - `getFavoriteById(String id)` : Récupère les détails d'une recette favorite par son ID.
 *
 * Méthodes utilitaires :
 * - `recipeDetailsToJson(RecipeDetails recipeDetails)` : Convertit une recette en objet JSON.
 *
 * Exceptions :
 * - Lance une `RuntimeException` en cas d'erreur de lecture ou d'écriture dans le fichier JSON.
 */

public class FavoriteRecipe {
    private final String PATH = "src/main/resources/data/FavoriteRecipe.json";
    private JsonFilesHandler jsonFilesHandler = new JsonFilesHandler();
    private JsonRequestHandler jsonRequestHandler = new JsonRequestHandler();

    //methodes pour ajouter une recette dans la liste des favoris
    public void addToFavoriteList(RecipeDetails recette) {
        // Charger le contenu du fichier
        JSONObject fichier = jsonFilesHandler.readFile(PATH);

        // Ajouter la recette
        fichier.put(recette.getId(), recipeDetailsToJson(recette));

        // Sauvegarder la modification
        jsonFilesHandler.writeToFile(fichier, PATH);
    }

    //methodes pour supprimer une recette de la liste des favoris
    public void removeFromFavoriteList(RecipeDetails recette) {
        // Charger le contenu du fichier
        JSONObject fichier = jsonFilesHandler.readFile(PATH);

        // Ajouter la recette
        fichier.remove(recette.getId());

        // Sauvegarder la modification
        jsonFilesHandler.writeToFile(fichier, PATH);
    }

    // Transforme une recette en objet JSON
    public JSONObject recipeDetailsToJson(RecipeDetails recipeDetails) {
        JSONObject newJon = new JSONObject();
        newJon.put("nom", recipeDetails.getName());
        newJon.put("image", recipeDetails.getImage());
        newJon.put("nutrients", recipeDetails.getNutrients());
        newJon.put("regimeAlimentaires", recipeDetails.getDiets());
        newJon.put("etapes", recipeDetails.getSteps());
        newJon.put("ingredients", recipeDetails.getIngredientList());
        newJon.put("tempsPreparaation", recipeDetails.getCookingTime());
        newJon.put("portion", recipeDetails.getServings());
        return newJon;
    }

    // Méthode pour vérifier si une recette est dans les favoris
    public boolean isInFavorites(String id) {
        JSONObject obj = jsonFilesHandler.readFile(PATH);
        return obj.has(id);
    }

    // Méthode pour récupérer les recettes des favoris en tant que liste de recette
    public List<Recipe> getFavoris() {
        JSONObject obj = jsonFilesHandler.readFile(PATH);
        List<Recipe> recipes = new ArrayList<>();
        for (String key : obj.keySet()) {
            JSONObject recette = obj.getJSONObject(key);
            Recipe rec = new Recipe(Long.parseLong(key), recette.getString("nom"), recette.getString("image"));
            recipes.add(rec);
        }
        return recipes;
    }

    public RecipeDetails getFavoriteById(String id) {
        JSONObject obj = jsonFilesHandler.readFile(PATH);
        if (obj.has(id)) {
            JSONObject recette = obj.getJSONObject(id);
            return jsonRequestHandler.jsonToIngredientSingle(recette, id);
        }
        return null;
    }

    public void setJsonFilesHandler(JsonFilesHandler jsonFilesHandler) {
        this.jsonFilesHandler = jsonFilesHandler;
    }

    public void setJsonRequestHandler(JsonRequestHandler jsonRequestHandler) {
        this.jsonRequestHandler = jsonRequestHandler;
    }
}