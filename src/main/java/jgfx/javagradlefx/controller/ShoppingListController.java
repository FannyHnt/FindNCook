package jgfx.javagradlefx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import jgfx.javagradlefx.model.GroceryList;

public class ShoppingListController {

    @FXML
    private TextField ingredientNameField;
    @FXML
    private ListView<String> ingredientListView;
    private GroceryList listeDecourses = new GroceryList();
    private NavigationHandler navbar = new NavigationHandler();
    private String userView = "/jgfx/javagradlefx/UserView.fxml";
    private String AdvancedSearchView = "/jgfx/javagradlefx/advancedResearchView.fxml";
    private String homeView = "/jgfx/javagradlefx/HomeView.fxml";
    private String favoritesView = "/jgfx/javagradlefx/FavoriteListView.fxml";

    @FXML
    public void initialize() {
        // Charger les ingrédients existants
        chargerIngredients();

        // Configurer la ListView pour utiliser des cellules personnalisées
        ingredientListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new IngredientListCell();
            }
        });
    }

    @FXML
    private void ajouterIngredient() {
        String name = ingredientNameField.getText();
        if (!name.isEmpty()) {
            listeDecourses.ajouterIngredient(name);
            chargerIngredients();
            ingredientNameField.clear();
        }
    }

    private void chargerIngredients() {
        ingredientListView.getItems().clear();
        for (String ingredient : listeDecourses.getListeDeCourse()) {
            ingredientListView.getItems().add(ingredient);
        }
    }

    private class IngredientListCell extends ListCell<String> {
        private HBox hbox = new HBox();
        private TextField textField = new TextField();
        private Button deleteButton = new Button("Remove");

        public IngredientListCell() {
            super();
            textField.setEditable(false);
            HBox.setHgrow(textField, Priority.ALWAYS);
            hbox.getChildren().addAll(textField, deleteButton);
            deleteButton.setOnAction(event -> {
                String item = getItem();
                if (item != null) {
                    listeDecourses.supprimerIngredient(item);
                    chargerIngredients();
                }
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                textField.setText(item);
                setGraphic(hbox);
            }
        }
    }

    @FXML
    private void goToUser() throws Exception {
        navbar.goToAnotherPage(ingredientNameField, userView);
    }

    @FXML
    private void goToHome() throws Exception {
        navbar.goToAnotherPage(ingredientNameField, homeView);
    }

    @FXML
    private void goToAdvancedSearch() throws Exception {
        navbar.goToAnotherPage(ingredientNameField, AdvancedSearchView);
    }

    @FXML
    private void goToFavorites() throws Exception {
        navbar.goToAnotherPage(ingredientNameField, favoritesView);
    }
    
}