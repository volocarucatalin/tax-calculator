package com.tax_calculator.controllers;

import com.tax_calculator.Requests.ContractorRequest;
import com.tax_calculator.services.ContractorService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/contractors")
public class ContractorController {

    private final ContractorService contractorService ;

    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    @RequestMapping("/con/add")
    public void createContractor(@RequestBody ContractorRequest contractorRequest) throws IOException {
        contractorService.createContractor(contractorRequest);
    }

}
