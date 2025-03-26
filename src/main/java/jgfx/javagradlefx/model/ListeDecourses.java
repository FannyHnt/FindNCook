package jgfx.javagradlefx.model;

import jgfx.javagradlefx.controller.JsonFilesHandler;
import org.json.JSONObject;
import java.util.List;


public class ListeDecourses {

    private final String PATH = "src/main/resources/data/ListeDeCourse.json";
    private JsonFilesHandler jsonFilesHandler = new JsonFilesHandler();

    // methode pour ajouter un ingredient dans la liste de course
    public void ajouterIngredient(Ingredient ingredient){
        try {
            // Charger le fichier JSON
            JSONObject fichierJson = jsonFilesHandler.chargerFichier(PATH);
            // Ajouter le nouvel élément
            fichierJson.put(ingredient.getName(), ingredient.getName());
            // Sauvegarder les modifications dans le fichier
            jsonFilesHandler.ecrireDansFichier(fichierJson, PATH);
        } catch (Exception e) {
            throw  new RuntimeException("Erreur lors de l'ajout de l'ingredient : " + e.getMessage());
        }
    }

    // Methode pour supprimer un ingredient de la liste de course
    public void supprimerIngredient(Ingredient ingredient){
        try {
            // charger le fichier JSON
            JSONObject fichier = jsonFilesHandler.chargerFichier(PATH);
            // Supprimer l'ingredient
            fichier.remove(ingredient.getName());
            // Sauvegarder la modification
            jsonFilesHandler.ecrireDansFichier(fichier, PATH);
        } catch (Exception e){
            throw new RuntimeException("Erreur de la supprision de l'ingredient : " + e.getMessage());
        }
    }

    // Methode pour générer une liste automatiquement
    public void genereListeDeCourseAutomatiquement(List<Ingredient> ingredientList){
        // AJouter les ingredients
        for(Ingredient ingredient : ingredientList){
            ajouterIngredient(ingredient);
        }
    }

}
