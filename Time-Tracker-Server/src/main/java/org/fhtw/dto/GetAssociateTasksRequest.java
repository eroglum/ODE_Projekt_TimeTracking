package org.fhtw.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class representing a request for getting a list of tasks for an associate between a certain date range.
 *
 * @author Rama
 */
public class GetAssociateTasksRequest {
    private String startDate;
    private String endDate;
    private String token;

    /**
     * Constructs a GetAssociateTasksRequest object.
     *
     * @param startDate The start date in the format "yyyy-MM-dd"
     * @param endDate   The end date in the format "yyyy-MM-dd"
     * @param token     The token used to authenticate the request
     * @throws ParseException           if the startDate or endDate is not in the format "yyyy-MM-dd"
     * @throws IllegalArgumentException if the start date is not before the end date
     */
    public GetAssociateTasksRequest(String startDate, String endDate, String token) throws ParseException {
// Set the date format you expect the input to be in
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
// Parse the start and end date strings into Date objects

        if (!startDate.isEmpty() && endDate != null) {
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);
            // Check if the start date is before the end date
            if (start.before(end)) {
                this.startDate = startDate;
                this.endDate = endDate;
            } else if (start.equals(end)) {
                this.startDate = startDate;
                this.endDate = null;
            } else {
                throw new IllegalArgumentException("Start date must be before end date.");
            }
            this.token = token;
        }
    }

    /**
     * @return the start date of the date range
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @return the end date of the date range
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @return the token used to authenticate the request
     */
    public String getToken() {
        return token;
    }

}