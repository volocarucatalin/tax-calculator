package com.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity(name = "Invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "subcontractor_id")
    private int subContractorId;

    @Column(name = "contractor_id")
    private int contractorId;

    @Column(name = "job_name")
    private String jobName;

    @Column
    private LocalDate date;

    @Column
    private int days;

    @Column
    private double amount;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
}
