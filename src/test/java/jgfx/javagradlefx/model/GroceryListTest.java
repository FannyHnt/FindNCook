package jgfx.javagradlefx.model;

import jgfx.javagradlefx.controller.JsonFilesHandler;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroceryListTest {

    private GroceryList groceryList;
    private JsonFilesHandler jsonFilesHandler;

    @BeforeEach
    public void setUp() {
        jsonFilesHandler = new JsonFilesHandler();
        groceryList = new GroceryList();
        groceryList.setJsonFilesHandler(jsonFilesHandler);
    }

    @Test
    public void testAddIngredient() {
        String ingredient = "Tomato";
        groceryList.addIngredient(ingredient);

        JSONObject jsonObject = jsonFilesHandler.readFile("src/main/resources/data/GroceryList.json");
        assertTrue(jsonObject.has(ingredient));
        groceryList.removeIngredient(ingredient);
    }

    @Test
    public void testRemoveIngredient() {
        String ingredient = "Tomato";
        groceryList.addIngredient(ingredient);
        groceryList.removeIngredient(ingredient);

        JSONObject jsonObject = jsonFilesHandler.readFile("src/main/resources/data/GroceryList.json");
        assertFalse(jsonObject.has(ingredient));
    }

    @Test
    public void testClearGroceryList() {
        groceryList.addIngredient("Tomato");
        groceryList.addIngredient("Potato");
        groceryList.clearGroceryList();

        JSONObject jsonObject = jsonFilesHandler.readFile("src/main/resources/data/GroceryList.json");
        assertEquals(0, jsonObject.length());
    }

    @Test
    public void testGetGroceryList() {
        groceryList.addIngredient("Tomato");
        groceryList.addIngredient("Potato");

        List<String> ingredients = groceryList.getGroceryList();
        assertTrue(ingredients.contains("Tomato"));
        assertTrue(ingredients.contains("Potato"));
        groceryList.removeIngredient("Potato");
        groceryList.removeIngredient("Tomato");
    }
}