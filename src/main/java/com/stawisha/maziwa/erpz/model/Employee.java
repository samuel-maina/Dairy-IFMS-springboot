package com.stawisha.maziwa.erpz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @Created 11/11/23 This class defines a system user
 * @version 1
 * @author samuel
 */
@Entity
@Table
@IdClass(EmployeeID.class)
public class Employee {

    @Id
    private String Id; //ECC-1-2023

    @Column
    private String firstName;

    @Column
    private String secondName;

    @Column(unique=true)
    private String phone;

    @Column
    private String identityNo;

    @Column
    private String email;

    @ManyToOne(cascade = CascadeType.ALL)
   
    @JoinColumn(name = "tenant")
    
    @Id
    private Tenant tenant;
    
    @OneToMany
    private List<Record> records;
    
    @OneToMany(mappedBy="employee",fetch=FetchType.EAGER)
    private List<EmployeeRoles> employeeRoles;

    @Column
    private String loginAccess;
    
    @Column(name = "enabled")
    private boolean enabled;  // is account enabled 
    
    @Column(name = "locked")
    private boolean locked; // is account locked
    

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public String getLoginAccess() {
        return loginAccess;
    }

    public void setLoginAccess(String loginAccess) {
        this.loginAccess = loginAccess;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public List<EmployeeRoles> getEmployeeRoles() {
        return employeeRoles;
    }

    public void setEmployeeRoles(List<EmployeeRoles> employeeRoles) {
        this.employeeRoles = employeeRoles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "Employee{" + "Id=" + Id + ", firstName=" + firstName + ", secondName=" + secondName + ", phone=" + phone + ", identityNo=" + identityNo + ", email=" + email + ", tenant=" + tenant + '}';
    }

    
   
    
    
    
}
