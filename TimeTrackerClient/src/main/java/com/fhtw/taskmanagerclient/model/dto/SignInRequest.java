package com.fhtw.taskmanagerclient.model.dto;

/**
 * Data transfer object class that represents a request to sign in to the system.
 */
public class SignInRequest {

    /**
     * The username of the user attempting to sign in.
     */
    private String username;

    /**
     * The password of the user attempting to sign in.
     */
    private String password;

    /**
     * Default constructor.
     */
    public SignInRequest() {
    }

    /**
     * Returns the username of the user attempting to sign in.
     *
     * @return the username of the user attempting to sign in
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user attempting to sign in.
     *
     * @param username the username of the user attempting to sign in
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the user attempting to sign in.
     *
     * @return the password of the user attempting to sign in
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user attempting to sign in.
     *
     * @param password the password of the user attempting to sign in
     */
    public void setPassword(String password) {
        this.password = password;
    }
}