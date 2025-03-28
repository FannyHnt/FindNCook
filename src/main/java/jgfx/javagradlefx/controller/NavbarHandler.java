package jgfx.javagradlefx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class NavbarHandler {
    @FXML
    public void goToUserPage(Node anyNodeInScene) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jgfx/javagradlefx/utilisateur.fxml"));

        // Get screen dimensions
        double screenWidth = Screen.getPrimary().getBounds().getWidth() - 200;
        double screenHeight = Screen.getPrimary().getBounds().getHeight() - 200;

        Scene root =new Scene(loader.load(),screenWidth,screenHeight);

        Stage stage = (Stage) anyNodeInScene.getScene().getWindow();
        stage.setScene(root);
        stage.show();
    }
}
