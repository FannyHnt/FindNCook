package jgfx.javagradlefx.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import jgfx.javagradlefx.model.Ingredient;
import jgfx.javagradlefx.model.Nutrient;
import jgfx.javagradlefx.model.RecetteInfo;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class RecetteDetailleeController {

    private RecetteInfo recetteInfo;

    @FXML
    private Text titleContent;
    @FXML
    private Text portionContent;
    @FXML
    private Text timeContent;
    @FXML
    private Text ingredientsContent;
    @FXML
    private Text stepsContent;
    @FXML
    private Text dietContent;
    @FXML
    private Text nutrientsContent;
    @FXML
    private VBox recipeDetailCard;
    @FXML
    private VBox innerCardRecipe;

    private SpoonacularService service = new SpoonacularService();


    @FXML
    public void initialize() {

    }

    public void setRecetteId(Long id) {
        this.recetteInfo = service.getRecipeInfo(id);
        showRecipes();
    }

    private void showRecipes() {
        recipeDetailCard.getChildren().clear();
        recipeDetailCard.setMaxWidth(700);

        VBox innerCardRecipeImage = new VBox(5);
        innerCardRecipeImage.setAlignment(Pos.CENTER);
        innerCardRecipeImage.setMaxWidth(250);

        // Image
        ImageView imageView = new ImageView();
        imageView.setFitWidth(120);
        imageView.setFitHeight(90);
        imageView.setPreserveRatio(true);
        try {
            Image image = new Image(recetteInfo.getImage(), true);
            imageView.setImage(image);
            if (image.isError()) {
                System.out.println("Erreur chargement image pour recette " + recetteInfo.getNom() + ": " + image.getException());
            } else {
                System.out.println("Image chargée avec succès pour recette " + recetteInfo.getNom());
            }
        } catch (Exception e) {
            System.out.println("Exception lors du chargement de l'image pour recette " + recetteInfo.getNom() + ": " + e.getMessage());
        }


        // Affichage des autres éléments
        titleContent.setText("\t" + recetteInfo.getNom());
        portionContent.setText("\t" + Integer.toString(recetteInfo.getPortion()));
        timeContent.setText("\t" + recetteInfo.getTempsPreparation() + " minutes");
        ingredientsContent.setText(ingredientToString(recetteInfo.getIngredientList()));
        stepsContent.setText(etapesToString(recetteInfo.getEtapes()));
        dietContent.setText(regimeToString(recetteInfo.getRegimesAlimentaires()));
        nutrientsContent.setText(nutrientToString(recetteInfo.getNutrients()));
        innerCardRecipeImage.getChildren().addAll(imageView);
        recipeDetailCard.getChildren().add(innerCardRecipeImage);
        recipeDetailCard.getChildren().add(innerCardRecipe);
    }

    public String ingredientToString(List<Ingredient> ingredients) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < ingredients.size() - 1; i++) {
            sb.append("\t");
            sb.append("- ");
            sb.append(ingredients.get(i).getName());
            sb.append("\n");
        }
        sb.append("\t");
        sb.append("- ");
        sb.append(ingredients.get(ingredients.size()-1).getName());
        return sb.toString();
    }

    public String nutrientToString(List<Nutrient> nutrients) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < nutrients.size() - 1; i++) {
            sb.append("\t");
            sb.append("- ");
            sb.append(nutrients.get(i).getName());
            sb.append("\n");
        }
        sb.append("\t");
        sb.append("- ");
        sb.append(nutrients.get(nutrients.size()-1).getName());
        return sb.toString();
    }

    public String etapesToString(List<String> etapes) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < etapes.size() - 1; i++) {
            sb.append("\t");
            sb.append("- ");
            sb.append(etapes.get(i));
            sb.append("\n");
        }
        sb.append("\t");
        sb.append("- ");
        sb.append(etapes.get(etapes.size()-1));
        return sb.toString();
    }

    public String regimeToString(List<String> regimes) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < regimes.size() - 1; i++) {
            sb.append("\t");
            sb.append("- ");
            sb.append(regimes.get(i));
            sb.append("\n");
        }
        sb.append("\t");
        sb.append("- ");
        sb.append(regimes.get(regimes.size()-1));
        return sb.toString();
    }

    @FXML
    private void addToShoppingList() {
        System.out.println("Added to shopping list");
    }
    @FXML
    private void addToFavoriteList() {
        System.out.println("Added to favorite list");
    }
}