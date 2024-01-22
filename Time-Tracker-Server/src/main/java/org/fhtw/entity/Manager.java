package org.fhtw.entity;


import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "manager")
public class Manager {
    @Id
    @Column(name = "manager_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "credential_id", nullable = false)
    private Credential credential;

    @OneToMany(mappedBy = "manager")
    private List<Associate> associates;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;


    public List<Associate> getEmployees() {
        return associates;
    }

    public void setEmployees(List<Associate> associates) {
        this.associates = associates;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}

/*
    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "credential_id", nullable = false)
    private Credential credential;
 */