package com.stawisha.maziwa.erpz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class defines a member object
 *
 * @create 11/11/23
 * @version 1
 * @author samuel
 */
@Entity
@Table
@IdClass(MemberID.class)
public class Member {

    @Id
    @Column
    private String Id;

    @Column
    private String firstName;

    @Column
    private String secondName;

    @Column
    private String address;

    @Column
    private String IdentityNo;

    @Column
    private String phone;
@JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tenant")
    @Id
    private Tenant tenant;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Record> records;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentityNo() {
        return IdentityNo;
    }

    public void setIdentityNo(String IdentityNo) {
        this.IdentityNo = IdentityNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

}
