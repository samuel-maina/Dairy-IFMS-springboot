package com.stawisha.maziwa.erpz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author samuel
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames={"member","date","tenant"})})

public class Record {

    public Record() {
    }
    
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date inserted;

    @Column
    private Date updated;

    @Column
    private double amount;

   @Column
   private LocalDate date;
   
   @Transient
   private Double sum;
   
    @OneToOne
    
    @JoinColumns({
        @JoinColumn(name = "member"),
        @JoinColumn(name = "tenant", insertable = true, updatable = true)})
    private Member member;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getInserted() {
        return inserted;
    }

    public void setInserted(Date inserted) {
        this.inserted = inserted;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public double getAmount() {
        return Double.parseDouble(new DecimalFormat("0.00").format(amount));
    }

    public void setAmount(double amount) {
        this.amount =Double.parseDouble(new DecimalFormat("0.00").format(amount));
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }


}
