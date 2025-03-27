package jgfx.javagradlefx.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import jgfx.javagradlefx.model.Ingredient;
import jgfx.javagradlefx.model.Nutrient;
import jgfx.javagradlefx.model.RecetteInfo;

import java.util.ArrayList;
import java.util.List;

public class RecetteDetailleeController {

    public RecetteDetailleeController() {
        List<String> regime = new ArrayList<>();
        regime.add("Vegan");
        List<String> etapes = new ArrayList<>();
        etapes.add("etape1");
        etapes.add("etape2");
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1, "pomme", "g", 5));
        ingredients.add(new Ingredient(2, "orange", "g", 4));
        ingredients.add(new Ingredient(3, "avocat", "kg", 3));
        ingredients.add(new Ingredient(4, "huile", "mg", 6));

        Nutrient nutrient = new Nutrient(1L,"nutrient 1", 44, "kg", 50);
        List<Nutrient> nutrients = new ArrayList<>();
        nutrients.add(nutrient);
        recette = new RecetteInfo(209129L,
                "Dinner Tonight: Grilled Romesco-Style Pork",
                "https://img.spoonacular.com/recipes/716429-556x370.jpg",
                nutrients,
                regime,
                etapes,
                ingredients,
                45,3
        );;

    }

    private RecetteInfo recette;

    @FXML
    private FlowPane recipeFlowPane;
    @FXML
    private Label titleLabel;
    @FXML
    private Label portionLabel;
    @FXML
    private Label tempsPreparationLabel;
    @FXML
    private Label ingredientsLabel;
    @FXML
    private Label etapesLabel;
    @FXML
    private Label regimesAlimentairesLabel;
    @FXML
    private Label nutrientsLabel;


    @FXML
    public void initialize() {
        showRecipes();
    }

    private void showRecipes() {
        recipeFlowPane.getChildren().clear();

        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0.3, 0, 2);");
        card.setPrefWidth((700));
        card.setAlignment(Pos.TOP_LEFT);

        // Image
        ImageView imageView = new ImageView();
        imageView.setFitWidth(120);
        imageView.setFitHeight(90);
        imageView.setPreserveRatio(true);
        try {
            Image image = new Image(recette.getImage(), true);
            imageView.setImage(image);
            if (image.isError()) {
                System.out.println("Erreur chargement image pour recette " + recette.getNom() + ": " + image.getException());
            } else {
                System.out.println("Image chargée avec succès pour recette " + recette.getNom());
            }
        } catch (Exception e) {
            System.out.println("Exception lors du chargement de l'image pour recette " + recette.getNom() + ": " + e.getMessage());
        }


        // Affichage des autres éléments
        titleLabel.setText("Title : " + recette.getNom());
        portionLabel.setText("Serving : " + recette.getPortion());
        tempsPreparationLabel.setText("Preparation time : " + recette.getTempsPreparation() + " minutes");
        ingredientsLabel.setText("Ingredients : " + ingredientToString(recette.getIngredientList()));
        etapesLabel.setText("Steps : " + etapesToString(recette.getEtapes()));
        regimesAlimentairesLabel.setText("Intolerances : " + regimeToString(recette.getRegimesAlimentaires()));
        nutrientsLabel.setText("Nutrients : " + nutrientToString(recette.getNutrients()));
        card.getChildren().addAll(imageView, titleLabel,ingredientsLabel, tempsPreparationLabel,  etapesLabel, portionLabel, regimesAlimentairesLabel, nutrientsLabel);
        recipeFlowPane.getChildren().add(card);
    }

    public String ingredientToString(List<Ingredient> ingredients) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for(int i = 0 ; i < ingredients.size(); i++) {
            sb.append("- ");
            sb.append(ingredients.get(i).getName());
            sb.append(" ");
            sb.append("\n");
        }
        return sb.toString();
    }

    public String nutrientToString(List<Nutrient> nutrients) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for(int i = 0 ; i < nutrients.size(); i++) {
            sb.append("- ");
            sb.append(nutrients.get(i).getName());
            sb.append(" ");
            sb.append("\n");
        }
        return sb.toString();
    }

    public String etapesToString(List<String> etapes) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for(int i = 0 ; i < etapes.size(); i++) {
            sb.append("- ");
            sb.append(etapes.get(i));
            sb.append(" ");
            sb.append("\n");
        }
        return sb.toString();
    }

    public String regimeToString(List<String> regimes) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for(int i = 0 ; i < regimes.size(); i++) {
            sb.append("- ");
            sb.append(regimes.get(i));
            sb.append(" ");
            sb.append("\n");
        }
        return sb.toString();
    }
}