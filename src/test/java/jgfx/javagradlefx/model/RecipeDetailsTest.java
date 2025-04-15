package jgfx.javagradlefx.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

class RecipeDetailsTest {

    @Test
    void testGetId() {
        RecipeDetails recipeDetails = new RecipeDetails(1L, "Pasta", "pasta.jpg", null, null, null, null, 30, 4);
        assertEquals("1", recipeDetails.getId());
    }

    @Test
    void testGetName() {
        RecipeDetails recipeDetails = new RecipeDetails(1L, "Pasta", "pasta.jpg", null, null, null, null, 30, 4);
        assertEquals("Pasta", recipeDetails.getName());
    }

    @Test
    void testGetImage() {
        RecipeDetails recipeDetails = new RecipeDetails(1L, "Pasta", "pasta.jpg", null, null, null, null, 30, 4);
        assertEquals("pasta.jpg", recipeDetails.getImage());
    }

    @Test
    void testGetServings() {
        RecipeDetails recipeDetails = new RecipeDetails(1L, "Pasta", "pasta.jpg", null, null, null, null, 30, 4);
        assertEquals(4, recipeDetails.getServings());
    }

    @Test
    void testGetCookingTime() {
        RecipeDetails recipeDetails = new RecipeDetails(1L, "Pasta", "pasta.jpg", null, null, null, null, 30, 4);
        assertEquals(30, recipeDetails.getCookingTime());
    }

    @Test
    void testGetIngredientList() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1L, "Tomato", "kg", 2));
        RecipeDetails recipeDetails = new RecipeDetails(1L, "Pasta", "pasta.jpg", null, null, null, ingredients, 30, 4);
        assertEquals(ingredients, recipeDetails.getIngredientList());
    }

    @Test
    void testGetSteps() {
        List<String> steps = new ArrayList<>();
        steps.add("Boil water");
        RecipeDetails recipeDetails = new RecipeDetails(1L, "Pasta", "pasta.jpg", null, null, steps, null, 30, 4);
        assertEquals(steps, recipeDetails.getSteps());
    }

    @Test
    void testGetDiets() {
        List<String> diets = new ArrayList<>();
        diets.add("Vegan");
        RecipeDetails recipeDetails = new RecipeDetails(1L, "Pasta", "pasta.jpg", null, diets, null, null, 30, 4);
        assertEquals(diets, recipeDetails.getDiets());
    }

    @Test
    void testGetNutrients() {
        List<Nutrient> nutrients = new ArrayList<>();
        nutrients.add(new Nutrient(1L, "Protein", 10, "g", 20));
        RecipeDetails recipeDetails = new RecipeDetails(1L, "Pasta", "pasta.jpg", nutrients, null, null, null, 30, 4);
        assertEquals(nutrients, recipeDetails.getNutrients());
    }

    @Test
    void testToString() {
        RecipeDetails recipeDetails = new RecipeDetails(1L, "Pasta", "pasta.jpg", null, null, null, null, 30, 4);
        String expected = "RecetteInfo{id=1, portion=4, tempsPreparation=30.0, ingredientList=null, etapes=null, regimesAlimentaires=null, nutrients=null}";
        assertEquals(expected, recipeDetails.toString());
    }
}