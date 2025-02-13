package com.services;

import com.entities.Contractor;
import com.entities.Invoice;
import com.entities.InvoiceStatus;
import com.entities.SubContractor;
import com.repositoris.InvoiceRepository;
import com.repositoris.SubContractorRepository;
import com.repositoris.UserRepository;
import com.request.InvoiceRequest;
import com.response.InvoiceResponseContractor;
import com.response.InvoiceResponseSubContractor;
import com.response.SubContractorResponse;
import com.security.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final SubContractorRepository subContractorRepository;
    private final UserRepository userRepository;
    private final ContractorService contractorService;

    public InvoiceService(InvoiceRepository invoiceRepository, SubContractorRepository subContractorRepository, UserRepository userRepository, ContractorService contractorService) {
        this.invoiceRepository = invoiceRepository;
        this.subContractorRepository = subContractorRepository;
        this.userRepository = userRepository;
        this.contractorService = contractorService;
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

    public List<InvoiceResponseSubContractor> getInvoicesBySubContractorId(Integer subContractorId) {
        List<InvoiceResponseSubContractor> invoiceResponseSubContractors = new ArrayList<>();
        if (subContractorId == null) {
            return null;
        } else {
            for (Invoice invoice : invoiceRepository.findAllBySubContractorId(subContractorId)) {
                InvoiceResponseSubContractor invoiceResponseSubContractor = new InvoiceResponseSubContractor();
                Optional<Contractor> contractor = contractorService.findById(invoice.getContractorId());
                contractor.ifPresent(value -> invoiceResponseSubContractor.setCompanyName(value.getCompanyName()));
                invoiceResponseSubContractor.setInvoiceId(invoice.getId());
                invoiceResponseSubContractor.setAmount(invoice.getAmount());
                invoiceResponseSubContractor.setDays(invoice.getDays());
                invoiceResponseSubContractor.setDate(invoice.getDate());
                invoiceResponseSubContractor.setJobName(invoice.getJobName());
                invoiceResponseSubContractor.setStatus(invoice.getStatus());
                invoiceResponseSubContractors.add(invoiceResponseSubContractor);
            }
        }
        return invoiceResponseSubContractors;
    }

    public List<InvoiceResponseContractor> getInvoicesByContractorId(Integer contractorId) {
        List<InvoiceResponseContractor> invoiceResponseContractors = new ArrayList<>();
        if (contractorId == null) {
            return null;
        } else {
            for (Invoice invoice : invoiceRepository.findAllByContractorId(contractorId)) {
                SubContractor subContractor = subContractorRepository.findByUserId(invoice.getSubContractorId());
                InvoiceResponseContractor invoiceResponseContractor = new InvoiceResponseContractor();
                invoiceResponseContractor.setUtr(subContractor.getUtr());
                User user = userRepository.findById(invoice.getSubContractorId()).get();
                invoiceResponseContractor.setInvoiceId(invoice.getId());
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

    public boolean updateInvoice(Integer invoiceId, String status) {
            Optional<Invoice> invoice = invoiceRepository.findById(invoiceId);
            if (invoice.isPresent()) {
                invoice.get().setStatus(InvoiceStatus.valueOf(status));
                invoiceRepository.save(invoice.get());
                return true;
            }

        return false;
    }

    public boolean deletePendingInvoice(Integer id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow();
            if (invoice.getStatus() == InvoiceStatus.PENDING){
                invoiceRepository.deleteById(id);
                return true;
            }
            return false;
    }
}
