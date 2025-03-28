package jgfx.javagradlefx.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import jgfx.javagradlefx.model.Recette;
import jgfx.javagradlefx.model.Utilisateur;
import java.io.IOException;
import java.util.List;

public class AccueilController {

    private SpoonacularService spoonacularService = new SpoonacularService();
    private List<Recette> recipes;
    private JsonRequestHandler json = new JsonRequestHandler();
    private Utilisateur user = json.chargerUtilisateur();
    private NavbarHandler navbar = new NavbarHandler();
    private String userView = "/jgfx/javagradlefx/utilisateur.fxml";
    private String AdvancedSearchView = "/jgfx/javagradlefx/advancedResearchView.fxml";
    private String groceryListView = "/jgfx/javagradlefx/listeDeCourse.fxml";
    private String favoritesView = "/jgfx/javagradlefx/favorisView.fxml";
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

    @FXML
    private void initialize() {
        System.out.println("Accueil chargé !");

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

    private void goToRecetteDetaillee(Long id) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jgfx/javagradlefx/recetteDetaillee.fxml"));
        Parent root = loader.load(); // Le FXML est chargé ici, le contrôleur est instancié par JavaFX

        RecetteDetailleeController controller = loader.getController();
        controller.setRecetteId(id);

        double screenWidth = Screen.getPrimary().getBounds().getWidth() - 200;
        double screenHeight = Screen.getPrimary().getBounds().getHeight() - 200;

        Scene scene =new Scene(root,screenWidth,screenHeight);

        Stage stage = (Stage) searchField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}

