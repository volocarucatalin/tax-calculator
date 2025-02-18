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
import com.security.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private SubContractorRepository subContractorRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ContractorService contractorService;

    @InjectMocks
    private InvoiceService invoiceService;

    @Test
    public void testCreateInvoiceWhenSubContractorNotFoundThenThrowRuntimeException() {
        // Arrange
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setSubContractorId(1);
        when(subContractorRepository.findByUserId(1)).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            invoiceService.createInvoice(invoiceRequest);
        });
        assertEquals("SubContractor not found", exception.getMessage());
    }

    @Test
    public void testCreateInvoiceWhenSubContractorFoundThenSaveInvoice() {
        // Arrange
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setSubContractorId(1);
        invoiceRequest.setDays(5);
        invoiceRequest.setAmount(1000.0);
        invoiceRequest.setDate((java.sql.Date.valueOf(LocalDate.now())));
        invoiceRequest.setJobName("Job1");

        SubContractor subContractor = new SubContractor();
        subContractor.setUserId(1);
        subContractor.setContractorId(2);

        when(subContractorRepository.findByUserId(1)).thenReturn(subContractor);

        // Act
        invoiceService.createInvoice(invoiceRequest);

        // Assert
        verify(invoiceRepository, times(1)).save(any(Invoice.class));
    }

    @Test
    public void testGetInvoicesBySubContractorIdWhenSubContractorIdIsNullThenReturnNull() {
        // Act
        List<InvoiceResponseSubContractor> result = invoiceService.getInvoicesBySubContractorId(null);

        // Assert
        assertNull(result);
    }

    @Test
    public void testGetInvoicesBySubContractorIdWhenSubContractorIdIsNotNullThenReturnListOfInvoiceResponseSubContractor() {
        // Arrange
        Integer subContractorId = 1;
        List<Invoice> invoices = new ArrayList<>();
        Invoice invoice = new Invoice();
        invoice.setId(1);
        invoice.setContractorId(2);
        invoice.setSubContractorId(1);
        invoice.setDays(5);
        invoice.setAmount(1000.0);
        invoice.setDate(java.sql.Date.valueOf(LocalDate.now()));
        invoice.setJobName("Job1");
        invoice.setStatus(InvoiceStatus.PENDING);
        invoices.add(invoice);

        Contractor contractor = new Contractor();
        contractor.setUserId(2);
        contractor.setCompanyName("Company1");

        when(invoiceRepository.findAllBySubContractorId(subContractorId)).thenReturn(invoices);
        when(contractorService.findById(2)).thenReturn(Optional.of(contractor));

        // Act
        List<InvoiceResponseSubContractor> result = invoiceService.getInvoicesBySubContractorId(subContractorId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Company1", result.get(0).getCompanyName());
    }

    @Test
    public void testGetInvoicesByContractorIdWhenContractorIdIsNullThenReturnNull() {
        // Act
        List<InvoiceResponseContractor> result = invoiceService.getInvoicesByContractorId(null);

        // Assert
        assertNull(result);
    }

    @Test
    public void testGetInvoicesByContractorIdWhenContractorIdIsNotNullThenReturnListOfInvoiceResponseContractor() {
        // Arrange
        Integer contractorId = 1;
        List<Invoice> invoices = new ArrayList<>();
        Invoice invoice = new Invoice();
        invoice.setId(1);
        invoice.setContractorId(1);
        invoice.setSubContractorId(2);
        invoice.setDays(5);
        invoice.setAmount(1000.0);
        invoice.setDate(java.sql.Date.valueOf(LocalDate.now()));
        invoice.setJobName("Job1");
        invoice.setStatus(InvoiceStatus.PENDING);
        invoices.add(invoice);

        SubContractor subContractor = new SubContractor();
        subContractor.setUserId(2);
        subContractor.setUtr("UTR123");

        User user = new User();
        user.setId(2);
        user.setFirstName("John");
        user.setLastName("Doe");

        when(invoiceRepository.findAllByContractorId(contractorId)).thenReturn(invoices);
        when(subContractorRepository.findByUserId(2)).thenReturn(subContractor);
        when(userRepository.findById(2)).thenReturn(Optional.of(user));

        // Act
        List<InvoiceResponseContractor> result = invoiceService.getInvoicesByContractorId(contractorId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
    }

    @Test
    public void testUpdateInvoiceWhenInvoiceIsFoundThenReturnTrue() {
        // Arrange
        Integer invoiceId = 1;
        String status = "PAID";
        Invoice invoice = new Invoice();
        invoice.setId(invoiceId);
        invoice.setStatus(InvoiceStatus.PENDING);

        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        // Act
        boolean result = invoiceService.updateInvoice(invoiceId, status);

        // Assert
        assertTrue(result);
        assertEquals(InvoiceStatus.PAID, invoice.getStatus());
        verify(invoiceRepository, times(1)).save(invoice);
    }

    @Test
    public void testUpdateInvoiceWhenInvoiceIsNotFoundThenReturnFalse() {
        // Arrange
        Integer invoiceId = 1;
        String status = "PAID";

        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.empty());

        // Act
        boolean result = invoiceService.updateInvoice(invoiceId, status);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testDeletePendingInvoiceWhenInvoiceIsFoundAndStatusIsPendingThenReturnTrue() {
        // Arrange
        Integer invoiceId = 1;
        Invoice invoice = new Invoice();
        invoice.setId(invoiceId);
        invoice.setStatus(InvoiceStatus.PENDING);

        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        // Act
        boolean result = invoiceService.deletePendingInvoice(invoiceId);

        // Assert
        assertTrue(result);
        verify(invoiceRepository, times(1)).deleteById(invoiceId);
    }

    @Test
    public void testDeletePendingInvoiceWhenInvoiceIsFoundAndStatusIsNotPendingThenReturnFalse() {
        // Arrange
        Integer invoiceId = 1;
        Invoice invoice = new Invoice();
        invoice.setId(invoiceId);
        invoice.setStatus(InvoiceStatus.PAID);

        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        // Act
        boolean result = invoiceService.deletePendingInvoice(invoiceId);

        // Assert
        assertFalse(result);
        verify(invoiceRepository, never()).deleteById(invoiceId);
    }
}
