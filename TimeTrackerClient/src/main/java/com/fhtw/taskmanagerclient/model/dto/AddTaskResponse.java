/**
 * The AddTaskResponse class is a data transfer object (DTO) used to model an API response to add a task to an employee.
 * The class also includes getter and setter methods for each field.
 *
 * @author Rama
 * @version 1.0
 * @since version 1.0
 */

package com.fhtw.taskmanagerclient.model.dto;

/**
 * Class that represents the response object for an add task request.
 *
 * <p>This class contains information about the status of the request and a message describing
 * the result of the request.
 */
public class AddTaskResponse {
    /**
     * HTTP status code of the response.
     */
    private int status;
    /**
     * Message describing the result of the request.
     */
    private String message;
    /**
     * Indicator of whether the request was successful or not.
     */
    private boolean requestSucceeded = false;

    /**
     * Constructor that sets the status and message.
     *
     * @param status HTTP status code of the response
     * @param message message describing the result of the request
     */
    public AddTaskResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Default constructor.
     */
    public AddTaskResponse() {
    }

    /**
     * Returns the value of {@link #requestSucceeded}.
     *
     * @return true if the request was successful, false otherwise
     */
    public boolean isRequestSucceeded() {
        return requestSucceeded;
    }

    /**
     * Sets the value of {@link #requestSucceeded}.
     *
     * @param requestSucceeded true if the request was successful, false otherwise
     */
    public void setRequestSucceeded(boolean requestSucceeded) {
        this.requestSucceeded = requestSucceeded;
    }

    /**
     * Returns the value of {@link #status}.
     *
     * @return the HTTP status code of the response
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the value of {@link #status}.
     *
     * @param status the HTTP status code of the response
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Returns the value of {@link #message}.
     *
     * @return message describing the result of the request
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of {@link #message}.
     *
     * @param message message describing the result of the request
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "AddTaskResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", requestSucceeded=" + requestSucceeded +
                '}';
    }
}
