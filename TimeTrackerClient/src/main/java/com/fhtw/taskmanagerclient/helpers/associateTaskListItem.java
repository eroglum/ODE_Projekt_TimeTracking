package com.fhtw.taskmanagerclient.helpers;

/**

 The class represents an item in the task list that is displayed in the ManagerPanel.

 It contains the name of the associate, the task, the date when the task was added and the hours spent on the task.
 */
public class associateTaskListItem {

    /** The name of the associate */
    private String associateName;
     /** The task */
    private String employeeTask;
     /** The date when the task was added */
    private String employeeDateFrom;
     /** The hours spent on the task */
    private double employeeHoursSpent;

    /**

     Constructor for the class
     @param associateName the name of the associate
     @param employeeTask the task
     @param employeeDateFrom the date when the task was added
     @param employeeHoursSpent the hours spent on the task
     */

    public associateTaskListItem(String associateName, String employeeTask, String employeeDateFrom, double employeeHoursSpent) {
        this.associateName = associateName;
        this.employeeTask = employeeTask;
        this.employeeDateFrom = employeeDateFrom;
        this.employeeHoursSpent = employeeHoursSpent;
    }

    public String getAssociateName() {
        return associateName;
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

    public void setAssociateName(String associateName) {
        this.associateName = associateName;
    }

    public void setEmployeeTask(String employeeTask) {
        this.employeeTask = employeeTask;
    }

    public void setEmployeeDateFrom(String employeeDateFrom) {
        this.employeeDateFrom = employeeDateFrom;
    }

    public void setEmployeeHoursSpent(double employeeHoursSpent) {
        this.employeeHoursSpent = employeeHoursSpent;
    }
}
