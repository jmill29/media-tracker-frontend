package com.jmill29.tvtrackerfrontend.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Unit tests for ShowResponse DTO")
class ShowResponseTest {

    private ShowResponse showResponse;

    @BeforeEach
    void setUp() {
        showResponse = new ShowResponse();
    }

    @Test
    @DisplayName("Should return the correct ID")
    void getId() {
        showResponse.setId(42);
        assertEquals(42, showResponse.getId());
    }

    @Test
    @DisplayName("Should set the ID correctly")
    void setId() {
        showResponse.setId(99);
        assertEquals(99, showResponse.getId());
    }

    @Test
    @DisplayName("Should return the correct name")
    void getName() {
        showResponse.setName("Breaking Bad");
        assertEquals("Breaking Bad", showResponse.getName());
    }

    @Test
    @DisplayName("Should set the name correctly")
    void setName() {
        showResponse.setName("Lost");
        assertEquals("Lost", showResponse.getName());
    }

    @Test
    @DisplayName("Should return the correct description")
    void getDescription() {
        showResponse.setDescription("A drama about survivors on a mysterious island.");
        assertEquals("A drama about survivors on a mysterious island.", showResponse.getDescription());
    }

    @Test
    @DisplayName("Should set the description correctly")
    void setDescription() {
        showResponse.setDescription("Sci-fi thriller");
        assertEquals("Sci-fi thriller", showResponse.getDescription());
    }

    @Test
    @DisplayName("Should return the correct image URL")
    void getImageUrl() {
        showResponse.setImageUrl("https://example.com/image.jpg");
        assertEquals("https://example.com/image.jpg", showResponse.getImageUrl());
    }

    @Test
    @DisplayName("Should set the image URL correctly")
    void setImageUrl() {
        showResponse.setImageUrl("http://cdn.tv/image.png");
        assertEquals("http://cdn.tv/image.png", showResponse.getImageUrl());
    }

    @Test
    @DisplayName("Should return the correct number of episodes")
    void getNumEpisodes() {
        showResponse.setNumEpisodes(10);
        assertEquals(10, showResponse.getNumEpisodes());
    }

    @Test
    @DisplayName("Should set the number of episodes correctly")
    void setNumEpisodes() {
        showResponse.setNumEpisodes(25);
        assertEquals(25, showResponse.getNumEpisodes());
    }

    @Test
    @DisplayName("Should return the correct release year")
    void getReleaseYear() {
        showResponse.setReleaseYear((short) 2008);
        assertEquals(2008, showResponse.getReleaseYear());
    }

    @Test
    @DisplayName("Should set the release year correctly")
    void setReleaseYear() {
        short year = 2024;
        showResponse.setReleaseYear(year);
        assertEquals(year, showResponse.getReleaseYear());
    }

    @Test
    @DisplayName("Should return the correct creation timestamp")
    void getCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        showResponse.setCreatedAt(now);
        assertEquals(now, showResponse.getCreatedAt());
    }

    @Test
    @DisplayName("Should set the creation timestamp correctly")
    void setCreatedAt() {
        LocalDateTime timestamp = LocalDateTime.of(2022, 12, 25, 10, 30);
        showResponse.setCreatedAt(timestamp);
        assertEquals(timestamp, showResponse.getCreatedAt());
    }

    @Test
    @DisplayName("toString() should include name, description, imageUrl, numEpisodes, and releaseYear")
    void testToString() {
        ShowResponse response = new ShowResponse(1, "Stranger Things", "Horror/sci-fi", "url.jpg", 8, (short) 2016, LocalDateTime.now());
        String result = response.toString();
        assertTrue(result.contains("Stranger Things"));
        assertTrue(result.contains("Horror/sci-fi"));
        assertTrue(result.contains("url.jpg"));
        assertTrue(result.contains("8"));
        assertTrue(result.contains("2016"));
    }

    // Edge case / negative tests

    @Test
    @DisplayName("Should allow setting name as empty string")
    void setEmptyName() {
        showResponse.setName("");
        assertEquals("", showResponse.getName());
    }

    @Test
    @DisplayName("Should allow setting a negative number of episodes")
    void setNegativeNumEpisodes() {
        showResponse.setNumEpisodes(-5);
        assertEquals(-5, showResponse.getNumEpisodes());
    }

    @Test
    @DisplayName("Should allow setting createdAt to null")
    void setNullCreatedAt() {
        showResponse.setCreatedAt(null);
        assertNull(showResponse.getCreatedAt());
    }
}
