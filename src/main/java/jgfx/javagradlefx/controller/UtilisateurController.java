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

public class UtilisateurController {

    @FXML private TextField nomField;
    @FXML private TextField regimeField;
    @FXML private TextField intoleranceField;
    @FXML private ListView<String> intoleranceList;

    private JsonRequestHandler jsonRequestHandler = new JsonRequestHandler();
    private Utilisateur utilisateur;

    @FXML
    public void initialize() {
        utilisateur = jsonRequestHandler.chargerUtilisateur();
        nomField.setText(utilisateur.getNom());
        regimeField.setText(utilisateur.getPreference().getRegimeAlimentaire());
        intoleranceList.getItems().addAll(utilisateur.getPreference().getIntolerancesAlimentaires());
    }

    @FXML
    public void ajouterIntolerance() {
        String intolerance = intoleranceField.getText().trim();
        if (!intolerance.isEmpty() && !intoleranceList.getItems().contains(intolerance)) {
            intoleranceList.getItems().add(intolerance);
            intoleranceField.clear();
        }
    }

    @FXML
    public void sauvegarder() {
        utilisateur.setNom(nomField.getText().trim());
        Preference pref = utilisateur.getPreference();
        pref.setRegimeAlimentaire(regimeField.getText().trim());
        pref.setIntolerancesAlimentaires(intoleranceList.getItems());
        jsonRequestHandler.modifierFichierUtilisateur(utilisateur);
    }

    @FXML
    public void reinitialiser() {
        jsonRequestHandler.reinitialiserFichierUtilisateur();
        utilisateur = jsonRequestHandler.chargerUtilisateur();
        nomField.setText("");
        regimeField.setText("");
        intoleranceField.clear();
        intoleranceList.getItems().clear();
    }

    @FXML
    public void goToAccueil() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jgfx/javagradlefx/accueil.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) nomField.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}