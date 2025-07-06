package com.jmill29.tvtrackerfrontend.service;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jmill29.tvtrackerfrontend.dto.ShowResponse;
import com.jmill29.tvtrackerfrontend.util.HttpRequestUtil;
import com.jmill29.tvtrackerfrontend.util.LocalDateTimeAdapter;


@DisplayName("Unit tests for ShowService")
class ShowServiceTest {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Test
    @DisplayName("Should display shows when response is successful (HTTP 200)")
    @SuppressWarnings("unchecked")
    void fetchAndDisplayShows_successfulResponse_displaysShows() {
        try (MockedStatic<HttpRequestUtil> mockedHttpRequestUtil = mockStatic(HttpRequestUtil.class)) {
            // Mock the HttpResponse
            HttpResponse<String> mockResponse = mock(HttpResponse.class);
            when(mockResponse.statusCode()).thenReturn(200);

            // Mock the response body
            List<ShowResponse> mockShows = List.of(
                    new ShowResponse(1, "Show 1", "Description 1", "image1.jpg", 10, (short) 2020, LocalDateTime.now()),
                    new ShowResponse(2, "Show 2", "Description 2", "image2.jpg", 8, (short) 2021, LocalDateTime.now())
            );
            when(mockResponse.body()).thenReturn(gson.toJson(mockShows));

            // Mock the sendGet method
            mockedHttpRequestUtil.when(() -> HttpRequestUtil.sendGet(anyString(), anyString(), anyString()))
                    .thenReturn(mockResponse);

            // Call the method
            ShowService.fetchAndDisplayShows("testUser", "testPassword");

            // Verify the output (you can use a custom output stream to capture console output if needed)
        }
    }

    @Test
    @DisplayName("Should display message when no shows are found (HTTP 404)")
    @SuppressWarnings("unchecked")
    void fetchAndDisplayShows_noShowsFound_displaysMessage() {
        try (MockedStatic<HttpRequestUtil> mockedHttpRequestUtil = mockStatic(HttpRequestUtil.class)) {
            // Mock the HttpResponse
            HttpResponse<String> mockResponse = mock(HttpResponse.class);
            when(mockResponse.statusCode()).thenReturn(404);

            // Mock the sendGet method
            mockedHttpRequestUtil.when(() -> HttpRequestUtil.sendGet(anyString(), anyString(), anyString()))
                    .thenReturn(mockResponse);

            // Call the method
            ShowService.fetchAndDisplayShows("testUser", "testPassword");

            // Verify the output (you can use a custom output stream to capture console output if needed)
        }
    }

    @Test
    @DisplayName("Should display error message when server error occurs (HTTP 500)")
    @SuppressWarnings("unchecked")
    void fetchAndDisplayShows_serverError_displaysErrorMessage() {
        try (MockedStatic<HttpRequestUtil> mockedHttpRequestUtil = mockStatic(HttpRequestUtil.class)) {
            // Mock the HttpResponse
            HttpResponse<String> mockResponse = mock(HttpResponse.class);
            when(mockResponse.statusCode()).thenReturn(500);

            // Mock the sendGet method
            mockedHttpRequestUtil.when(() -> HttpRequestUtil.sendGet(anyString(), anyString(), anyString()))
                    .thenReturn(mockResponse);

            // Call the method
            ShowService.fetchAndDisplayShows("testUser", "testPassword");

            // Verify the output (you can use a custom output stream to capture console output if needed)
        }
    }

    @Test
    @DisplayName("Should display error message when an exception is thrown during fetch")
    void fetchAndDisplayShows_exceptionThrown_displaysErrorMessage() {
        try (MockedStatic<HttpRequestUtil> mockedHttpRequestUtil = mockStatic(HttpRequestUtil.class)) {
            // Mock the sendGet method to throw an exception
            mockedHttpRequestUtil.when(() -> HttpRequestUtil.sendGet(anyString(), anyString(), anyString()))
                    .thenThrow(new RuntimeException("Network error"));

            // Call the method
            ShowService.fetchAndDisplayShows("testUser", "testPassword");

            // Verify the output (you can use a custom output stream to capture console output if needed)
        }
    }
}