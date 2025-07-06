package com.jmill29.tvtrackerfrontend.service;

import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.jmill29.tvtrackerfrontend.dto.UserWatchHistoryResponse;
import com.jmill29.tvtrackerfrontend.enums.WatchStatus;
import com.jmill29.tvtrackerfrontend.util.HttpRequestUtil;


@DisplayName("Unit tests for UserWatchHistoryService")
class UserWatchHistoryServiceTest {

    private static final Gson gson = new Gson();

    @Test
    @DisplayName("Should display watch history when response is successful (HTTP 200)")
    @SuppressWarnings("unchecked")
    void fetchAndDisplayWatchHistory_successfulResponse_displaysHistory() {
        try (MockedStatic<HttpRequestUtil> mockedHttpRequestUtil = mockStatic(HttpRequestUtil.class)) {
            HttpResponse<String> mockResponse = mock(HttpResponse.class);
            when(mockResponse.statusCode()).thenReturn(200);

            List<UserWatchHistoryResponse> mockHistory = List.of(
                    new UserWatchHistoryResponse(1, "Show 1", "Description 1", "image1.jpg", WatchStatus.CURRENTLY_WATCHING.getDbValue()),
                    new UserWatchHistoryResponse(2, "Show 2", "Description 2", "image2.jpg", WatchStatus.ALREADY_WATCHED.getDbValue())
            );
            when(mockResponse.body()).thenReturn(gson.toJson(mockHistory));

            mockedHttpRequestUtil.when(() -> HttpRequestUtil.sendGet(anyString(), anyString(), anyString()))
                    .thenReturn(mockResponse);

            UserWatchHistoryService.fetchAndDisplayWatchHistory("testUser", "testPassword");
        }
    }

    @Test
    @DisplayName("Should display message when no watch history is found (HTTP 404)")
    @SuppressWarnings("unchecked")
    void fetchAndDisplayWatchHistory_emptyResponse_displaysMessage() {
        try (MockedStatic<HttpRequestUtil> mockedHttpRequestUtil = mockStatic(HttpRequestUtil.class)) {
            HttpResponse<String> mockResponse = mock(HttpResponse.class);
            when(mockResponse.statusCode()).thenReturn(404);

            mockedHttpRequestUtil.when(() -> HttpRequestUtil.sendGet(anyString(), anyString(), anyString()))
                    .thenReturn(mockResponse);

            UserWatchHistoryService.fetchAndDisplayWatchHistory("testUser", "testPassword");
        }
    }

    @Test
    @DisplayName("Should return true when adding to watch history is successful (HTTP 201)")
    @SuppressWarnings("unchecked")
    void addToWatchHistory_successfulResponse_returnsTrue() {
        try (MockedStatic<HttpRequestUtil> mockedHttpRequestUtil = mockStatic(HttpRequestUtil.class)) {
            HttpResponse<String> mockResponse = mock(HttpResponse.class);
            when(mockResponse.statusCode()).thenReturn(201);

            mockedHttpRequestUtil.when(() -> HttpRequestUtil.sendPost(anyString(), anyString(), anyString(), anyString()))
                    .thenReturn(mockResponse);

            boolean result = UserWatchHistoryService.addToWatchHistory(1, "Currently Watching", "testUser", "testPassword");
            assertTrue(result);
        }
    }

    @Test
    @DisplayName("Should return true when updating watch status is successful (HTTP 200)")
    @SuppressWarnings("unchecked")
    void updateWatchStatus_successfulResponse_returnsTrue() {
        try (MockedStatic<HttpRequestUtil> mockedHttpRequestUtil = mockStatic(HttpRequestUtil.class)) {
            HttpResponse<String> mockResponse = mock(HttpResponse.class);
            when(mockResponse.statusCode()).thenReturn(200);

            mockedHttpRequestUtil.when(() -> HttpRequestUtil.sendPut(anyString(), anyString(), anyString(), anyString()))
                    .thenReturn(mockResponse);

            boolean result = UserWatchHistoryService.updateWatchStatus(1, "Already Watched", "testUser", "testPassword");
            assertTrue(result);
        }
    }

    @Test
    @DisplayName("Should display message when deleting from watch history is successful (HTTP 200)")
    @SuppressWarnings("unchecked")
    void deleteFromWatchHistory_successfulResponse_displaysMessage() {
        try (MockedStatic<HttpRequestUtil> mockedHttpRequestUtil = mockStatic(HttpRequestUtil.class)) {
            HttpResponse<String> mockResponse = mock(HttpResponse.class);
            when(mockResponse.statusCode()).thenReturn(200);

            mockedHttpRequestUtil.when(() -> HttpRequestUtil.sendDelete(anyString(), anyString(), anyString()))
                    .thenReturn(mockResponse);

            UserWatchHistoryService.deleteFromWatchHistory(1, "testUser", "testPassword");
        }
    }
}