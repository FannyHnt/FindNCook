package jgfx.javagradlefx.model;

import jgfx.javagradlefx.controller.JsonFilesHandler;
import jgfx.javagradlefx.controller.JsonRequestHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;


import java.util.List;

public class FavoriteRecipeTest {

    private FavoriteRecipe favoriteRecipe;
    private JsonFilesHandler jsonFilesHandler;
    private JsonRequestHandler jsonRequestHandler;

    @BeforeEach
    public void setUp() {
        jsonFilesHandler = new JsonFilesHandler();
        jsonRequestHandler = new JsonRequestHandler();
        favoriteRecipe = new FavoriteRecipe();
        favoriteRecipe.setJsonFilesHandler(jsonFilesHandler);
        favoriteRecipe.setJsonRequestHandler(jsonRequestHandler);
    }

    @Test
    public void testAddToFavoriteList() {
        RecipeDetails recipeDetails = new RecipeDetails(1L, "Test Recipe", "test.jpg", null, null, null, null, 30, 4);
        JSONObject jsonObject = new JSONObject();
        jsonFilesHandler.writeToFile(jsonObject, "src/main/resources/data/FavoriteRecipe.json");

        favoriteRecipe.addToFavoriteList(recipeDetails);

        JSONObject updatedJsonObject = jsonFilesHandler.readFile("src/main/resources/data/FavoriteRecipe.json");
        assertTrue(updatedJsonObject.has("1"));
    }

    @Test
    public void testRemoveFromFavoriteList() {
        RecipeDetails recipeDetails = new RecipeDetails(1L, "Test Recipe", "test.jpg", null, null, null, null, 30, 4);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("1", new JSONObject());
        jsonFilesHandler.writeToFile(jsonObject, "src/main/resources/data/FavoriteRecipe.json");

        favoriteRecipe.removeFromFavoriteList(recipeDetails);

        JSONObject updatedJsonObject = jsonFilesHandler.readFile("src/main/resources/data/FavoriteRecipe.json");
        assertFalse(updatedJsonObject.has("1"));
    }

    @Test
    public void testIsInFavorites() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("1", new JSONObject());
        jsonFilesHandler.writeToFile(jsonObject, "src/main/resources/data/FavoriteRecipe.json");

        assertTrue(favoriteRecipe.isInFavorites("1"));
        assertFalse(favoriteRecipe.isInFavorites("2"));
    }

    @Test
    public void testGetFavoris() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("1", new JSONObject().put("nom", "Test Recipe").put("image", "test.jpg"));
        jsonFilesHandler.writeToFile(jsonObject, "src/main/resources/data/FavoriteRecipe.json");

        List<Recipe> recipes = favoriteRecipe.getFavoris();

        assertEquals(1, recipes.size());
        assertEquals("Test Recipe", recipes.get(0).getName());
    }

}
