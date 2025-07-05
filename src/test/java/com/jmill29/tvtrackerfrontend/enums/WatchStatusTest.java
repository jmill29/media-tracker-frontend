package com.jmill29.tvtrackerfrontend.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for WatchStatus enum")
class WatchStatusTest {

    @Test
    @DisplayName("Should return correct dbValue for each enum")
    void testGetDbValue() {
        assertEquals("Not Watched", WatchStatus.NOT_WATCHED.getDbValue());
        assertEquals("Want to Watch", WatchStatus.WANT_TO_WATCH.getDbValue());
        assertEquals("Currently Watching", WatchStatus.CURRENTLY_WATCHING.getDbValue());
        assertEquals("Already Watched", WatchStatus.ALREADY_WATCHED.getDbValue());
    }

    @Test
    @DisplayName("Should parse dbValue string to correct WatchStatus")
    void testFromDbValue() {
        assertEquals(WatchStatus.NOT_WATCHED, WatchStatus.fromDbValue("Not Watched"));
        assertEquals(WatchStatus.CURRENTLY_WATCHING, WatchStatus.fromDbValue("currently watching")); // case-insensitive
        assertEquals(WatchStatus.ALREADY_WATCHED, WatchStatus.fromDbValue("Already Watched"));
    }

    @Test
    @DisplayName("Should throw exception for invalid dbValue")
    void testFromDbValueInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                WatchStatus.fromDbValue("Not A Real Status"));
        assertTrue(exception.getMessage().contains("Invalid watch status"));
    }
}
