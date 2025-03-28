package jgfx.javagradlefx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class AdvancedResearchViewController {

    @FXML private ComboBox<String> cuisineField;
    @FXML private ComboBox<String> dietField;
    @FXML private ComboBox<String> intolerancesField;
    @FXML private TextField includeIngredientField;
    @FXML private TextField excludeIngredientField;
    @FXML private ComboBox<String> typeField;
    @FXML private TextField maxReadyTimeField;
    @FXML private TextField minProteinField;
    @FXML private TextField minCaloriesField;

    private final List<String> cuisines = List.of("African", "Asian", "American", "British", "Cajun", "Caribbean", "Chinese", "Eastern European", "European", "French", "German", "Greek", "Indian", "Irish", "Italian", "Japanese", "Jewish", "Korean", "Latin American", "Mediterranean", "Mexican", "Middle Eastern", "Nordic", "Southern", "Spanish", "Thai", "Vietnamese");
    private final List<String> diets = List.of("Gluten Free", "Ketogenic", "Vegetarian", "Lacto-Vegetarian", "Ovo-Vegetarian", "Vegan", "Pescetarian", "Paleo", "Primal", "Low FODMAP", "Whole30");
    private final List<String> intolerances = List.of("Dairy", "Egg", "Gluten", "Grain", "Peanut", "Seafood", "Sesame", "Shellfish", "Soy", "Sulfite", "Tree Nut", "Wheat");
    private final List<String> types = List.of("main course", "side dish", "dessert", "appetizer", "salad", "bread", "breakfast", "soup", "beverage", "sauce", "marinade", "fingerfood", "snack", "drink");

    @FXML
    public void initialize() {
        cuisineField.getItems().addAll(cuisines);
        dietField.getItems().addAll(diets);
        intolerancesField.getItems().addAll(intolerances);
        typeField.getItems().addAll(types);
    }
}