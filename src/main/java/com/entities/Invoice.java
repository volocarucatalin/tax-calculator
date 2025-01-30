package com.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "Invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String jobName;

    @JoinColumn
    @ManyToOne
    SubContractor subContractor;

    @JoinColumn
    @ManyToOne
    Contractor  contractor;

    @Column
    private String month;

    @Column
    private int numberOfDays;

    @Column
    private double dayRate;

    @Column
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public double getDayRate() {
        return dayRate;
    }

    public void setDayRate(double dayRate) {
        this.dayRate = dayRate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public SubContractor getSubContractor() {
        return subContractor;
    }

    public void setSubContractor(SubContractor subContractor) {
        this.subContractor = subContractor;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return id == invoice.id && numberOfDays == invoice.numberOfDays && Double.compare(dayRate, invoice.dayRate) == 0 && status == invoice.status && Objects.equals(jobName, invoice.jobName) && Objects.equals(subContractor, invoice.subContractor) && Objects.equals(contractor, invoice.contractor) && Objects.equals(month, invoice.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jobName, subContractor, contractor, month, numberOfDays, dayRate, status);
    }
}
