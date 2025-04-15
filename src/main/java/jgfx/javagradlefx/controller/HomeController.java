package jgfx.javagradlefx.controller;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import jgfx.javagradlefx.model.Recipe;
import jgfx.javagradlefx.model.User;
import java.io.IOException;
import java.util.List;

public class HomeController {

    private SpoonacularService spoonacularService;
    private List<Recipe> recipes;
    private JsonRequestHandler json = new JsonRequestHandler();
    private User user = json.chargerUtilisateur();
    private NavigationHandler navbar = new NavigationHandler();
    private String userView = "/jgfx/javagradlefx/UserView.fxml";
    private String AdvancedSearchView = "/jgfx/javagradlefx/advancedResearchView.fxml";
    private String groceryListView = "/jgfx/javagradlefx/GroceryListView.fxml";
    private String favoritesView = "/jgfx/javagradlefx/FavoriteListView.fxml";
    @FXML
    private TextField searchField;
    @FXML
    private Label searchResultLabel;
    @FXML
    private FlowPane recipeFlowPane;

    private void showRecipes(){
        recipeFlowPane.getChildren().clear();
        for (Recipe recipe : recipes) {

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
                Image image = new Image(recipe.getUrlImage(), true);
                imageView.setImage(image);
                if (image.isError()) {
                    throw new RuntimeException("Erreur chargement image pour recette " + recipe.getName() + ": " + image.getException());
                }
            } catch (Exception e) {
                throw new RuntimeException("Exception lors du chargement de l'image pour recette " + recipe.getName() + ": " + e.getMessage());
            }

            Hyperlink hyperlink = new Hyperlink();
            hyperlink.setText(recipe.getName());
            hyperlink.setId(String.valueOf(recipe.getId()));
            hyperlink.setOnAction(event -> {
                try {
                    goToRecipeDetails(Long.parseLong(hyperlink.getId()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            card.getChildren().addAll(imageView, hyperlink);
            recipeFlowPane.getChildren().add(card);
        }
    }

    @FXML
    private void initialize() {
        spoonacularService = new SpoonacularService();
        recipes = spoonacularService.getRecipeByPrefs(user.getPreference().getDiet(),user.getPreference().getIntolerances());
        showRecipes();
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText().trim();
        if (!query.isEmpty()) {
            searchResultLabel.setText("You searched : " + query);
        } else {
            searchResultLabel.setText("The search bar is empty !");
        }
        recipes=spoonacularService.getRecipe(query);
        showRecipes();
    }

    @FXML
    private void goToUser() throws IOException {
        navbar.goToAnotherPage(searchField,userView);
    }

    @FXML
    private void goToAdvancedSearch() throws IOException {
        navbar.goToAnotherPage(searchField,AdvancedSearchView);
    }

    @FXML
    private void goToGroceryList() throws IOException {
        navbar.goToAnotherPage(searchField,groceryListView);
    }

    @FXML
    private void goToFavorites() throws IOException {
        navbar.goToAnotherPage(searchField,favoritesView);
    }

    private void goToRecipeDetails(Long id) throws IOException {
        navbar.goToRecipeDetails(id,searchField);
    }
}

