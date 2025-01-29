package com.controllers;

import com.request.SubContractorRequest;
import com.services.SubContractorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
