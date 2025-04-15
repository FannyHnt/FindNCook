package jgfx.javagradlefx.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {

    @Test
    void testGetName() {
        Ingredient ingredient = new Ingredient(1L, "Tomato", "kg", 2);
        assertEquals("Tomato", ingredient.getName());
    }

    @Test
    void testGetId() {
        Ingredient ingredient = new Ingredient(1L, "Tomato", "kg", 2);
        assertEquals(1L, ingredient.getId());
    }

    @Test
    void testGetUnit() {
        Ingredient ingredient = new Ingredient(1L, "Tomato", "kg", 2);
        assertEquals("kg", ingredient.getUnit());
    }

    @Test
    void testGetQuantity() {
        Ingredient ingredient = new Ingredient(1L, "Tomato", "kg", 2);
        assertEquals(2, ingredient.getQuantity());
    }

    @Test
    void testToString() {
        Ingredient ingredient = new Ingredient(1L, "Tomato", "kg", 2);
        String expected = "Ingredient{id=1, name='Tomato', unite='kg', quantite=2}";
        assertEquals(expected, ingredient.toString());
    }
}