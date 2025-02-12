package com.response;

import com.entities.InvoiceStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Builder
@AllArgsConstructor
@Data
public class InvoiceResponseContractor {

    private String firstName;
    private String lastName;
    private String utr;
    private String jobName;
    private Date date;
    private int days;
    private double amount;
    private InvoiceStatus status;

    public InvoiceResponseContractor() {

    }
}
