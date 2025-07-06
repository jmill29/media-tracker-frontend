package com.jmill29.tvtrackerfrontend.util;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@DisplayName("Tests for LocalDateTimeAdapter")
class LocalDateTimeAdapterTest {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Test
    @DisplayName("Serializes LocalDateTime to ISO string")
    void testSerializeLocalDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 7, 6, 15, 30, 45);
        String json = gson.toJson(dateTime);
        assertEquals("\"2025-07-06T15:30:45\"", json);
    }

    @Test
    @DisplayName("Deserializes ISO string to LocalDateTime")
    void testDeserializeLocalDateTime() {
        String json = "\"2025-07-06T15:30:45\"";
        LocalDateTime dateTime = gson.fromJson(json, LocalDateTime.class);
        assertEquals(LocalDateTime.of(2025, 7, 6, 15, 30, 45), dateTime);
    }
}
