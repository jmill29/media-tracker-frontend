package com.jmill29.tvtrackerfrontend.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for ErrorResponse DTO")
class ErrorResponseTest {

    private ErrorResponse errorResponse;

    @BeforeEach
    void setUp() {
        errorResponse = new ErrorResponse();
    }

    @Test
    @DisplayName("Should return the correct status code")
    void getStatus() {
        errorResponse.setStatus(404);
        assertEquals(404, errorResponse.getStatus());
    }

    @Test
    @DisplayName("Should set the status code correctly")
    void setStatus() {
        errorResponse.setStatus(500);
        assertEquals(500, errorResponse.getStatus());
    }

    @Test
    @DisplayName("Should return the correct message")
    void getMessage() {
        errorResponse.setMessage("Resource not found");
        assertEquals("Resource not found", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Should set the message correctly")
    void setMessage() {
        errorResponse.setMessage("Server error");
        assertEquals("Server error", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Should return the correct timestamp")
    void getTimeStamp() {
        errorResponse.setTimeStamp(1720000000000L);
        assertEquals(1720000000000L, errorResponse.getTimeStamp());
    }

    @Test
    @DisplayName("Should set the timestamp correctly")
    void setTimeStamp() {
        long now = System.currentTimeMillis();
        errorResponse.setTimeStamp(now);
        assertEquals(now, errorResponse.getTimeStamp());
    }

    @Test
    @DisplayName("toString() should include status, message, and timestamp")
    void testToString() {
        ErrorResponse r = new ErrorResponse(400, "Invalid request", 1723456789000L);
        String result = r.toString();
        assertTrue(result.contains("status=400"));
        assertTrue(result.contains("Invalid request"));
        assertTrue(result.contains("1723456789000"));
    }

    // Optional edge case tests

    @Test
    @DisplayName("Should allow setting null message")
    void setNullMessage() {
        errorResponse.setMessage(null);
        assertNull(errorResponse.getMessage());
        assertTrue(errorResponse.toString().contains("message='null'"));
    }

    @Test
    @DisplayName("Should allow negative timestamp values")
    void setNegativeTimestamp() {
        errorResponse.setTimeStamp(-1L);
        assertEquals(-1L, errorResponse.getTimeStamp());
    }
}
