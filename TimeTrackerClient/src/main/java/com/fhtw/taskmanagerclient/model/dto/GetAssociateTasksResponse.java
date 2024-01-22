package com.fhtw.taskmanagerclient.model.dto;

import java.util.List;

/**
 * DTO class representing the response from a request to get the tasks of an associate.
 *
 * @author <a href="mailto:your.email@example.com">Your Name</a>
 */
public class GetAssociateTasksResponse {
    List<TaskDto> tasks;
    private int status;
    private String message;
    private boolean requestSucceeded = false;

    /**
     * Default constructor for the class.
     */
    public GetAssociateTasksResponse() {
    }

    /**
     * Indicates whether the request was successful or not.
     *
     * @return boolean
     */
    public boolean isRequestSucceeded() {
        return requestSucceeded;
    }

    /**
     * Set request Succeeded
     *
     * @param requestSucceeded
     */
    public void setRequestSucceeded(boolean requestSucceeded) {
        this.requestSucceeded = requestSucceeded;
    }

    /**
     * HTTP status code
     *
     * @return int
     */
    public int getStatus() {
        return status;
    }

    /**
     * Set status
     *
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * status message
     *
     * @return string
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
     * Get all task associated with an associate
     *
     * @return list of tasks
     */
    public List<TaskDto> getTasks() {
        return tasks;
    }

    /**
     * Set Tasks
     *
     * @param tasks
     */
    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }

    /**
     * toString representation of the class
     *
     * @return string
     */

    @Override
    public String toString() {
        return "GetAssociateTasksResponse{" +
                "tasks=" + tasks +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", requestSucceeded=" + requestSucceeded +
                '}';
    }
}

