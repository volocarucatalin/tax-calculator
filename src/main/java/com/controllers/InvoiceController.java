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
    @GetMapping("/sub-contractor/{subContractorId}")
    public ResponseEntity<?> getInvoicesBySubContractorId(@PathVariable Integer subContractorId) {
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getInvoicesBySubContractorId(subContractorId));
    }

    @GetMapping("/contractor/{contractorId}")
    public ResponseEntity<?> getInvoicesByContractorId(@PathVariable Integer contractorId) {
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getInvoicesByContractorId(contractorId));
    }

    @PutMapping("/{invoiceId}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer invoiceId  , @RequestBody String status) {
        boolean response = invoiceService.updateInvoice(invoiceId,status);
        if(response){
            return ResponseEntity.status(HttpStatus.OK).body("Invoice has been updated");
        }else
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not found");

    }




}
