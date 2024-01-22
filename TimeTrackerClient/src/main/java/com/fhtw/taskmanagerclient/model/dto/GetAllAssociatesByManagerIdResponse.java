package com.fhtw.taskmanagerclient.model.dto;

import java.util.List;

/**
 * This class is a data transfer object (DTO) used to return the response of a request to get all associates by a manager's ID.
 * <p>
 * The response includes the status of the request, a message describing the outcome of the request, a flag indicating if the request succeeded or failed, and a list of {@link AssociateDto} objects representing the associates.
 */
public class GetAllAssociatesByManagerIdResponse {
    private int status;
    private String message;
    private boolean requestSucceeded = false;
    List<AssociateDto> associates;

    /**
     * Constructs a new GetAllAssociatesByManagerIdResponse object with default values.
     */
    public GetAllAssociatesByManagerIdResponse() {
    }

    /**
     * Returns the status of the request.
     *
     * @return an int representing the status of the request
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of the request.
     *
     * @param status an int representing the status of the request
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Returns the message describing the outcome of the request.
     *
     * @return a string representing the message describing the outcome of the request
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
     * Returns a flag indicating if the request succeeded or failed.
     *
     * @return a boolean representing a flag indicating if the request succeeded or failed
     */
    public boolean isRequestSucceeded() {
        return requestSucceeded;
    }

    /**
     * Sets the flag indicating if the request succeeded or failed.
     *
     * @param requestSucceeded a boolean representing a flag indicating if the request succeeded or failed
     */
    public void setRequestSucceeded(boolean requestSucceeded) {
        this.requestSucceeded = requestSucceeded;
    }

    /**
     * Returns the list of {@link AssociateDto} objects representing the associates.
     *
     * @return a list of {@link AssociateDto} objects representing the associates
     */
    public List<AssociateDto> getAssociates() {
        return associates;
    }

    /**
     * Sets the list of {@link AssociateDto} objects representing the associates.
     *
     * @param associates a list of {@link AssociateDto} objects representing the associates
     */
    public void setAssociates(List<AssociateDto> associates) {
        this.associates = associates;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "GetAllAssociatesByManagerIdResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", requestSucceeded=" + requestSucceeded +
                ", associates=" + associates +
                '}';
    }
}
