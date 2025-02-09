package com.services;

import com.entities.Invoice;
import com.entities.InvoiceStatus;
import com.entities.SubContractor;
import com.repositoris.InvoiceRepository;
import com.repositoris.SubContractorRepository;
import com.request.InvoiceRequest;
import com.response.SubContractorResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final SubContractorRepository subContractorRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, SubContractorRepository subContractorRepository) {
        this.invoiceRepository = invoiceRepository;
        this.subContractorRepository = subContractorRepository;
    }

    public void createInvoice(InvoiceRequest invoiceRequest) {
        SubContractor subContractor = subContractorRepository.findByUserId(invoiceRequest.getSubContractorId());
        if (subContractor == null) {
            throw new RuntimeException("SubContractor not found");
        }
        Invoice invoice = new Invoice();
        invoice.setContractorId(subContractor.getContractorId());
        invoice.setSubContractorId(subContractor.getUserId());
        invoice.setDays(invoiceRequest.getDays());
        invoice.setAmount(invoiceRequest.getAmount());
        invoice.setDate(invoiceRequest.getDate());
        invoice.setJobName(invoiceRequest.getJobName());
        invoice.setStatus(InvoiceStatus.PENDING);
        invoiceRepository.save(invoice);
    }

    public List<Invoice> getInvoicesBySubContractorId(Integer subContractorId) {
        return invoiceRepository.findAllBySubContractorId(subContractorId);
    }
}
