package com.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
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
    private Date date;

    @Column
    private int days;

    @Column
    private double amount;

    @Column
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
}
