/**
 * The AddTaskRequest class is a data transfer object (DTO) used to model an API request to add a task to an employee.
 * The class includes fields for the token, employee task, employee date from and employee hours spent.
 * The class also includes a constructor that takes in 4 parameters, token, employee task, employee date from and employee hours spent and throws a ParseException if the date format is not valid.
 * The class also includes getter methods for each field.
 *
 * @author Muhammed
 * @version 1.0
 * @since version 1.0
 */
package org.fhtw.dto;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class that represents a request to add a task for an employee.
 */
public class AddTaskRequest {
    private final String token;
    private final String employeeTask;
    private final String employeeDateFrom;
    private final double employeeHoursSpent;

    /**
     * This constructor takes in 4 parameters, token, employee task, employee date from and employee hours spent
     *
     * @param token (String) a token of the user that makes the request
     * @param employeeTask (String) the task that is assigned to the employee
     * @param employeeDateFrom (String) the date from when the task is assigned to the employee
     * @param employeeHoursSpent (double) the hours that the employee spent on this task
     * @throws ParseException if the date format is not valid
     */
    public AddTaskRequest(String token, String employeeTask, String employeeDateFrom, double employeeHoursSpent) throws ParseException {
        // Set the date format you expect the input to be in
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(employeeDateFrom);
            this.token = token;
            this.employeeTask = employeeTask;
            this.employeeDateFrom = employeeDateFrom;
            this.employeeHoursSpent = employeeHoursSpent;
        } catch (ParseException e) {
            throw new ParseException(e.getMessage(), e.getErrorOffset());
        }
    }

    public String getToken() {
        return token;
    }


    public String getEmployeeTask() {
        return employeeTask;
    }


    public String getEmployeeDateFrom() {
        return employeeDateFrom;
    }


    public double getEmployeeHoursSpent() {
        return employeeHoursSpent;
    }


    @Override
    public String toString() {
        return "AddTaskRequest{" +
                "token='" + token + '\'' +
                ", employeeTask='" + employeeTask + '\'' +
                ", employeeDateFrom='" + employeeDateFrom + '\'' +
                ", employeeHoursSpent='" + employeeHoursSpent + '\'' +
                '}';
    }
}

/* TODO: First step of programm*/
// TODO: for the future we could make a credential table for associate and a credential table for managers
// TODO: then we could also support manager sign in and associate sign in
// query to credential to validate if the username and password exist
// query to credential to return the employee id
// query to employee id to return the employee row
/* TODO: second step of programm*/
// query to add task to a employee -> findbyid return the employee. create new list. merge the employee
/* TODO: third step of programm*/
// query to return all manager emploee rows


