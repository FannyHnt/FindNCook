package jgfx.javagradlefx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import jgfx.javagradlefx.model.Recette;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    private TextField maxProteinField;
    @FXML
    private TextField minCaloriesField;
    @FXML
    private TextField maxCaloriesField;
    @FXML
    private VBox scrollPaneContent; // Ajoutez cette ligne pour référencer le VBox principal
    @FXML
    private FlowPane recipeFlowPane; // Ajoutez cette ligne pour référencer le FlowPane des résultats

    private final Set<String> intolerancesChecked = new HashSet<>();
    private final Map<CheckBox, String> checkBoxToIntolerance = new HashMap<>();
    private SpoonacularService spoonacularService;
    private JsonRequestHandler jsonRequestHandler;
    List<Recette> recipes;

    private final List<String> cuisines = List.of("","African", "Asian", "American", "British", "Cajun", "Caribbean", "Chinese", "Eastern European", "European", "French", "German", "Greek", "Indian", "Irish", "Italian", "Japanese", "Jewish", "Korean", "Latin American", "Mediterranean", "Mexican", "Middle Eastern", "Nordic", "Southern", "Spanish", "Thai", "Vietnamese");
    private final List<String> diets = List.of("","Gluten Free", "Ketogenic", "Vegetarian", "Lacto-Vegetarian", "Ovo-Vegetarian", "Vegan", "Pescetarian", "Paleo", "Primal", "Low FODMAP", "Whole30");
    private final List<String> intolerances = List.of("Dairy", "Egg", "Gluten", "Grain", "Peanut", "Seafood", "Sesame", "Shellfish", "Soy", "Sulfite", "Tree Nut", "Wheat");
    private final List<String> types = List.of("","main course", "side dish", "dessert", "appetizer", "salad", "bread", "breakfast", "soup", "beverage", "sauce", "marinade", "fingerfood", "snack", "drink");

    @FXML
    public void initialize() {
        cuisineField.getItems().addAll(cuisines);
        dietField.getItems().addAll(diets);
        typeField.getItems().addAll(types);
        for (String intolerance : intolerances) {
            CheckBox cb = new CheckBox(intolerance);
            checkBoxToIntolerance.put(cb, intolerance);

            cb.setOnAction(e -> handleCheckboxChange(cb));
            intoleranceCheckboxes.getChildren().add(cb);
        }
        spoonacularService = new SpoonacularService();
        jsonRequestHandler = new JsonRequestHandler();
    }

    @FXML
    private void handleSearch() throws UnsupportedEncodingException {
        StringBuilder includeIngredients = new StringBuilder(includeIngredientField.getText());
        StringBuilder excludeIngredients = new StringBuilder(excludeIngredientField.getText());
        StringBuilder maxReadyTime = new StringBuilder(maxReadyTimeField.getText());
        StringBuilder minProtein = new StringBuilder(minProteinField.getText());
        StringBuilder maxProtein = new StringBuilder(maxProteinField.getText());
        StringBuilder minCalories = new StringBuilder(minCaloriesField.getText());
        StringBuilder maxCalories = new StringBuilder(maxCaloriesField.getText());

        // Construire la requête API avec les paramètres sélectionnés
        StringBuilder sb = new StringBuilder();
        if (cuisineField.getValue() != null) {
            StringBuilder cuisine = new StringBuilder(cuisineField.getValue());
            sb.append("&cuisine=").append(URLEncoder.encode(cuisine.toString(), "UTF-8"));
        }
        if (!intolerancesChecked.isEmpty()) {
            sb.append("&intolerances=");
            for (String intolerance : intolerancesChecked) {
                sb.append(intolerance).append(",");
            }
            sb.deleteCharAt(sb.length() - 1); // Supprimer la dernière virgule
        }
        if (dietField.getValue() != null) {
            StringBuilder diet = new StringBuilder(dietField.getValue());
            sb.append("&diet=").append(URLEncoder.encode(diet.toString(), "UTF-8"));
        }
        if (!includeIngredients.isEmpty()) {
            sb.append("&includeIngredients=").append(includeIngredients);
        }
        if(!excludeIngredients.isEmpty()){
            sb.append("&excludeIngredients=").append(excludeIngredients);
        }
        if(typeField.getValue() != null){
            StringBuilder type = new StringBuilder(typeField.getValue());
            sb.append("&type=").append(URLEncoder.encode(type.toString(), "UTF-8"));
        }
        if(!maxReadyTime.isEmpty()){
            sb.append("&maxReadyTime=").append(maxReadyTime);
        }
        if(!minProtein.isEmpty()){
            sb.append("&minProtein=").append(minProtein);
        }
        if(!maxProtein.isEmpty()){
            sb.append("&maxProtein=").append(maxProtein);
        }
        if(!minCalories.isEmpty()){
            sb.append("&minCalories=").append(minCalories);
        }
        if(!maxCalories.isEmpty()){
            sb.append("&maxCalories=").append(maxCalories);
        }
        JSONObject obj = spoonacularService.getRepcipeByUrl(sb.toString());
        if(obj.getDouble("totalResults") > 0){
            recipes = jsonRequestHandler.jsonToRecipe(obj);
            showRecipes();
        } else {
            recipeFlowPane.getChildren().clear();
            Label noResultLabel = new Label("No recipe found");
            recipeFlowPane.getChildren().add(noResultLabel);
        }
    }

    private void showRecipes() {
        recipeFlowPane.getChildren().clear();
        for (Recette recette : recipes) {
            VBox card = new VBox(5);
            card.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0.3, 0, 2);");
            card.setPrefWidth(300);
            card.setAlignment(Pos.CENTER);

            // Image
            ImageView imageView = new ImageView();
            imageView.setFitWidth(120);
            imageView.setFitHeight(90);
            imageView.setPreserveRatio(true);
            try {
                Image image = new Image(recette.getUrlImage(), true);
                imageView.setImage(image);
                if (image.isError()) {
                    System.out.println("Erreur chargement image pour recette " + recette.getNom() + ": " + image.getException());
                } else {
                    System.out.println("Image chargée avec succès pour recette " + recette.getNom());
                }
            } catch (Exception e) {
                System.out.println("Exception lors du chargement de l'image pour recette " + recette.getNom() + ": " + e.getMessage());
            }

            Hyperlink hyperlink = new Hyperlink();
            hyperlink.setText(recette.getNom());
            hyperlink.setId(String.valueOf(recette.getId()));
            hyperlink.setOnAction(event -> {
                try {
                    goToRecetteDetaillee(Long.parseLong(hyperlink.getId()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            card.getChildren().addAll(imageView, hyperlink);
            recipeFlowPane.getChildren().add(card);
        }
    }

    private void handleCheckboxChange(CheckBox cb) {
        String intolerance = checkBoxToIntolerance.get(cb);

        if (cb.isSelected()) {
            if (!intolerancesChecked.contains(intolerance)) {
                intolerancesChecked.add(intolerance);
            } else {
                if (intolerancesChecked.contains(intolerance)) {
                    intolerancesChecked.remove(intolerance);
                }
            }
        }
    }

    private void goToRecetteDetaillee(Long id) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jgfx/javagradlefx/recetteDetaillee.fxml"));
        Parent root = loader.load(); // Le FXML est chargé ici, le contrôleur est instancié par JavaFX

        RecetteDetailleeController controller = loader.getController();
        controller.setRecetteId(id);

        double screenWidth = Screen.getPrimary().getBounds().getWidth() - 200;
        double screenHeight = Screen.getPrimary().getBounds().getHeight() - 200;

        Scene scene = new Scene(root, screenWidth, screenHeight);

        Stage stage = (Stage) scrollPaneContent.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}