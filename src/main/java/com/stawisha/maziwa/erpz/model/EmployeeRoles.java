package com.stawisha.maziwa.erpz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author samuel
 */
@Entity
@Table
public class EmployeeRoles {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    private Employee employee;
    private Roles role;

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "EmployeeRoles{" + "Id=" + Id + ", employee=" + employee + ", role=" + role + '}';
    }

    
    
}
