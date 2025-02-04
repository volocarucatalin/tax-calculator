package com.controllers;

import com.request.ContractorRequest;
import com.services.ContractorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/contractors")
public class ContractorController {

    private final ContractorService contractorService ;

    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }
    @PutMapping("con/update/{id}")
    public void updateContractor(@PathVariable int id, @RequestBody ContractorRequest contractorRequest) throws IOException {

    }

    @GetMapping("con/get/all")
    public ResponseEntity<?> getAllContractors(){
        return ResponseEntity.status(HttpStatus.OK).body(contractorService.getAllContractors());
    }
}
