package com.stawisha.maziwa.erpz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stawisha.maziwa.erpz.APILoggers.ExpireLicenseException;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * File-name Tenant.java A tenant describes a top level owner in a multi-tenant
 * application setup. In this type of application the data is segmented across
 * the tenant lines. created - 11/11/23
 *
 * @version 1
 * @author samuel
 */
@Entity
@Table
public class Tenant {
    
    public Tenant(){
    System.out.println(Id);
    }

    @Id
    private String Id;

    @Column
    private String name;

    @Column
    private String location;

    @Column
    private String email;

    @Column
    private String phone;
    
    @JsonIgnore
    @OneToMany(mappedBy = "tenant")
    private List<Member> members;
    
    @JsonIgnore
    @OneToMany(mappedBy = "tenant")
    private List<Employee> employees;
    
    @JsonIgnore
    @OneToMany(mappedBy = "tenant")
    private List<License> licenses;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }
    

}
