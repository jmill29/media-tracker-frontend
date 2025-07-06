package com.jmill29.tvtrackerfrontend.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;


/**
 * Gson TypeAdapter for serializing and deserializing {@link LocalDateTime} objects to and from ISO-8601 strings.
 * <p>
 * This adapter ensures that LocalDateTime fields are correctly handled when converting between Java objects and JSON.
 * </p>
 */
public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    /**
     * Serializes a {@link LocalDateTime} object to an ISO-8601 string for JSON output.
     *
     * @param out   the JsonWriter to write to
     * @param value the LocalDateTime value to serialize
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        out.value(value.format(formatter));
    }

    /**
     * Deserializes an ISO-8601 string from JSON into a {@link LocalDateTime} object.
     *
     * @param in the JsonReader to read from
     * @return the parsed LocalDateTime object
     * @throws IOException if an I/O error occurs
     */
    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        return LocalDateTime.parse(in.nextString(), formatter);
    }
}
