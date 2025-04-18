package jgfx.javagradlefx.model;

import jgfx.javagradlefx.controller.JsonFilesHandler;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestionnaire de la liste de courses dans l'application JavaFX.
 * Permet d'ajouter, de supprimer, de vider et de récupérer des ingrédients.
 *
 * Dépendances :
 * - `JsonFilesHandler` : Gestion des fichiers JSON pour stocker les ingrédients.
 *
 * Fichier JSON associé : `src/main/resources/data/GroceryList.json`.
 *
 * Méthodes principales :
 * - `addIngredient(String ingredient)` : Ajoute un ingrédient à la liste de courses.
 * - `removeIngredient(String ingredient)` : Supprime un ingrédient de la liste de courses.
 * - `clearGroceryList()` : Vide complètement la liste de courses.
 * - `generateGroceryListAuto(List<Ingredient> ingredientList)` : Génère automatiquement une liste de courses à partir d'une liste d'ingrédients.
 * - `getGroceryList()` : Récupère tous les ingrédients de la liste de courses.
 *
 * Méthodes utilitaires :
 * - `setJsonFilesHandler(JsonFilesHandler jsonFilesHandler)` : Définit un gestionnaire JSON personnalisé.
 *
 * Exceptions :
 * - Lance une `RuntimeException` en cas d'erreur de lecture ou d'écriture dans le fichier JSON.
 */

public class GroceryList {

    private final String PATH = "src/main/resources/data/GroceryList.json";
    private JsonFilesHandler jsonFilesHandler = new JsonFilesHandler();

    // methode pour ajouter un ingredient dans la liste de course
    public void addIngredient(String ingredient){
        try {
            // Charger le fichier JSON
            JSONObject fichierJson = jsonFilesHandler.readFile(PATH);
            // Ajouter le nouvel élément
            fichierJson.put(ingredient, ingredient);
            // Sauvegarder les modifications dans le fichier
            jsonFilesHandler.writeToFile(fichierJson, PATH);
        } catch (Exception e) {
            throw  new RuntimeException("Erreur lors de l'ajout de l'ingredient : " + e.getMessage());
        }
    }

    // Methode pour supprimer un ingredient de la liste de course
    public void removeIngredient(String ingredient){
        try {
            // charger le fichier JSON
            JSONObject fichier = jsonFilesHandler.readFile(PATH);
            // Supprimer l'ingredient
            fichier.remove(ingredient);
            // Sauvegarder la modification
            jsonFilesHandler.writeToFile(fichier, PATH);
        } catch (Exception e){
            throw new RuntimeException("Erreur de la supprision de l'ingredient : " + e.getMessage());
        }
    }

    // Methode pour vider la liste de course
    public void clearGroceryList(){
        try {
            // charger le fichier JSON
            JSONObject fichier = jsonFilesHandler.readFile(PATH);
            // Supprimer tous les ingredients
            fichier = new JSONObject();
            // Sauvegarder la modification
            jsonFilesHandler.writeToFile(fichier, PATH);
        } catch (Exception e){
            throw new RuntimeException("Erreur de la supprision de l'ingredient : " + e.getMessage());
        }
    }

    // Methode pour générer une liste automatiquement
    public void generateGroceryListAuto(List<Ingredient> ingredientList){
        // AJouter les ingredients
        for(Ingredient ingredient : ingredientList){
            addIngredient(ingredient.getName());
        }
    }

    // Methode pour récupérer la liste de course
    public List<String> getGroceryList(){
        List<String> ingredients = new ArrayList<>();
        JSONObject obj = jsonFilesHandler.readFile(PATH);
        for(String key : obj.keySet()){
            ingredients.add(key);
        }
        return ingredients;
    }

    public void setJsonFilesHandler(JsonFilesHandler jsonFilesHandler) {
        this.jsonFilesHandler = jsonFilesHandler;
    }
}
