package jgfx.javagradlefx.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import jgfx.javagradlefx.model.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class RecipeDetailsController {

    private RecipeDetails recipeDetails;
    private SpoonacularService service = new SpoonacularService();
    private NavigationHandler navbar = new NavigationHandler();
    private String userView = "/jgfx/javagradlefx/UserView.fxml";
    private String AdvancedSearchView = "/jgfx/javagradlefx/advancedResearchView.fxml";
    private String groceryListView = "/jgfx/javagradlefx/GroceryListView.fxml";
    private String favoritesView = "/jgfx/javagradlefx/FavoriteListView.fxml";
    private String homeView = "/jgfx/javagradlefx/HomeView.fxml";
    GroceryList listeDeCourse;
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
    @FXML
    private Button homeButton;
    @FXML
    private Button toFavoriteListButton;
    private FavoriteRecipe favoriteRecipe;

    @FXML
    public void initialize() {
        favoriteRecipe = new FavoriteRecipe();
        listeDeCourse = new GroceryList();
    }

    public void setRecetteId(Long id) {
        if (favoriteRecipe.isInFavorites(String.valueOf(id))){
            toFavoriteListButton.setText("Remove from favorites");
            this.recipeDetails = favoriteRecipe.getFavoriteById(String.valueOf(id));
        } else {
            this.recipeDetails = service.getRecipeInfo(id);
            toFavoriteListButton.setText("Add to favorites");
        }
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
            Image image = new Image(recipeDetails.getImage(), true);
            imageView.setImage(image);
            if (image.isError()) {
                System.out.println("Erreur chargement image pour recette " + recipeDetails.getNom() + ": " + image.getException());
            } else {
                System.out.println("Image chargée avec succès pour recette " + recipeDetails.getNom());
            }
        } catch (Exception e) {
            System.out.println("Exception lors du chargement de l'image pour recette " + recipeDetails.getNom() + ": " + e.getMessage());
        }

        // Affichage des autres éléments
        titleContent.setText("\t" + recipeDetails.getNom());
        portionContent.setText("\t" + Integer.toString(recipeDetails.getPortion()));
        timeContent.setText("\t" + recipeDetails.getTempsPreparation() + " minutes");
        ingredientsContent.setText(ingredientToString(recipeDetails.getIngredientList()));
        stepsContent.setText(etapesToString(recipeDetails.getEtapes()));
        dietContent.setText(regimeToString(recipeDetails.getRegimesAlimentaires()));
        nutrientsContent.setText(nutrientToString(recipeDetails.getNutrients()));
        innerCardRecipeImage.getChildren().addAll(imageView);
        recipeDetailCard.getChildren().add(innerCardRecipeImage);
        recipeDetailCard.getChildren().add(innerCardRecipe);
    }

    public String ingredientToString(List<Ingredient> ingredients) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < ingredients.size() - 1; i++) {
            sb.append("\t");
            sb.append("- " + ingredients.get(i).getQuantite() + " " + ingredients.get(i).getUnite() + " " + ingredients.get(i).getName());
            sb.append("\n");
        }
        sb.append("\t");
        sb.append("- ");
        sb.append(ingredients.get(ingredients.size() - 1).getQuantite() + " " + ingredients.get(ingredients.size() - 1).getUnite() + " " + ingredients.get(ingredients.size() - 1).getName());
        return sb.toString();
    }

    public String nutrientToString(List<Nutrient> nutrients) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < nutrients.size() - 1; i++) {
            sb.append("\t");
            sb.append("- " + nutrients.get(i).getQuantity() + " " + nutrients.get(i).getUnit() + " " + nutrients.get(i).getName());
            sb.append("\n");
        }
        sb.append("\t");
        sb.append("- ");
        sb.append(nutrients.get(nutrients.size()-1).getQuantity() + " " + nutrients.get(nutrients.size()-1).getUnit() + " " + nutrients.get(nutrients.size()-1).getName());
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
        if (regimes.isEmpty()) {
            return "";
        }
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
    public void modifInFavoriteList() {
        if (toFavoriteListButton.getText().equals("Add to favorites")) {
            favoriteRecipe.ajouterFavoris(recipeDetails);
            toFavoriteListButton.setText("Remove from favorites");
        } else {
            favoriteRecipe.supprimerFavoris(recipeDetails);
            toFavoriteListButton.setText("Add to favorites");
        }
    }

    @FXML
    private void addToShoppingList() {
        listeDeCourse.genereListeDeCourseAutomatiquement(recipeDetails.getIngredientList());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Liste de courses");
        alert.setHeaderText(null);
        alert.setContentText("Les éléments ont bien été ajoutés à la liste de courses.");
        alert.showAndWait();
    }

    @FXML
    private void goToUser() throws IOException {
        navbar.goToAnotherPage(homeButton,userView);
    }

    @FXML
    private void goToHome() throws IOException {
        navbar.goToAnotherPage(homeButton,homeView);
    }

    @FXML
    private void goToAdvancedSearch() throws IOException {
        navbar.goToAnotherPage(homeButton,AdvancedSearchView);
    }

    @FXML
    private void goToGroceryList() throws IOException {
        navbar.goToAnotherPage(homeButton,groceryListView);
    }

    @FXML
    private void goToFavorites() throws IOException {
        navbar.goToAnotherPage(homeButton,favoritesView);
    }
}