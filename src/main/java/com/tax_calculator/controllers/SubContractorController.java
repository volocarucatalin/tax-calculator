package com.tax_calculator.controllers;

import com.tax_calculator.Requests.SubContractorRequest;
import com.tax_calculator.services.SubContractorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sub-contractors")
public class SubContractorController {
    private final SubContractorService subContractorService;


    public SubContractorController(SubContractorService subContractorService) {
        this.subContractorService = subContractorService;
    }

    @PostMapping("/sub/add")
    public void createSubContractor(@RequestBody SubContractorRequest subContractorRequest) {
        subContractorService.creteSubContractor(subContractorRequest);
    }

    @GetMapping("/sub/get")
    public ResponseEntity<?> getSubContractors(){
        return ResponseEntity.status(HttpStatus.OK).body(subContractorService.getAllSubContractors());
    }
}
