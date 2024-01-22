package com.fhtw.taskmanagerclient.model.dto;

/**
 * This class represents the response to a request to update a user's credentials.
 * The response includes the HTTP status code, a message indicating the outcome of the request,
 * and a boolean indicating whether the request was successful.
 */
public class UpdateCredentialResponse {
    private int status;
    private String message;
    private boolean requestSucceeded = false;

    /**
     * default constructor
     */
    public UpdateCredentialResponse() {
    }

    /**
     * constructor to create an instance of UpdateCredentialResponse
     *
     * @param status  HTTP status code
     * @param message indicating the outcome of the request
     */
    public UpdateCredentialResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Get the HTTP status code
     *
     * @return HTTP status code
     */
    public int getStatus() {
        return status;
    }

    /**
     * Set the HTTP status code
     *
     * @param status HTTP status code
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Get the boolean indicating whether the request was successful
     *
     * @return boolean indicating whether the request was successful
     */
    public boolean isRequestSucceeded() {
        return requestSucceeded;
    }

    /**
     * Set the boolean indicating whether the request was successful
     *
     * @param requestSucceeded boolean indicating whether the request was successful
     */
    public void setRequestSucceeded(boolean requestSucceeded) {
        this.requestSucceeded = requestSucceeded;
    }

    /**
     * Get the message indicating the outcome of the request
     *
     * @return message indicating the outcome of the request
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message indicating the outcome of the request
     *
     * @param message indicating the outcome of the request
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UpdateCredentialResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", requestSucceeded=" + requestSucceeded +
                '}';
    }
}
