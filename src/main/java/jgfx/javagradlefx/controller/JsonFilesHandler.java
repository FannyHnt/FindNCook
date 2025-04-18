package jgfx.javagradlefx.controller;


import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Gestionnaire de fichiers JSON pour la lecture et l'écriture.
 * Fournit des méthodes utilitaires pour manipuler des fichiers JSON.
 *
 * Méthodes principales :
 * - `readFile(String PATH)` : Lit le contenu d'un fichier JSON et le retourne sous forme de `JSONObject`.
 * - `writeToFile(JSONObject jsonObject, String PATH)` : Écrit un objet JSON dans un fichier.
 *
 * Exceptions :
 * - Lance une `RuntimeException` si le fichier n'existe pas ou en cas d'erreur d'entrée/sortie.
 */

public class JsonFilesHandler {

    // Fonction pour charger les éléments d'un fichier JSON
    public  JSONObject readFile(String PATH) throws RuntimeException{
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
    public  void writeToFile(JSONObject jsonObject, String PATH) {
        try {
            Files.write(Paths.get(PATH), jsonObject.toString(4).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
