package com.jmill29.tvtrackerfrontend.ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.jmill29.tvtrackerfrontend.service.AuthService;
import com.jmill29.tvtrackerfrontend.service.UserService;
import com.jmill29.tvtrackerfrontend.service.UserWatchHistoryService;

@DisplayName("Tests for Menu UI logic")
class MenuTest {

    private Menu menu;
    private Scanner mockScanner;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        mockScanner = mock(Scanner.class);
        menu = new Menu(mockScanner);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    @DisplayName("handleExit displays exit message")
    void handleExit_displaysExitMessage() {
        menu.handleExit();
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.replaceAll("\\s+", " ").contains("Thanks for using TV Tracker. See you next time!"));
    }

    @Test
    @DisplayName("displayMenu shows error for invalid input")
    void displayMenu_invalidInput_displaysErrorMessage() {
        when(mockScanner.nextLine()).thenReturn("invalid", "exit");
        menu.displayMenu(2);
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Invalid input detected, please try again."));
    }

    @Test
    @DisplayName("handleRegistration registers user with valid input")
    void handleRegistration_validInput_registersUser() {
        when(mockScanner.nextLine()).thenReturn("John Doe", "johndoe", "password123", "john@example.com");
        try (var mockedUserService = mockStatic(UserService.class)) {
            mockedUserService.when(() -> UserService.registerUser(any())).thenReturn(true);
            menu.handleRegistration();
            mockedUserService.verify(() -> UserService.registerUser(any()), times(1));
        }
    }

    @Test
    @DisplayName("handleRegistration does not register user with invalid input")
    void handleRegistration_invalidInput_doesNotRegisterUser() {
        when(mockScanner.nextLine()).thenReturn("", "", "", "");
        try (var mockedUserService = mockStatic(UserService.class)) {
            menu.handleRegistration();
            mockedUserService.verify(() -> UserService.registerUser(any()), never());
        }
    }

    @Test
    @DisplayName("handleLogin logs in user with valid credentials")
    void handleLogin_validCredentials_logsInUser() {
        when(mockScanner.nextLine()).thenReturn("johndoe", "password123");
        try (var mockedAuthService = mockStatic(AuthService.class)) {
            mockedAuthService.when(() -> AuthService.login(anyString(), anyString())).thenReturn(true);
            menu.handleLogin();
            mockedAuthService.verify(() -> AuthService.login(anyString(), anyString()), times(1));
        }
    }

    @Test
    @DisplayName("handleLogin shows error for invalid credentials")
    void handleLogin_invalidCredentials_displaysErrorMessage() {
        when(mockScanner.nextLine()).thenReturn("johndoe", "wrongpassword");
        try (var mockedAuthService = mockStatic(AuthService.class)) {
            mockedAuthService.when(() -> AuthService.login(anyString(), anyString())).thenReturn(false);
            menu.handleLogin();
            mockedAuthService.verify(() -> AuthService.login(anyString(), anyString()), times(1));
        }
    }

    @Test
    @DisplayName("handleAddToWatchHistory adds show with valid input")
    void handleAddToWatchHistory_validInput_addsShow() {
        when(mockScanner.nextLine()).thenReturn("1", "A");
        menu.loggedInUsername = "testuser";
        menu.loggedInPassword = "testpass";
        try (var mockedWatchHistoryService = mockStatic(UserWatchHistoryService.class)) {
            mockedWatchHistoryService.when(() -> UserWatchHistoryService.addToWatchHistory(anyInt(), anyString(), anyString(), anyString()))
                    .thenReturn(true);
            menu.handleAddToWatchHistory();
            mockedWatchHistoryService.verify(() -> UserWatchHistoryService.addToWatchHistory(anyInt(), anyString(), anyString(), anyString()), times(1));
        }
    }

    @Test
    @DisplayName("handleAddToWatchHistory shows error for invalid input")
    void handleAddToWatchHistory_invalidInput_displaysErrorMessage() {
        when(mockScanner.nextLine()).thenReturn("invalid", "Z");
        try (var mockedWatchHistoryService = mockStatic(UserWatchHistoryService.class)) {
            menu.handleAddToWatchHistory();
            mockedWatchHistoryService.verify(() -> UserWatchHistoryService.addToWatchHistory(anyInt(), anyString(), anyString(), anyString()), never());
        }
    }

    @Test
    @DisplayName("handleUpdate updates watch status with valid input")
    void handleUpdate_validInput_updatesWatchStatus() {
        when(mockScanner.nextLine()).thenReturn("1", "B");
        menu.loggedInUsername = "testuser";
        menu.loggedInPassword = "testpass";
        try (var mockedWatchHistoryService = mockStatic(UserWatchHistoryService.class)) {
            mockedWatchHistoryService.when(() -> UserWatchHistoryService.updateWatchStatus(anyInt(), anyString(), anyString(), anyString()))
                    .thenReturn(true);
            menu.handleUpdate();
            mockedWatchHistoryService.verify(() -> UserWatchHistoryService.updateWatchStatus(anyInt(), anyString(), anyString(), anyString()), times(1));
        }
    }

    @Test
    @DisplayName("handleUpdate shows error for invalid input")
    void handleUpdate_invalidInput_displaysErrorMessage() {
        when(mockScanner.nextLine()).thenReturn("invalid", "Z");
        try (var mockedWatchHistoryService = mockStatic(UserWatchHistoryService.class)) {
            menu.handleUpdate();
            mockedWatchHistoryService.verify(() -> UserWatchHistoryService.updateWatchStatus(anyInt(), anyString(), anyString(), anyString()), never());
        }
    }

    @Test
    @DisplayName("handleDeleteFromWatchHistory deletes show with valid input")
    void handleDeleteFromWatchHistory_validInput_deletesShow() {
        when(mockScanner.nextLine()).thenReturn("1");
        menu.loggedInUsername = "testuser";
        menu.loggedInPassword = "testpass";
        try (var mockedWatchHistoryService = mockStatic(UserWatchHistoryService.class)) {
            menu.handleDeleteFromWatchHistory();
            mockedWatchHistoryService.verify(() -> UserWatchHistoryService.deleteFromWatchHistory(anyInt(), anyString(), anyString()), times(1));
        }
    }

    @Test
    @DisplayName("handleDeleteFromWatchHistory shows error for invalid input")
    void handleDeleteFromWatchHistory_invalidInput_displaysErrorMessage() {
        when(mockScanner.nextLine()).thenReturn("invalid");
        try (var mockedWatchHistoryService = mockStatic(UserWatchHistoryService.class)) {
            menu.handleDeleteFromWatchHistory();
            mockedWatchHistoryService.verify(() -> UserWatchHistoryService.deleteFromWatchHistory(anyInt(), anyString(), anyString()), never());
        }
    }

    @Test
    @DisplayName("handleLogout resets login info")
    void handleLogout_resetsLoginInfo() {
        menu.handleLogout();
        assertNull(menu.loggedInUsername);
        assertNull(menu.loggedInPassword);
    }
    @Test
    @DisplayName("displayMenu shows Login and Register options when not logged in")
    void displayMenu_noUserLoggedIn_showsLoginAndRegisterOptions() {
        when(mockScanner.nextLine()).thenReturn("exit");
        menu.displayMenu(1);
        String output = outputStream.toString();
        assertTrue(output.contains("Login"));
        assertTrue(output.contains("Register"));
    }

    @Test
    @DisplayName("displayMenu shows Logout and Add Show options when logged in")
    void displayMenu_userLoggedIn_showsLogoutAndWatchOptions() {
        menu.loggedInUsername = "user";
        menu.loggedInPassword = "pass";
        when(mockScanner.nextLine()).thenReturn("exit");
        menu.displayMenu(1);
        String output = outputStream.toString().replaceAll("\\s+", " ").toLowerCase();
        assertTrue(output.contains("logout"), "Menu should contain 'Logout' option");
        assertTrue(output.contains("add show to watch history"), "Menu should contain 'Add Show to Watch History' option");
    }

    @Test
    @DisplayName("displayMenu shows error for repeated invalid input")
    void displayMenu_repeatedInvalidInput_displaysErrorEachTime() {
        when(mockScanner.nextLine()).thenReturn("bad", "worse", "exit");
        menu.displayMenu(3);
        String output = outputStream.toString();
        int count = output.split("Invalid input detected, please try again.").length - 1;
        assertTrue(count >= 2);
    }

    @Test
    @DisplayName("handleRegistration does not register user with null input")
    void handleRegistration_nullInput_doesNotRegisterUser() {
        when(mockScanner.nextLine()).thenReturn(null, null, null, null);
        try (var mockedUserService = mockStatic(UserService.class)) {
            menu.handleRegistration();
            mockedUserService.verify(() -> UserService.registerUser(any()), never());
        }
    }

    @Test
    @DisplayName("handleAddToWatchHistory shows error if service throws exception")
    void handleAddToWatchHistory_serviceThrowsException_displaysError() {
        when(mockScanner.nextLine()).thenReturn("1", "A");
        menu.loggedInUsername = "testuser";
        menu.loggedInPassword = "testpass";
        try (var mockedWatchHistoryService = mockStatic(UserWatchHistoryService.class)) {
            mockedWatchHistoryService.when(() -> UserWatchHistoryService.addToWatchHistory(anyInt(), anyString(), anyString(), anyString()))
                    .thenThrow(new RuntimeException("Service error"));
            menu.handleAddToWatchHistory();
            String output = outputStream.toString();
            assertTrue(output.toLowerCase().contains("error") || output.toLowerCase().contains("exception"));
        }
    }

    @Test
    @DisplayName("handleUpdate shows error if service throws exception")
    void handleUpdate_serviceThrowsException_displaysError() {
        when(mockScanner.nextLine()).thenReturn("1", "B");
        menu.loggedInUsername = "testuser";
        menu.loggedInPassword = "testpass";
        try (var mockedWatchHistoryService = mockStatic(UserWatchHistoryService.class)) {
            mockedWatchHistoryService.when(() -> UserWatchHistoryService.updateWatchStatus(anyInt(), anyString(), anyString(), anyString()))
                    .thenThrow(new RuntimeException("Service error"));
            menu.handleUpdate();
            String output = outputStream.toString();
            assertTrue(output.toLowerCase().contains("error") || output.toLowerCase().contains("exception"));
        }
    }

    @Test
    @DisplayName("handleDeleteFromWatchHistory shows error if service throws exception")
    void handleDeleteFromWatchHistory_serviceThrowsException_displaysError() {
        when(mockScanner.nextLine()).thenReturn("1");
        menu.loggedInUsername = "testuser";
        menu.loggedInPassword = "testpass";
        try (var mockedWatchHistoryService = mockStatic(UserWatchHistoryService.class)) {
            mockedWatchHistoryService.when(() -> UserWatchHistoryService.deleteFromWatchHistory(anyInt(), anyString(), anyString()))
                    .thenThrow(new RuntimeException("Service error"));
            menu.handleDeleteFromWatchHistory();
            String output = outputStream.toString();
            assertTrue(output.toLowerCase().contains("error") || output.toLowerCase().contains("exception"));
        }
    }
}