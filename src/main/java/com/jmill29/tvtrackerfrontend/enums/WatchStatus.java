package com.jmill29.tvtrackerfrontend.enums;

/**
 * Enum representing the watch status of a TV show from the user's perspective.
 * Used to map user selections to consistent values stored or used by the backend.
 */
public enum WatchStatus {

    /** Show has not been watched or added to any list */
    NOT_WATCHED("Not Watched"),

    /** User wants to watch this show */
    WANT_TO_WATCH("Want to Watch"),

    /** User is currently watching this show */
    CURRENTLY_WATCHING("Currently Watching"),

    /** User has completed watching this show */
    ALREADY_WATCHED("Already Watched");

    /** Value matching the backend's expected string for each status */
    private final String dbValue;

    /**
     * Constructor to associate a display/db value with each enum constant.
     *
     * @param dbValue the string used to represent this status in the database
     */
    WatchStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    /**
     * @return the database value associated with this enum constant
     */
    public String getDbValue() {
        return dbValue;
    }

    /**
     * Parses a database value and returns the matching WatchStatus enum.
     *
     * @param value the string from the database or external source
     * @return corresponding WatchStatus enum
     * @throws IllegalArgumentException if no match is found
     */
    public static WatchStatus fromDbValue(String value) {
        for (WatchStatus status : values()) {
            if (status.dbValue.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid watch status: " + value);
    }
}
