package org.fhtw.dto;


/**
 * DTO (data transfer object) class for {@link org.fhtw.entity.Task}
 * It is used for transferring data between layers and over the network.
 * The TaskDto class represents a task an employee has completed, including the task name, date it was completed, and hours spent on the task.
 */
public class TaskDto {
    private String employeeTask;
    private String employeeDateFrom;
    private double employeeHoursSpent;

    /**
     * Creates a new instance of the TaskDto class.
     */
    public TaskDto() {
    }

    /**
     * Gets the name of the task.
     *
     * @return The name of the task.
     */
    public String getEmployeeTask() {
        return employeeTask;
    }

    /**
     * Sets the name of the task.
     *
     * @param employeeTask The new name of the task.
     */
    public void setEmployeeTask(String employeeTask) {
        this.employeeTask = employeeTask;
    }

    /**
     * Gets the date the task was completed.
     *
     * @return The date the task was completed.
     */
    public String getEmployeeDateFrom() {
        return employeeDateFrom;
    }

    /**
     * Sets the date the task was completed.
     *
     * @param employeeDateFrom The new date the task was completed.
     */
    public void setEmployeeDateFrom(String employeeDateFrom) {
        this.employeeDateFrom = employeeDateFrom;
    }

    /**
     * Gets the number of hours spent on the task.
     *
     * @return The number of hours spent on the task.
     */
    public double getEmployeeHoursSpent() {
        return employeeHoursSpent;
    }

    /**
     * Sets the number of hours spent on the task.
     *
     * @param employeeHoursSpent The new number of hours spent on the task.
     */
    public void setEmployeeHoursSpent(double employeeHoursSpent) {
        this.employeeHoursSpent = employeeHoursSpent;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return A string representation of the task.
     */
    @Override
    public String toString() {
        return "TaskDto{" +
                "employeeTask='" + employeeTask + '\'' +
                ", employeeDateFrom='" + employeeDateFrom + '\'' +
                ", employeeHoursSpent=" + employeeHoursSpent +
                '}';
    }
}
