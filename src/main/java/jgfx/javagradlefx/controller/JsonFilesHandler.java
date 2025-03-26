package jgfx.javagradlefx.controller;

import jgfx.javagradlefx.model.Ingredient;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JsonFilesHandler {

    // Fonction pour charger les éléments d'un fichier JSON
    public  JSONObject chargerFichier(String PATH) throws RuntimeException{
        try {
            Path path = Paths.get(PATH);
            if (!Files.exists(path)) {
                throw new RuntimeException("Le fichier" + PATH + "n\'existe pas");
            }
            String content = Files.readString(path);
            return new JSONObject(content);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(" Il y a un problème au niveau de la lecture du fichier JSON : " + PATH);
        }
    }
    // Fonction pour écrire dans un fichier JSON
    public  void ecrireDansFichier(JSONObject jsonObject, String PATH) {
        try {
            Files.write(Paths.get(PATH), jsonObject.toString(4).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
