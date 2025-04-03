package jgfx.javagradlefx.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Test
    void testGetId() {
        Recipe recipe = new Recipe(1L, "Pasta", "pasta.jpg");
        assertEquals(1L, recipe.getId());
    }

    @Test
    void testSetId() {
        Recipe recipe = new Recipe(1L, "Pasta", "pasta.jpg");
        recipe.setId(2L);
        assertEquals(2L, recipe.getId());
    }

    @Test
    void testGetName() {
        Recipe recipe = new Recipe(1L, "Pasta", "pasta.jpg");
        assertEquals("Pasta", recipe.getName());
    }

    @Test
    void testGetUrlImage() {
        Recipe recipe = new Recipe(1L, "Pasta", "pasta.jpg");
        assertEquals("pasta.jpg", recipe.getUrlImage());
    }

    @Test
    void testToString() {
        Recipe recipe = new Recipe(1L, "Pasta", "pasta.jpg");
        String expected = "1:{id=1, nom='Pasta', urlImage='pasta.jpg'}";
        assertEquals(expected, recipe.toString());
    }
}