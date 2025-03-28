package jgfx.javagradlefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import jgfx.javagradlefx.controller.JsonFilesHandler;
import jgfx.javagradlefx.model.Favoris;
import jgfx.javagradlefx.model.Ingredient;
import jgfx.javagradlefx.model.Nutrient;
import jgfx.javagradlefx.model.RecetteInfo;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class FindNCookApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FindNCookApplication.class.getResource("accueil.fxml"));

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
       //JsonFilesHandler jsonFilesHandler = new JsonFilesHandler();
//        ListeDecourses listeDecourses = new ListeDecourses();
//        List<Ingredient> ingredients = new ArrayList<>();
//        ingredients.add(new Ingredient(1, "pomme", "g", 5));
//        ingredients.add(new Ingredient(2, "orange", "g", 4));
//        ingredients.add(new Ingredient(3, "avocat", "kg", 3));
//        ingredients.add(new Ingredient(4, "huile", "mg", 6));
//
//        Nutrient nutrient = new Nutrient(1L,"nutrient 1", 44, "kg", 50);
//        List<Nutrient> nutrients = new ArrayList<>();
//        nutrients.add(nutrient);
//
//        List<String> regime = new ArrayList<>();
//        regime.add("Vegan");
//        List<String> etapes = new ArrayList<>();
//        etapes.add("etape1");
//        etapes.add("etape2");
//
//        System.out.println("----------------Avant---------------");
//        System.out.println(jsonFilesHandler.chargerFichier("src/main/resources/data/ListeDeCourse.json"));
//        listeDecourses.genereListeDeCourseAutomatiquement(ingredients);
//        System.out.println("----------------Après---------------");
//        System.out.println(jsonFilesHandler.chargerFichier("src/main/resources/data/ListeDeCourse.json"));
//        //SpoonacularService service = new SpoonacularService();
//        //service.getRecipe("tomato rice");
//        //service.testRecipeByPreference();
//        //System.out.println(service.getAnalyzedRecipeInfomation(1095889L).keySet());
//        //System.out.println(service.getAnalyzedRecipeInfomation(1095889L));
//        //System.out.println(service.getRecipeInfo(1095889L));
//
//        RecetteInfo recetteInfo = new RecetteInfo(209129L,
//                "Dinner Tonight: Grilled Romesco-Style Pork",
//                "https://img.spoonacular.com/recipes/716429-556x370.jpg",
//                nutrients,
//                regime,
//                etapes,
//                ingredients,
//                45,3
//                );
//
//        Favoris favoris = new Favoris();
//
//
//        System.out.println("----------------Avant---------------");
//        JSONObject obj = jsonFilesHandler.chargerFichier("src/main/resources/data/Favoris.json");
//        System.out.println(obj.getJSONObject("209129").get("image"));


    }
}