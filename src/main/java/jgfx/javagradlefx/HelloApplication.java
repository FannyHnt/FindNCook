package jgfx.javagradlefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import jgfx.javagradlefx.controller.SpoonacularService;
import jgfx.javagradlefx.model.Preference;
import jgfx.javagradlefx.model.Utilisateur;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accueil.fxml"));

        // Get screen dimensions
        double screenWidth = Screen.getPrimary().getBounds().getWidth() - 200;
        double screenHeight = Screen.getPrimary().getBounds().getHeight() - 200;

        Scene scene = new Scene(fxmlLoader.load(), screenWidth, screenHeight);
        stage.setTitle("Find N Cook");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        launch();
        //SpoonacularService service = new SpoonacularService();
        //service.getRecipe("tomato rice");
        //service.testRecipeByPreference();
        //System.out.println(service.getAnalyzedRecipeInfomation(1095889L).keySet());
        //System.out.println(service.getAnalyzedRecipeInfomation(1095889L));
        //System.out.println(service.getRecipeInfo(1095889L));
    }
}