package org.fhtw.entity;

import javax.persistence.*;

@Entity
@Table(name = "credential")
public class Credential {
    @Id
    @Column(name = "credential_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "employee_id")
    private Associate associate;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Associate getEmployee() {
        return associate;
    }

    public void setEmployee(Associate associate) {
        this.associate = associate;
    }

/*
    public Credential( String username, String password) {
        this.employee = employee;
        this.username = username;
        this.password = password;
    }

     */

    public Credential(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Credential() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
*/
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
