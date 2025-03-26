package jgfx.javagradlefx.controller;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import jgfx.javagradlefx.model.Preference;
import jgfx.javagradlefx.model.Recette;
import jgfx.javagradlefx.model.Utilisateur;

import java.util.List;

public class AccueilController {

    private SpoonacularService spoonacularService = new SpoonacularService();
    private List<Recette> recipes;
    private Utilisateur user= new Utilisateur(1L,"Annour");

    @FXML
    private TextField searchField;
    @FXML
    private Label searchResultLabel;
    @FXML
    private FlowPane recipeFlowPane;

    private void showRecipes(){
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
                imageView.setImage(new Image(recette.getUrlImage(), true));
            } catch (Exception e) {
                System.out.println("Erreur chargement image pour recette " + recette.getNom());
            }

            // Nom
            Button button = new Button();
            button.setText(recette.getNom());
            button.setId(String.valueOf(recette.getId()));
            button.setOnAction(event -> {
                System.out.println("Recette sélectionnée : " + recette.getNom());
            });

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

    @FXML
    private void initialize() {
        System.out.println("Accueil chargé !");
        Preference pref1L = new Preference(1L);
        pref1L.setRegimeAlimentaire("omnivore");
        pref1L.ajouterIntoleranceAlimentaire("gluten");
        pref1L.ajouterIntoleranceAlimentaire("dairy");
        user.mettreAJourPreference(pref1L);

        recipes = spoonacularService.getRecipeByPrefs(user.getPreference().getRegimeAlimentaire(),user.getPreference().getIntolerancesAlimentaires());
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
}

