package com.services;

import com.entities.Invoice;
import com.repositoris.InvoiceRepository;
import com.request.InvoiceRequest;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public void createInvoice(InvoiceRequest invoiceRequest) {
            Invoice invoice = new Invoice();
            invoice.setSubContractorId(invoiceRequest.getSubContractorId());
            invoice.setDays(invoiceRequest.getDays());
            invoice.setAmount(invoiceRequest.getAmount());
            invoice.setDate(invoiceRequest.getDate());
            invoice.setJobName(invoiceRequest.getJobName());
            invoiceRepository.save(invoice);

    }
}
