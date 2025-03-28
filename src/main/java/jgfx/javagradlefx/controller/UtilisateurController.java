package jgfx.javagradlefx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import jgfx.javagradlefx.model.Utilisateur;
import jgfx.javagradlefx.model.Preference;

import java.io.IOException;
import java.util.*;

public class UtilisateurController {

    private JsonRequestHandler jsonRequestHandler = new JsonRequestHandler();
    private Utilisateur utilisateur;
    @FXML private TextField nameField;
    @FXML private ComboBox dietField;
    private List<String> intolerancesPossibles = List.of("Dairy", "Egg", "Gluten", "Grain", "Peanut", "Seafood", "Sesame", "Shellfish", "Soy", "Sulfite", "Tree Nut", "Wheat");
    @FXML private FlowPane intoleranceCheckboxes;
    private List<String> regimesPossibles = List.of("","Gluten Free", "Ketogenic", "Vegetarian", "Lacto-Vegetarian", "Ovo-Vegetarian", "Vegan", "Pescetarian", "Paleo","Primal", "Whole30", "Low FODMAP");
    private final String COULEUR_VALIDE = "-fx-background-color: #90EE90;";
    private final String COULEUR_NOUVEAU = "-fx-background-color: #ADD8E6;";
    private final String COULEUR_DECOCHE = "-fx-background-color: #FFB6C1;";
    private final Map<CheckBox, String> checkBoxToIntolerance = new HashMap<>();
    private final Set<String> initialIntolerances = new HashSet<>();
    private final Set<String> added = new HashSet<>();
    private final Set<String> removed = new HashSet<>();

    private NavbarHandler navbar = new NavbarHandler();
    private String homeView = "/jgfx/javagradlefx/accueil.fxml";
    private String AdvancedSearchView = "/jgfx/javagradlefx/advancedResearchView.fxml";
    private String groceryListView = "/jgfx/javagradlefx/listeDeCourse.fxml";
    private String favoritesView = "/jgfx/javagradlefx/favorisView.fxml";

    @FXML
    public void initialize() {

        // On récupère les données enregistrées sur l'utilisateur
        utilisateur = jsonRequestHandler.chargerUtilisateur();
        nameField.setText(utilisateur.getNom());
        for (String regime : regimesPossibles) {
            dietField.getItems().add(regime);
        }

        Preference pref = utilisateur.getPreference();
        dietField.setValue(pref.getRegimeAlimentaire());

        // Charger les intolérances enregistrées
        initialIntolerances.addAll(pref.getIntolerancesAlimentaires());

        // Créer les checkboxes
        for (String intolerance : intolerancesPossibles) {
            CheckBox cb = new CheckBox(intolerance);

            checkBoxToIntolerance.put(cb, intolerance); // Mapping du checkbox avec l'intolérance

            if (initialIntolerances.contains(intolerance)) {
                cb.setSelected(true);
                cb.setStyle(COULEUR_VALIDE);
            }

            cb.setOnAction(e -> handleCheckboxChange(cb));
            intoleranceCheckboxes.getChildren().add(cb);
        }
    }

    @FXML
    public void sauvegarder() {
        utilisateur.setNom(nameField.getText().trim());
        Preference pref = utilisateur.getPreference();
        pref.setRegimeAlimentaire(dietField.getValue().toString());

        // Appliquer les modifs aux intolérances
        Set<String> updated = new HashSet<>(initialIntolerances);
        updated.addAll(added);
        updated.removeAll(removed);
        pref.setIntolerancesAlimentaires(new ArrayList<>(updated));
        jsonRequestHandler.modifierFichierUtilisateur(utilisateur);


        initialIntolerances.clear();
        initialIntolerances.addAll(updated);
        added.clear();
        removed.clear();

        // Mise à jour les styles des checkboxes
        for (CheckBox cb : checkBoxToIntolerance.keySet()) {
            if (cb.isSelected()) {
                cb.setStyle(COULEUR_VALIDE);
            } else {
                cb.setStyle("");
            }
        }
    }


    @FXML
    public void reinitialiser() {
        jsonRequestHandler.reinitialiserFichierUtilisateur();
        utilisateur = jsonRequestHandler.chargerUtilisateur();
        nameField.setText("");
        dietField.setValue(dietField.getItems().get(0));
        initialIntolerances.clear();
        added.clear();
        removed.clear();

        // Décocher toutes les checkboxes et enlever le style
        for (CheckBox cb : checkBoxToIntolerance.keySet()) {
            cb.setSelected(false);
            cb.setStyle("");
        }
    }

    private void handleCheckboxChange(CheckBox cb) {
        String intolerance = checkBoxToIntolerance.get(cb);

        if (cb.isSelected()) {
            if (!initialIntolerances.contains(intolerance)) {
                added.add(intolerance);
                
                removed.remove(intolerance);
                cb.setStyle(COULEUR_NOUVEAU);
            } else {
                removed.remove(intolerance);
                cb.setStyle(COULEUR_VALIDE);
            }
        } else {
            if (initialIntolerances.contains(intolerance)) {
                removed.add(intolerance);
                cb.setStyle(COULEUR_DECOCHE);
            } else {
                added.remove(intolerance);
                cb.setStyle(""); // sans style = état initial
            }
        }
    }

    @FXML
    private void goToHome() throws IOException {
        navbar.goToAnotherPage(dietField, homeView);
    }

    @FXML
    private void goToAdvancedSearch() throws IOException {
        navbar.goToAnotherPage(dietField, AdvancedSearchView);
    }

    @FXML
    private void goToGroceryList() throws IOException {
        navbar.goToAnotherPage(dietField, groceryListView);
    }

    @FXML
    private void goToFavorites() throws IOException {
        navbar.goToAnotherPage(dietField, favoritesView);
    }
}