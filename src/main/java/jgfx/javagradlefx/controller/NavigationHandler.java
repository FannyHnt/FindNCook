package jgfx.javagradlefx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationHandler {

    @FXML
    public void goToAnotherPage(Node anyNodeInScene,String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));

        // Get screen dimensions
        double screenWidth = anyNodeInScene.getScene().getWindow().getWidth();
        double screenHeight = anyNodeInScene.getScene().getWindow().getHeight();

        Scene root =new Scene(loader.load(),screenWidth,screenHeight);

        Stage stage = (Stage) anyNodeInScene.getScene().getWindow();
        stage.setScene(root);
        stage.show();
    }

    @FXML
    public void goToRecipeDetails(Long id, Node anyNode) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jgfx/javagradlefx/RecipeDetailsView.fxml"));
        Parent root = loader.load(); // Le FXML est chargé ici, le contrôleur est instancié par JavaFX

        RecipeDetailsController controller = loader.getController();
        controller.setRecetteId(id);

        // Get screen dimensions
        double screenWidth = anyNode.getScene().getWindow().getWidth();
        double screenHeight = anyNode.getScene().getWindow().getHeight();

        Scene scene =new Scene(root,screenWidth,screenHeight);

        Stage stage = (Stage) anyNode.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
