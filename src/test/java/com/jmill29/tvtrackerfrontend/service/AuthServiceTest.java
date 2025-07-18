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

import com.jmill29.tvtrackerfrontend.util.HttpRequestUtil;


@DisplayName("Unit tests for AuthService")
class AuthServiceTest {

    @Test
    @DisplayName("Should return true when login is successful (HTTP 200)")
    @SuppressWarnings("unchecked")
    void login_successfulResponse_returnsTrue() {
        try (MockedStatic<HttpRequestUtil> mockedHttpRequestUtil = mockStatic(HttpRequestUtil.class)) {
            // Mock the HttpResponse
            HttpResponse<String> mockResponse = mock(HttpResponse.class);
            when(mockResponse.statusCode()).thenReturn(200);

            // Mock the sendGet method
            mockedHttpRequestUtil.when(() -> HttpRequestUtil.sendGet(anyString(), anyString(), anyString()))
                    .thenReturn(mockResponse);

            // Assert that login returns true for a 200 status code
            assertTrue(AuthService.login("testUser", "testPassword"));
        }
    }

    @Test
    @DisplayName("Should return true when user is not found (HTTP 404)")
    @SuppressWarnings("unchecked")
    void login_userNotFound_returnsTrue() {
        try (MockedStatic<HttpRequestUtil> mockedHttpRequestUtil = mockStatic(HttpRequestUtil.class)) {
            // Mock the HttpResponse
            HttpResponse<String> mockResponse = mock(HttpResponse.class);
            when(mockResponse.statusCode()).thenReturn(404);

            // Mock the sendGet method
            mockedHttpRequestUtil.when(() -> HttpRequestUtil.sendGet(anyString(), anyString(), anyString()))
                    .thenReturn(mockResponse);

            // Assert that login returns true for a 404 status code
            assertTrue(AuthService.login("testUser", "testPassword"));
        }
    }

    @Test
    @DisplayName("Should return false when server error occurs (HTTP 500)")
    @SuppressWarnings("unchecked")
    void login_serverError_returnsFalse() {
        try (MockedStatic<HttpRequestUtil> mockedHttpRequestUtil = mockStatic(HttpRequestUtil.class)) {
            // Mock the HttpResponse
            HttpResponse<String> mockResponse = mock(HttpResponse.class);
            when(mockResponse.statusCode()).thenReturn(500);

            // Mock the sendGet method
            mockedHttpRequestUtil.when(() -> HttpRequestUtil.sendGet(anyString(), anyString(), anyString()))
                    .thenReturn(mockResponse);

            // Assert that login returns false for a 500 status code
            assertFalse(AuthService.login("testUser", "testPassword"));
        }
    }

    @Test
    @DisplayName("Should return false when an exception is thrown during login")
    void login_exceptionThrown_returnsFalse() {
        try (MockedStatic<HttpRequestUtil> mockedHttpRequestUtil = mockStatic(HttpRequestUtil.class)) {
            // Mock the sendGet method to throw an exception
            mockedHttpRequestUtil.when(() -> HttpRequestUtil.sendGet(anyString(), anyString(), anyString()))
                    .thenThrow(new RuntimeException("Network error"));

            // Assert that login returns false when an exception is thrown
            assertFalse(AuthService.login("testUser", "testPassword"));
        }
    }
}