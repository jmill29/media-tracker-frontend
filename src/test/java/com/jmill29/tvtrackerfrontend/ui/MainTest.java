package com.jmill29.tvtrackerfrontend.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for Main entry point")
class MainTest {
    @Test
    @DisplayName("Main.main runs and displays the menu without error")
    void main_runsAndDisplaysMenu() {
        // Provide enough "exit" inputs to ensure the menu loop always has input
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) sb.append("3\n"); // 3 = Exit when not logged in
        ByteArrayInputStream testIn = new ByteArrayInputStream(sb.toString().getBytes());
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        try {
            System.setIn(testIn);
            System.setOut(new PrintStream(testOut));
            Main.main(new String[]{});
            String output = testOut.toString();
            assertTrue(output.contains("TV Tracker CLI"), "Menu should be displayed");
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}
