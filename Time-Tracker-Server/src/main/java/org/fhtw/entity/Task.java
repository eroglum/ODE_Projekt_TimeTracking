package org.fhtw.entity;


import javax.persistence.*;


@Entity
@Table(name = "task")
public class Task {
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_task")
    private String employeeTask;

    @Column(name = "employee_date_from")
    private String employeeDateFrom;

    @Column(name = "employee_hours_spent")
    private double employeeHoursSpent;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "employee_id")
    private Associate associate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeTask() {
        return employeeTask;
    }

    public void setEmployeeTask(String employeeTask) {
        this.employeeTask = employeeTask;
    }

    public String getEmployeeDateFrom() {
        return employeeDateFrom;
    }

    public void setEmployeeDateFrom(String employeeDateFrom) {
        this.employeeDateFrom = employeeDateFrom;
    }

    public double getEmployeeHoursSpent() {
        return employeeHoursSpent;
    }

    public void setEmployeeHoursSpent(double employeeHoursSpent) {
        this.employeeHoursSpent = employeeHoursSpent;
    }

    public Associate getEmployee() {
        return associate;
    }

    public void setEmployee(Associate associate) {
        this.associate = associate;
    }


}
