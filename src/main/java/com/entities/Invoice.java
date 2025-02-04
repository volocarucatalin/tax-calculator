package com.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
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
