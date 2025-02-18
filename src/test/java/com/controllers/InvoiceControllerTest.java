package com.controllers;

import com.request.InvoiceRequest;
import com.services.InvoiceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @Test
    public void testRegisterInvoiceWhenValidRequestThenReturnOk() throws Exception {
        // Arrange
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setSubContractorId(1);
        invoiceRequest.setJobName("Job Name");
        invoiceRequest.setDate((java.sql.Date.valueOf(LocalDate.now())));
        invoiceRequest.setDays(5);
        invoiceRequest.setAmount(1000.0);

        // Act & Assert
        mockMvc.perform(post("/invoices/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"subContractorId\":1,\"jobName\":\"Job Name\",\"date\":\"2023-10-10\",\"days\":5,\"amount\":1000.0}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetInvoicesBySubContractorIdWhenValidIdThenReturnInvoices() throws Exception {
        // Arrange
        Integer subContractorId = 1;
        Mockito.when(invoiceService.getInvoicesBySubContractorId(subContractorId)).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/invoices/sub-contractor/{subContractorId}", subContractorId))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testGetInvoicesByContractorIdWhenValidIdThenReturnInvoices() throws Exception {
        // Arrange
        Integer contractorId = 1;
        Mockito.when(invoiceService.getInvoicesByContractorId(contractorId)).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/invoices/contractor/{contractorId}", contractorId))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testUpdateStatusWhenValidIdAndStatusThenReturnSuccessMessage() throws Exception {
        // Arrange
        Integer invoiceId = 1;
        String status = "APPROVED";
        Mockito.when(invoiceService.updateInvoice(invoiceId, status)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(put("/invoices/{invoiceId}", invoiceId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"APPROVED\""))
                .andExpect(status().isOk())
                .andExpect(content().string("Invoice has been updated"));
    }

    @Test
    public void testDeleteInvoiceWhenValidIdThenReturnSuccessMessage() throws Exception {
        // Arrange
        Integer id = 1;
        Mockito.when(invoiceService.deletePendingInvoice(id)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/invoices/{Id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Invoice has been deleted"));
    }
}
