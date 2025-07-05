package com.jmill29.tvtrackerfrontend.dto;

import com.jmill29.tvtrackerfrontend.enums.WatchStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for UserWatchHistoryRequest DTO")
class UserWatchHistoryRequestTest {

    private UserWatchHistoryRequest request;

    @BeforeEach
    void setUp() {
        request = new UserWatchHistoryRequest();
    }

    @Test
    @DisplayName("Should return the correct showId")
    void getShowId() {
        request.setShowId(101);
        assertEquals(101, request.getShowId());
    }

    @Test
    @DisplayName("Should set the showId correctly")
    void setShowId() {
        request.setShowId(202);
        assertEquals(202, request.getShowId());
    }

    @Test
    @DisplayName("Should return the correct WatchStatus")
    void getStatus() {
        request.setStatus(WatchStatus.ALREADY_WATCHED);
        assertEquals(WatchStatus.ALREADY_WATCHED, request.getStatus());
        assertEquals("Already Watched", request.getStatus().getDbValue());
    }

    @Test
    @DisplayName("Should set the WatchStatus correctly")
    void setStatus() {
        request.setStatus(WatchStatus.CURRENTLY_WATCHING);
        assertEquals(WatchStatus.CURRENTLY_WATCHING, request.getStatus());
        assertEquals("Currently Watching", request.getStatus().getDbValue());
    }

    @Test
    @DisplayName("toString() should include showId and WatchStatus name")
    void testToString() {
        UserWatchHistoryRequest r = new UserWatchHistoryRequest(7, WatchStatus.WANT_TO_WATCH);
        String result = r.toString();
        assertTrue(result.contains("showId=7"));
        assertTrue(result.contains("status=WANT_TO_WATCH")); // name(), not dbValue
    }

    @Test
    @DisplayName("Should allow setting null WatchStatus")
    void setNullStatus() {
        request.setStatus(null);
        assertNull(request.getStatus());
        assertTrue(request.toString().contains("status=null"));
    }

    @Test
    @DisplayName("Should allow setting negative showId")
    void setNegativeShowId() {
        request.setShowId(-10);
        assertEquals(-10, request.getShowId());
    }
}
