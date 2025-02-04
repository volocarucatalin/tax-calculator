package com.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column
    private String month;

    @Column
    private int numberOfDays;

    @Column
    private double dayRate;

    @Column
    private int status;
}
