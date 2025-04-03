package jgfx.javagradlefx.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PreferenceTest {

    @Test
    void testGetId() {
        Preference preference = new Preference(1L);
        assertEquals(1L, preference.getId());
    }

    @Test
    void testSetId() {
        Preference preference = new Preference(1L);
        preference.setId(2L);
        assertEquals(2L, preference.getId());
    }

    @Test
    void testGetDiet() {
        Preference preference = new Preference(1L);
        preference.setDiet("Vegan");
        assertEquals("Vegan", preference.getDiet());
    }

    @Test
    void testSetDiet() {
        Preference preference = new Preference(1L);
        preference.setDiet("Vegetarian");
        assertEquals("Vegetarian", preference.getDiet());
    }

    @Test
    void testGetIntolerances() {
        Preference preference = new Preference(1L);
        assertNotNull(preference.getIntolerances());
    }

    @Test
    void testSetIntolerances() {
        Preference preference = new Preference(1L);
        List<String> intolerances = new ArrayList<>();
        intolerances.add("Gluten");
        preference.setIntolerances(intolerances);
        assertEquals(intolerances, preference.getIntolerances());
    }

    @Test
    void testAddIntolerance() {
        Preference preference = new Preference(1L);
        preference.addIntolerance("Lactose");
        assertTrue(preference.getIntolerances().contains("Lactose"));
    }
}