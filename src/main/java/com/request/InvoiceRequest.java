package com.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
public class InvoiceRequest implements Serializable {


    private int subContractorId;
    private String jobName;
    private Date date;
    private int days;
    private double amount;

}
