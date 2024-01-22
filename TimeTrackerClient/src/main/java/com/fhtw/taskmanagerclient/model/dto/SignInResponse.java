package com.fhtw.taskmanagerclient.model.dto;

/**
 * A Data Transfer Object (DTO) representing the response for a Sign In request.
 * It contains information about the status, message, success flag, jwt token, role, and employee ID of the request.
 */
public class SignInResponse {

    private int status;
    private String message;
    private boolean requestSucceeded = false;
    private String jwtToken;
    private String role;
    private Long employeeId;

    /**
     * Creates an instance of SignInResponse with no arguments
     */
    public SignInResponse() {
    }

    /**
     * Creates an instance of SignInResponse with only the status field set
     *
     * @param status the status of the sign in request
     */
    public SignInResponse(int status) {
        this.status = status;
    }

    /**
     * Creates an instance of SignInResponse with all the fields set
     *
     * @param status     the status of the sign in request
     * @param jwtToken   the JWT token generated for the request
     * @param message    a message describing the outcome of the request
     * @param employeeId the ID of the employee who made the request
     * @param role       the role of the employee who made the request
     */
    public SignInResponse(int status, String jwtToken, String message, Long employeeId, String role) {
        this.status = status;
        this.jwtToken = jwtToken;
        this.employeeId = employeeId;
        this.message = message;
        this.role = role;
    }

    /**
     * Gets whether the request succeeded
     *
     * @return a boolean indicating whether the request succeeded
     */
    public boolean isRequestSucceeded() {
        return requestSucceeded;
    }

    /**
     * Sets whether the request succeeded
     *
     * @param requestSucceeded a boolean indicating whether the request succeeded
     */
    public void setRequestSucceeded(boolean requestSucceeded) {
        this.requestSucceeded = requestSucceeded;
    }

    /**
     * Gets the status of the request
     *
     * @return the status of the request
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of the request
     *
     * @param status the status of the request
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets the message associated with the request
     *
     * @return the message associated with the request
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message describing the outcome of the request.
     *
     * @param message a string representing the message describing the outcome of the request
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the role associated with this response.
     *
     * @return the role associated with this response.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role associated with this response.
     *
     * @param role the role to set.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns the JSON Web Token (JWT) associated with this response.
     *
     * @return the JSON Web Token associated with this response.
     */
    public String getJwtToken() {
        return jwtToken;
    }

    /**
     * Sets the JSON Web Token (JWT) associated with this response.
     *
     * @param jwtToken the JSON Web Token to set.
     */
    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    /**
     * Returns the employee ID associated with this response.
     *
     * @return the employee ID associated with this response.
     */
    public Long getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the employee ID associated with this response.
     *
     * @param employeeId the employee ID to set.
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */

    @Override
    public String toString() {
        return "SignInResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", requestSucceeded=" + requestSucceeded +
                ", jwtToken='" + jwtToken + '\'' +
                ", role='" + role + '\'' +
                ", employeeId=" + employeeId +
                '}';
    }
}
