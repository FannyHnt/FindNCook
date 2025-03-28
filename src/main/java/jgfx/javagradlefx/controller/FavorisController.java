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
import jgfx.javagradlefx.model.RecetteInfo;

import java.util.ArrayList;
import java.util.List;

public class FavorisController {
    @FXML
    private FlowPane recipeFlowPane;

    Favoris favoris = new Favoris();
    List<Recette> recipes = new ArrayList<>();

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
                System.out.println("Recette sélectionnée : " + recette.getNom());
            });

            card.getChildren().addAll(imageView, hyperlink);
            recipeFlowPane.getChildren().add(card);
        }
    }
}
