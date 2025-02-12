package com.services;

import com.entities.Invoice;
import com.entities.InvoiceStatus;
import com.entities.SubContractor;
import com.repositoris.InvoiceRepository;
import com.repositoris.SubContractorRepository;
import com.repositoris.UserRepository;
import com.request.InvoiceRequest;
import com.response.InvoiceResponseContractor;
import com.response.SubContractorResponse;
import com.security.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final SubContractorRepository subContractorRepository;
    private final UserRepository userRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, SubContractorRepository subContractorRepository, UserRepository userRepository) {
        this.invoiceRepository = invoiceRepository;
        this.subContractorRepository = subContractorRepository;
        this.userRepository = userRepository;
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

    public List<InvoiceResponseContractor> getInvoicesByContractorId(Integer contractorId) {
        List<InvoiceResponseContractor> invoiceResponseContractors = new ArrayList<>();
        if (contractorId == null){
            return null;
        }else {
            for (Invoice invoice : invoiceRepository.findAllByContractorId(contractorId)) {
                SubContractor subContractor = subContractorRepository.findByUserId(invoice.getSubContractorId());
                InvoiceResponseContractor invoiceResponseContractor = new InvoiceResponseContractor();
                invoiceResponseContractor.setUtr(subContractor.getUtr());
                User user = userRepository.findById(invoice.getSubContractorId()).get();
                invoiceResponseContractor.setFirstName(user.getFirstName());
                invoiceResponseContractor.setLastName(user.getLastName());
                invoiceResponseContractor.setAmount(invoice.getAmount());
                invoiceResponseContractor.setDays(invoice.getDays());
                invoiceResponseContractor.setDate(invoice.getDate());
                invoiceResponseContractor.setJobName(invoice.getJobName());
                invoiceResponseContractor.setStatus(invoice.getStatus());
                invoiceResponseContractors.add(invoiceResponseContractor);

            }
        }

            return invoiceResponseContractors;
    }
}
