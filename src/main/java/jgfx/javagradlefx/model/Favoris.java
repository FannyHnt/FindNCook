package jgfx.javagradlefx.model;

import jgfx.javagradlefx.controller.JsonFilesHandler;
import org.json.JSONObject;

import java.util.List;

public class Favoris {
    private final String PATH = "src/main/resources/data/Favoris.json";
    private JsonFilesHandler jsonFilesHandler = new JsonFilesHandler();

    //methodes pour ajouter une recette dans la liste des favoris
    public void ajouterFavoris(RecetteInfo recette) {
        // Charger le contenu du fichier
        JSONObject fichier = jsonFilesHandler.chargerFichier(PATH);

        // Ajouter la recette
        fichier.put(recette.getId(), recetteInfoToJson(recette));

        // Sauvegarder la modification
        jsonFilesHandler.ecrireDansFichier(fichier, PATH);
    }

    //methodes pour supprimer une recette de la liste des favoris
    public void supprimerFavoris(RecetteInfo recette) {
        // Charger le contenu du fichier
        JSONObject fichier = jsonFilesHandler.chargerFichier(PATH);

        // Ajouter la recette
        fichier.remove(recette.getId());

        // Sauvegarder la modification
        jsonFilesHandler.ecrireDansFichier(fichier, PATH);
    }

    // Transforme une recette en objet JSON
    public JSONObject recetteInfoToJson(RecetteInfo recetteInfo) {
        JSONObject newJon = new JSONObject();
        newJon.put("nom", recetteInfo.getNom());
        newJon.put("image", recetteInfo.getImage());
        newJon.put("nutrients", recetteInfo.getNutrients());
        newJon.put("regimeAlimentaires", recetteInfo.getRegimesAlimentaires());
        newJon.put("etapes", recetteInfo.getEtapes());
        newJon.put("ingredients", recetteInfo.getIngredientList());
        newJon.put("tempsPreparaation", recetteInfo.getTempsPreparation());
        newJon.put("portion", recetteInfo.getPortion());
        return newJon;
    }
}