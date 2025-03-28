package jgfx.javagradlefx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.*;

public class AdvancedResearchViewController {

    @FXML
    private ComboBox<String> cuisineField;
    @FXML
    private ComboBox<String> dietField;
    @FXML
    private FlowPane intoleranceCheckboxes;
    @FXML
    private TextField includeIngredientField;
    @FXML
    private TextField excludeIngredientField;
    @FXML
    private ComboBox<String> typeField;
    @FXML
    private TextField maxReadyTimeField;
    @FXML
    private TextField minProteinField;
    @FXML
    private TextField minCaloriesField;

    private final String COULEUR_VALIDE = "-fx-background-color: #90EE90;";
    private final String COULEUR_NOUVEAU = "-fx-background-color: #ADD8E6;";
    private final String COULEUR_DECOCHE = "-fx-background-color: #FFB6C1;";
    private final Set<String> added = new HashSet<>();
    private final Map<CheckBox, String> checkBoxToIntolerance = new HashMap<>();

    private final List<String> cuisines = List.of("African", "Asian", "American", "British", "Cajun", "Caribbean", "Chinese", "Eastern European", "European", "French", "German", "Greek", "Indian", "Irish", "Italian", "Japanese", "Jewish", "Korean", "Latin American", "Mediterranean", "Mexican", "Middle Eastern", "Nordic", "Southern", "Spanish", "Thai", "Vietnamese");
    private final List<String> diets = List.of("Gluten Free", "Ketogenic", "Vegetarian", "Lacto-Vegetarian", "Ovo-Vegetarian", "Vegan", "Pescetarian", "Paleo", "Primal", "Low FODMAP", "Whole30");
    private final List<String> intolerances = List.of("Dairy", "Egg", "Gluten", "Grain", "Peanut", "Seafood", "Sesame", "Shellfish", "Soy", "Sulfite", "Tree Nut", "Wheat");
    private final List<String> types = List.of("main course", "side dish", "dessert", "appetizer", "salad", "bread", "breakfast", "soup", "beverage", "sauce", "marinade", "fingerfood", "snack", "drink");

    @FXML
    public void initialize() {
        cuisineField.getItems().addAll(cuisines);
        dietField.getItems().addAll(diets);
        typeField.getItems().addAll(types);
        // Créer les checkboxes
        for (String intolerance : intolerances) {
            CheckBox cb = new CheckBox(intolerance);

            checkBoxToIntolerance.put(cb, intolerance); // Mapping du checkbox avec l'intolérance
            intoleranceCheckboxes.getChildren().add(cb);
        }
    }

    @FXML
    private void handleSearch() {
        String cuisine = cuisineField.getValue();
        String diet = dietField.getValue();
       // List<String> intolerances = intolerancesField.getCheckModel().getCheckedItems();
        String includeIngredient = includeIngredientField.getText();
        String excludeIngredient = excludeIngredientField.getText();
        String type = typeField.getValue();
        String maxReadyTime = maxReadyTimeField.getText();
        String minProtein = minProteinField.getText();
        String minCalories = minCaloriesField.getText();

        // Construire la requête API avec les paramètres sélectionnés
 //       String apiUrl = buildApiUrl(cuisine, diet, intolerances, includeIngredient, excludeIngredient, type, maxReadyTime, minProtein, minCalories);

    }

}