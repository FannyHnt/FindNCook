package jgfx.javagradlefx.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import jgfx.javagradlefx.model.Favoris;
import jgfx.javagradlefx.model.Recette;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FavorisController {

    Favoris favoris = new Favoris();
    List<Recette> recipes = new ArrayList<>();
    private NavigationHandler navbar = new NavigationHandler();
    private String userView = "/jgfx/javagradlefx/utilisateur.fxml";
    private String AdvancedSearchView = "/jgfx/javagradlefx/advancedResearchView.fxml";
    private String groceryListView = "/jgfx/javagradlefx/listeDeCourse.fxml";
    private String homeView = "/jgfx/javagradlefx/accueil.fxml";

    @FXML
    private FlowPane recipeFlowPane;

    @FXML
    private void initialize() {

        showRecipes();
    }



    private void showRecipes(){
        recipeFlowPane.getChildren().clear();
        recipes = favoris.getFavoris();

        if (recipes.isEmpty()) {
            Label label = new Label("Empty");
            recipeFlowPane.getChildren().add(label);
            return;
        }
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
                    navbar.goToRecetteDetaillee(Long.parseLong(hyperlink.getId()), recipeFlowPane);
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
