package com.jmill29.tvtrackerfrontend.service;

import com.jmill29.tvtrackerfrontend.util.HttpRequestUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Test
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