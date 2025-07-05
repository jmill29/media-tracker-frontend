package com.jmill29.tvtrackerfrontend.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for UserWatchHistoryResponse DTO")
class UserWatchHistoryResponseTest {

    private UserWatchHistoryResponse response;

    @BeforeEach
    void setUp() {
        response = new UserWatchHistoryResponse();
    }

    @Test
    @DisplayName("Should return the correct showId")
    void getShowId() {
        response.setShowId(10);
        assertEquals(10, response.getShowId());
    }

    @Test
    @DisplayName("Should set the showId correctly")
    void setShowId() {
        response.setShowId(42);
        assertEquals(42, response.getShowId());
    }

    @Test
    @DisplayName("Should return the correct show name")
    void getShowName() {
        response.setShowName("Better Call Saul");
        assertEquals("Better Call Saul", response.getShowName());
    }

    @Test
    @DisplayName("Should set the show name correctly")
    void setShowName() {
        response.setShowName("Severance");
        assertEquals("Severance", response.getShowName());
    }

    @Test
    @DisplayName("Should return the correct description")
    void getDescription() {
        response.setDescription("Corporate thriller with sci-fi elements.");
        assertEquals("Corporate thriller with sci-fi elements.", response.getDescription());
    }

    @Test
    @DisplayName("Should set the description correctly")
    void setDescription() {
        response.setDescription("A group of employees at Lumon Industries...");
        assertEquals("A group of employees at Lumon Industries...", response.getDescription());
    }

    @Test
    @DisplayName("Should return the correct image URL")
    void getImageUrl() {
        response.setImageUrl("https://img.example.com/poster.jpg");
        assertEquals("https://img.example.com/poster.jpg", response.getImageUrl());
    }

    @Test
    @DisplayName("Should set the image URL correctly")
    void setImageUrl() {
        response.setImageUrl("https://cdn.example.com/cover.png");
        assertEquals("https://cdn.example.com/cover.png", response.getImageUrl());
    }

    @Test
    @DisplayName("Should return the correct watch status string")
    void getStatus() {
        response.setStatus("Currently Watching");
        assertEquals("Currently Watching", response.getStatus());
    }

    @Test
    @DisplayName("Should set the watch status string correctly")
    void setStatus() {
        response.setStatus("Already Watched");
        assertEquals("Already Watched", response.getStatus());
    }

    @Test
    @DisplayName("toString() should return all fields including showId and status")
    void testToString() {
        UserWatchHistoryResponse r = new UserWatchHistoryResponse(
                1,
                "Dark",
                "A time-traveling mystery set in Germany.",
                "https://example.com/dark.jpg",
                "Already Watched"
        );

        String result = r.toString();
        assertTrue(result.contains("showId=1"));
        assertTrue(result.contains("Dark"));
        assertTrue(result.contains("Already Watched"));
        assertTrue(result.contains("https://example.com/dark.jpg"));
        assertTrue(result.contains("A time-traveling mystery"));
    }

    // Optional edge case tests

    @Test
    @DisplayName("Should allow setting null status")
    void setNullStatus() {
        response.setStatus(null);
        assertNull(response.getStatus());
        assertTrue(response.toString().contains("status='null'"));
    }

    @Test
    @DisplayName("Should allow setting empty show name")
    void setEmptyShowName() {
        response.setShowName("");
        assertEquals("", response.getShowName());
    }
}
