package org.fhtw.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "associate")
public class Associate {
    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "credential_id", nullable = false)
    private Credential credential;

    @OneToMany(mappedBy = "associate", cascade = CascadeType.PERSIST)
    private List<Task> tasks;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;


    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }


    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }


    public Credential getCredentials() {
        return credential;
    }

    public void setCredentials(Credential credential) {
        this.credential = credential;
    }


    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }


    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}


