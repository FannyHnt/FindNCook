package jgfx.javagradlefx.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import jgfx.javagradlefx.model.FavoriteRecipe;
import jgfx.javagradlefx.model.Recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur pour la gestion des recettes favorites dans l'application JavaFX.
 * Permet d'afficher les recettes favorites sous forme de cartes interactives.
 *
 * Dépendances :
 * - `NavigationHandler` : Navigation entre les vues.
 * - `FavoriteRecipe` : Gestion des recettes favorites.
 *
 * FXML associé : Non spécifié.
 *
 * Méthodes principales :
 * - `initialize()` : Initialise l'affichage des recettes favorites.
 * - `showRecipes()` : Affiche les recettes favorites sous forme de cartes.
 * - Méthodes de navigation (`goToUser`, `goToAdvancedSearch`, etc.) : Naviguent vers d'autres vues.
 */

public class FavoriteController {

    FavoriteRecipe favoriteRecipe = new FavoriteRecipe();
    List<Recipe> recipes = new ArrayList<>();
    private NavigationHandler navbar = new NavigationHandler();
    private String userView = "/jgfx/javagradlefx/UserView.fxml";
    private String AdvancedSearchView = "/jgfx/javagradlefx/advancedResearchView.fxml";
    private String groceryListView = "/jgfx/javagradlefx/GroceryListView.fxml";
    private String homeView = "/jgfx/javagradlefx/HomeView.fxml";

    @FXML
    private FlowPane recipeFlowPane;

    @FXML
    private ScrollPane scrollPaneRoot;

    @FXML
    private void initialize() {
        showRecipes();
    }

    private void showRecipes(){
        recipeFlowPane.getChildren().clear();
        recipes = favoriteRecipe.getFavoris();


        if (recipes.isEmpty()) {
            Label label = new Label("Empty");
            recipeFlowPane.getChildren().add(label);
            return;
        }
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
                    navbar.goToRecipeDetails(Long.parseLong(hyperlink.getId()), recipeFlowPane);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            card.getChildren().addAll(imageView, hyperlink);
            recipeFlowPane.getChildren().add(card);
        }
    }

    @FXML
    private void goToUser() throws Exception {
        navbar.goToAnotherPage(recipeFlowPane, userView);
    }

    @FXML
    private void goToAdvancedSearch() throws Exception {
        navbar.goToAnotherPage(recipeFlowPane, AdvancedSearchView);
    }

    @FXML
    private void goToGroceryList() throws Exception {
        navbar.goToAnotherPage(recipeFlowPane, groceryListView);
    }

    @FXML
    private void goToHome() throws Exception {
        navbar.goToAnotherPage(recipeFlowPane, homeView);
    }
}
