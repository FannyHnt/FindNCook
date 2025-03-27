package jgfx.javagradlefx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jgfx.javagradlefx.model.Utilisateur;
import jgfx.javagradlefx.model.Preference;

import java.io.IOException;
import java.util.List;

public class UtilisateurController {

    @FXML private TextField nameField;
    @FXML private ComboBox dietField;


    private JsonRequestHandler jsonRequestHandler = new JsonRequestHandler();
    private Utilisateur utilisateur;

    private List<String> intolerancesPossibles = List.of("Dairy", "Egg", "Gluten", "Grain", "Peanut", "Seafood", "Sesame", "Shellfish", "Soy", "Sulfite", "Tree Nut", "Wheat");
    private List<String> regimesPossibles = List.of("","Gluten Free", "Ketogenic", "Vegetarian", "Lacto-Vegetarian", "Ovo-Vegetarian", "Vegan", "Pescetarian", "Paleo","Primal", "Whole30", "Low FODMAP");

    @FXML
    public void initialize() {
        utilisateur = jsonRequestHandler.chargerUtilisateur();
        nameField.setText(utilisateur.getNom());
        for (String regime : regimesPossibles) {
            dietField.getItems().add(regime);
        }
        //dietField.setText(utilisateur.getPreference().getRegimeAlimentaire());
        //intoleranceList.getItems().addAll(utilisateur.getPreference().getIntolerancesAlimentaires());
    }

    @FXML
    public void ajouterIntolerance() {

    }

    @FXML
    public void sauvegarder() {
        utilisateur.setNom(nameField.getText().trim());
        Preference pref = utilisateur.getPreference();
        pref.setRegimeAlimentaire(dietField.getValue().toString());
        //pref.setIntolerancesAlimentaires(intoleranceList.getItems());
        jsonRequestHandler.modifierFichierUtilisateur(utilisateur);
    }

    @FXML
    public void reinitialiser() {
        jsonRequestHandler.reinitialiserFichierUtilisateur();
        utilisateur = jsonRequestHandler.chargerUtilisateur();
        nameField.setText("");
        dietField.setValue(dietField.getItems().get(0));
        //intoleranceField.clear();
        //intoleranceList.getItems().clear();
    }

    @FXML
    public void goToAccueil() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jgfx/javagradlefx/accueil.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}