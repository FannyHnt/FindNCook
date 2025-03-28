package jgfx.javagradlefx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import jgfx.javagradlefx.model.User;
import jgfx.javagradlefx.model.Preference;

import java.io.IOException;
import java.util.*;

public class UserController {

    private JsonRequestHandler jsonRequestHandler = new JsonRequestHandler();
    private User user;
    @FXML private TextField nameField;
    @FXML private ComboBox dietField;
    private List<String> intolerancesLabels = List.of("Dairy", "Egg", "Gluten", "Grain", "Peanut", "Seafood", "Sesame", "Shellfish", "Soy", "Sulfite", "Tree Nut", "Wheat");
    @FXML private FlowPane intoleranceCheckboxes;
    private List<String> dietsLabels = List.of("","Gluten Free", "Ketogenic", "Vegetarian", "Lacto-Vegetarian", "Ovo-Vegetarian", "Vegan", "Pescetarian", "Paleo","Primal", "Whole30", "Low FODMAP");
    private final String VALID_COLOR = "-fx-background-color: #90EE90;";
    private final String NEW_COLOR = "-fx-background-color: #ADD8E6;";
    private final String UNCHECKED_COLOR = "-fx-background-color: #FFB6C1;";
    private final Map<CheckBox, String> checkBoxToIntolerance = new HashMap<>();
    private final Set<String> initialIntolerances = new HashSet<>();
    private final Set<String> added = new HashSet<>();
    private final Set<String> removed = new HashSet<>();

    private NavigationHandler navbar = new NavigationHandler();
    private String homeView = "/jgfx/javagradlefx/HomeView.fxml";
    private String AdvancedSearchView = "/jgfx/javagradlefx/advancedResearchView.fxml";
    private String groceryListView = "/jgfx/javagradlefx/GroceryListView.fxml";
    private String favoritesView = "/jgfx/javagradlefx/FavoriteListView.fxml";

    @FXML
    public void initialize() {

        // On récupère les données enregistrées sur l'utilisateur
        user = jsonRequestHandler.chargerUtilisateur();
        nameField.setText(user.getName());
        for (String regime : dietsLabels) {
            dietField.getItems().add(regime);
        }

        Preference pref = user.getPreference();
        dietField.setValue(pref.getDiet());

        // Charger les intolérances enregistrées
        initialIntolerances.addAll(pref.getIntolerances());

        // Créer les checkboxes
        for (String intolerance : intolerancesLabels) {
            CheckBox cb = new CheckBox(intolerance);

            checkBoxToIntolerance.put(cb, intolerance); // Mapping du checkbox avec l'intolérance

            if (initialIntolerances.contains(intolerance)) {
                cb.setSelected(true);
                cb.setStyle(VALID_COLOR);
            }

            cb.setOnAction(e -> handleCheckboxChange(cb));
            intoleranceCheckboxes.getChildren().add(cb);
        }
    }

    @FXML
    public void save() {
        user.setName(nameField.getText().trim());
        Preference pref = user.getPreference();
        pref.setDiet(dietField.getValue().toString());

        // Appliquer les modifs aux intolérances
        Set<String> updated = new HashSet<>(initialIntolerances);
        updated.addAll(added);
        updated.removeAll(removed);
        pref.setIntolerances(new ArrayList<>(updated));
        jsonRequestHandler.modifierFichierUtilisateur(user);


        initialIntolerances.clear();
        initialIntolerances.addAll(updated);
        added.clear();
        removed.clear();

        // Mise à jour les styles des checkboxes
        for (CheckBox cb : checkBoxToIntolerance.keySet()) {
            if (cb.isSelected()) {
                cb.setStyle(VALID_COLOR);
            } else {
                cb.setStyle("");
            }
        }
    }


    @FXML
    public void reinitialize() {
        jsonRequestHandler.reinitialiserFichierUtilisateur();
        user = jsonRequestHandler.chargerUtilisateur();
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
                cb.setStyle(NEW_COLOR);
            } else {
                removed.remove(intolerance);
                cb.setStyle(VALID_COLOR);
            }
        } else {
            if (initialIntolerances.contains(intolerance)) {
                removed.add(intolerance);
                cb.setStyle(UNCHECKED_COLOR);
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