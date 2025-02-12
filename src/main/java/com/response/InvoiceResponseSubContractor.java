package com.response;

import com.entities.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Builder
@AllArgsConstructor
@Data
public class InvoiceResponseSubContractor {
    private String companyName;
    private String jobName;
    private Date date;
    private int days;
    private double amount;
    private InvoiceStatus status;

    public InvoiceResponseSubContractor() {
    }
}
