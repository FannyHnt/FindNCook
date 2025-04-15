package jgfx.javagradlefx.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NutrientTest {

    @Test
    void testGetId() {
        Nutrient nutrient = new Nutrient(1L, "Protein", 10, "g", 20);
        assertEquals(1L, nutrient.getId());
    }

    @Test
    void testGetName() {
        Nutrient nutrient = new Nutrient(1L, "Protein", 10, "g", 20);
        assertEquals("Protein", nutrient.getName());
    }

    @Test
    void testGetQuantity() {
        Nutrient nutrient = new Nutrient(1L, "Protein", 10, "g", 20);
        assertEquals(10, nutrient.getQuantity());
    }

    @Test
    void testGetUnit() {
        Nutrient nutrient = new Nutrient(1L, "Protein", 10, "g", 20);
        assertEquals("g", nutrient.getUnit());
    }

    @Test
    void testGetPercentOfDailyNeeds() {
        Nutrient nutrient = new Nutrient(1L, "Protein", 10, "g", 20);
        assertEquals(20, nutrient.getPercentOfDailyNeeds());
    }

    @Test
    void testToString() {
        Nutrient nutrient = new Nutrient(1L, "Protein", 10, "g", 20);
        String expected = "Nutrient{id=1, name='Protein', quantity=10.0, unit='g', percentOfDailyNeeds=20.0}";
        assertEquals(expected, nutrient.toString());
    }
}