package com.fhtw.taskmanagerclient.model.dto;

/**
 * The UpdateCredentialRequest class is used to hold the new password and token of the user
 * Class representing the update credentials request data transfer object.
 * when they want to update their password.
 *
 * @author Muhammed
 */
public class UpdateCredentialRequest {


    /**
     * The new password that the user wants to set
     */
    private String newPassword;
    /**
     * The token of the user who wants to update their password
     */
    private String token;

    /**
     * No-args constructor for creating an empty UpdateCredentialRequest object
     */
    public UpdateCredentialRequest() {
    }

    /**
     * Constructor for creating an UpdateCredentialRequest object with
     * the specified new password and token
     *
     * @param newPassword The new password that the user wants to set
     * @param token       The token of the user who wants to update their password
     */
    public UpdateCredentialRequest(String newPassword, String token) {
        this.newPassword = newPassword;
        this.token = token;
    }

    /**
     * Get the new password
     *
     * @return the new password
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Set the new password
     *
     * @param newPassword The new password
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Get the token of the user
     *
     * @return the token of the user
     */
    public String getToken() {
        return token;
    }

    /**
     * Set the token of the user
     *
     * @param token The token of the user
     */
    public void setToken(String token) {
        this.token = token;
    }
}