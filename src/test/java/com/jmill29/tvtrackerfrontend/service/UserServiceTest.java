package com.jmill29.tvtrackerfrontend.service;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.jmill29.tvtrackerfrontend.dto.UserRequest;
import com.jmill29.tvtrackerfrontend.util.HttpRequestUtil;

@DisplayName("Unit tests for UserService")
class UserServiceTest {

    @Test
    @DisplayName("Should return true for successful registration (HTTP 200)")
    @SuppressWarnings("unchecked")
    void registerUser_success() {
        UserRequest mockRequest = new UserRequest("Test", "testuser", "password123", "test@example.com");

        // Mock response with status 200
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(200);

        try (MockedStatic<HttpRequestUtil> mockedStatic = mockStatic(HttpRequestUtil.class)) {
            mockedStatic.when(() -> HttpRequestUtil.sendPost(anyString(), anyString(), anyString(), anyString()))
                    .thenReturn(mockResponse);

            boolean result = UserService.registerUser(mockRequest);
            assertTrue(result);
        }
    }

    @Test
    @DisplayName("Should return false for failed registration (HTTP 400)")
    @SuppressWarnings("unchecked")
    void registerUser_failure_statusCode() {
        UserRequest mockRequest = new UserRequest("Test", "testuser", "password123", "test@example.com");

        // Mock response with status 400
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(400);

        try (MockedStatic<HttpRequestUtil> mockedStatic = mockStatic(HttpRequestUtil.class)) {
            mockedStatic.when(() -> HttpRequestUtil.sendPost(anyString(), anyString(), anyString(), anyString()))
                    .thenReturn(mockResponse);

            boolean result = UserService.registerUser(mockRequest);
            assertFalse(result);
        }
    }

    @Test
    @DisplayName("Should return false when an exception occurs during registration")
    void registerUser_exceptionThrown() {
        UserRequest mockRequest = new UserRequest("Test", "testuser", "password123", "test@example.com");

        try (MockedStatic<HttpRequestUtil> mockedStatic = mockStatic(HttpRequestUtil.class)) {
            mockedStatic.when(() -> HttpRequestUtil.sendPost(anyString(), anyString(), anyString(), anyString()))
                    .thenThrow(new RuntimeException("Network error"));

            boolean result = UserService.registerUser(mockRequest);
            assertFalse(result);
        }
    }
}
