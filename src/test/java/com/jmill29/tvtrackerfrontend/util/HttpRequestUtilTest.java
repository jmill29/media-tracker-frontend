package com.jmill29.tvtrackerfrontend.util;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for HttpRequestUtil utility methods")
class HttpRequestUtilTest {

    @Test
    @DisplayName("loginInfoNotNull covers all branches")
    void testLoginInfoNotNull() {
        assertTrue(HttpRequestUtil.loginInfoNotNull("user", "pass"));
        assertFalse(HttpRequestUtil.loginInfoNotNull(null, "pass"));
        assertFalse(HttpRequestUtil.loginInfoNotNull("user", null));
        assertFalse(HttpRequestUtil.loginInfoNotNull("", "pass"));
        assertFalse(HttpRequestUtil.loginInfoNotNull("user", ""));
        assertFalse(HttpRequestUtil.loginInfoNotNull(" ", "pass"));
        assertFalse(HttpRequestUtil.loginInfoNotNull("user", "   "));
    }

    @Test
    @DisplayName("getBasicAuthHeader returns correct header for user:pass and empty values")
    void testGetBasicAuthHeader_reflection() throws Exception {
        Method method = HttpRequestUtil.class.getDeclaredMethod("getBasicAuthHeader", String.class, String.class);
        method.setAccessible(true);
        String header = (String) method.invoke(null, "user", "pass");
        assertTrue(header.startsWith("Basic "));
        // The base64 of "user:pass" is dXNlcjpwYXNz
        assertEquals("Basic dXNlcjpwYXNz", header);

        // Test with empty username and password
        String emptyHeader = (String) method.invoke(null, "", "");
        assertEquals("Basic Og==", emptyHeader); // base64 of ":"
    }

    // Note: sendGet is best tested with integration tests or with a mock server.
    // Here, we just check that it throws for an invalid URL.
    @Test
    @DisplayName("sendGet throws exception for invalid URL")
    void testSendGet_invalidUrl_throwsException() {
        Exception exception = assertThrows(Exception.class, () -> {
            HttpRequestUtil.sendGet("http://invalid-url", null, null);
        });
        assertNotNull(exception);
    }

    @Test
    @DisplayName("sendGet returns 200 for a valid public endpoint (no auth)")
    void testSendGet_validUrl_noAuth() throws Exception {
        var response = HttpRequestUtil.sendGet("https://httpbin.org/get", null, null);
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("\"url\": \"https://httpbin.org/get\""));
    }

    @Test
    @DisplayName("sendGet returns 401 for a protected endpoint with wrong auth")
    void testSendGet_validUrl_wrongAuth() throws Exception {
        var response = HttpRequestUtil.sendGet("https://httpbin.org/basic-auth/user/pass", "user", "wrongpass");
        assertEquals(401, response.statusCode());
    }
}
