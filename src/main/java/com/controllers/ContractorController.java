package com.controllers;

import com.request.ContractorRequest;
import com.services.ContractorService;
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

    @PostMapping("/con/add")
    public void createContractor(@RequestBody ContractorRequest contractorRequest) throws IOException {
        contractorService.createContractor(contractorRequest);
    }

    @PutMapping("con/update/{id}")
    public void updateContractor(@PathVariable int id, @RequestBody ContractorRequest contractorRequest) throws IOException {

    }
}
