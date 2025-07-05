package com.jmill29.tvtrackerfrontend.dto;

/**
 * Represents the payload for user registration requests.
 * This DTO is used to send user information to the backend during the /register POST call.
 */
public class UserRequest {

    /** Full name of the user */
    private String name;

    /** Unique username for login and identification */
    private String username;

    /** Raw password (will be encrypted by backend) */
    private String password;

    /** User's email address */
    private String email;

    /**
     * Default constructor required for serialization/deserialization (e.g., Gson).
     */
    public UserRequest() {
    }

    /**
     * Constructs a UserRequest with all fields initialized.
     *
     * @param name     Full name of the user
     * @param username Desired username
     * @param password User's plain-text password (should be encrypted by backend)
     * @param email    User's email address
     */
    public UserRequest(String name, String username, String password, String email) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * @return the user's full name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the user's full name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the user's chosen username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the user's chosen username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the user's raw password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the user's raw password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the user's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns a string representation of the object (excluding password for security).
     *
     * @return String summary of the user registration request
     */
    @Override
    public String toString() {
        return "UserRequest{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
