package com.controllers;

import com.request.InvoiceRequest;
import com.services.InvoiceService;import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/register")
    public void registerInvoice(@RequestBody InvoiceRequest invoiceRequest) {
          invoiceService.createInvoice(invoiceRequest);
    }
    @GetMapping("/{subContractorId}")
    public ResponseEntity<?> getInvoicesBySubContractorId(@PathVariable Integer subContractorId) {
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getInvoicesBySubContractorId(subContractorId));
    }


}
