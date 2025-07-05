package com.jmill29.tvtrackerfrontend.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for UserRequest DTO")
class UserRequestTest {

    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
    }

    @Test
    @DisplayName("Should return the correct name")
    void getName() {
        userRequest.setName("Jacob Miller");
        assertEquals("Jacob Miller", userRequest.getName());
    }

    @Test
    @DisplayName("Should set the name correctly")
    void setName() {
        userRequest.setName("Alex Johnson");
        assertEquals("Alex Johnson", userRequest.getName());
    }

    @Test
    @DisplayName("Should return the correct username")
    void getUsername() {
        userRequest.setUsername("jmill29");
        assertEquals("jmill29", userRequest.getUsername());
    }

    @Test
    @DisplayName("Should set the username correctly")
    void setUsername() {
        userRequest.setUsername("alex_dev");
        assertEquals("alex_dev", userRequest.getUsername());
    }

    @Test
    @DisplayName("Should return the correct password")
    void getPassword() {
        userRequest.setPassword("securePass123!");
        assertEquals("securePass123!", userRequest.getPassword());
    }

    @Test
    @DisplayName("Should set the password correctly")
    void setPassword() {
        userRequest.setPassword("passwordXYZ");
        assertEquals("passwordXYZ", userRequest.getPassword());
    }

    @Test
    @DisplayName("Should return the correct email")
    void getEmail() {
        userRequest.setEmail("jacob@example.com");
        assertEquals("jacob@example.com", userRequest.getEmail());
    }

    @Test
    @DisplayName("Should set the email correctly")
    void setEmail() {
        userRequest.setEmail("alex@example.org");
        assertEquals("alex@example.org", userRequest.getEmail());
    }

    @Test
    @DisplayName("toString() should include name, username, and email but not password")
    void testToString() {
        UserRequest request = new UserRequest("Jacob", "jmill29", "secret123", "jacob@example.com");
        String result = request.toString();
        assertTrue(result.contains("Jacob"));
        assertTrue(result.contains("jmill29"));
        assertTrue(result.contains("jacob@example.com"));
        assertFalse(result.contains("secret123")); // Ensure password is not leaked
    }

    // Optional edge-case tests

    @Test
    @DisplayName("Should allow setting empty name")
    void setEmptyName() {
        userRequest.setName("");
        assertEquals("", userRequest.getName());
    }

    @Test
    @DisplayName("Should allow setting null email")
    void setNullEmail() {
        userRequest.setEmail(null);
        assertNull(userRequest.getEmail());
    }

    @Test
    @DisplayName("Should allow setting blank username")
    void setBlankUsername() {
        userRequest.setUsername("   ");
        assertEquals("   ", userRequest.getUsername());
    }
}
