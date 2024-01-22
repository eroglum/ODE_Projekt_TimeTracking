package com.fhtw.taskmanagerclient.model.dto;

import java.text.ParseException;
import java.util.Date;

/**
 * A data transfer object class for holding the request information for getting all associates for a manager.
 */
public class GetAllAssociatesByManagerIdRequest {
    private String token;

    /**
     * Creates a new {@code GetAllAssociatesByManagerIdRequest} instance.
     *
     * @param token the manager's token
     * @throws ParseException           if the startDate or endDate cannot be parsed into a {@link Date} object
     * @throws IllegalArgumentException if the startDate is not before the endDate
     */
    public GetAllAssociatesByManagerIdRequest(String token) {
        this.token = token;
    }


    /**
     * Gets the manager's token.
     *
     * @return the manager's token
     */
    public String getToken() {
        return token;
    }
}