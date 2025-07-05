package com.jmill29.tvtrackerfrontend.dto;

/**
 * DTO representing error information returned by the backend API.
 * Used for displaying error messages and HTTP status details to the user.
 */
public class ErrorResponse {

    /** The HTTP status code of the error (e.g., 400, 404, 500) */
    private int status;

    /** A human-readable error message for the client */
    private String message;

    /** The timestamp of when the error occurred, in milliseconds since epoch */
    private long timeStamp;

    /**
     * Default constructor for deserialization.
     */
    public ErrorResponse() {
    }

    /**
     * Full constructor to initialize all fields.
     *
     * @param status     the HTTP status code
     * @param message    the detailed error message
     * @param timeStamp  the time the error occurred (milliseconds since epoch)
     */
    public ErrorResponse(int status, String message, long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * String representation for debugging and logging.
     */
    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
