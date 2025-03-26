package jgfx.javagradlefx.controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AccueilController {

    @FXML
    private TextField searchField;

    @FXML
    private void initialize() {
        System.out.println("Accueil chargé !");
    }
}

