package jgfx.javagradlefx.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getId() {
        User user = new User(1L, "Test User");
        assertEquals(1L, user.getId());
    }

    @Test
    void setId() {
        User user = new User(1L, "Test User");
        user.setId(2L);
        assertEquals(2L, user.getId());
    }

    @Test
    void getName() {
        User user = new User(1L, "Test User");
        assertEquals("Test User", user.getName());
    }

    @Test
    void setName() {
        User user = new User(1L, "Test User");
        user.setName("New Name");
        assertEquals("New Name", user.getName());
    }

    @Test
    void getPreference() {
        User user = new User(1L, "Test User");
        assertNotNull(user.getPreference());
    }

    @Test
    void updatePreference() {
        User user = new User(1L, "Test User");
        Preference newPreference = new Preference(1L);
        user.updatePreference(newPreference);
        assertEquals(newPreference, user.getPreference());
    }
}