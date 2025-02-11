package com.controllers;

import com.request.SubContractorRequest;
import com.response.SubContractorResponse;
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

    @GetMapping("/{contractorId}")
    public ResponseEntity<?> getSubContractorsByContractorId(@PathVariable Integer contractorId) {
        return ResponseEntity.status(HttpStatus.OK).body(subContractorService.getAllSubContractorsByContractorId(contractorId));
    }

    @GetMapping("get-sub/{contractorId}")
    public ResponseEntity<?> getSubContractorByContractorId(@PathVariable Integer contractorId) {
        if (subContractorService.getSubContractorById(contractorId) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(subContractorService.getSubContractorByContractorId(contractorId));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SubContractor not found");
        }
    }

    @PutMapping("/{subContractorId}")
    public ResponseEntity<?> updateSubContractor(@PathVariable Integer subContractorId, @RequestBody SubContractorRequest subContractorRequest) {
        SubContractorResponse response = subContractorService.updateSubContractor(subContractorId, subContractorRequest);
        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SubContractor not found");
        }
    }

    @DeleteMapping("sub/delete/{id}")
    public ResponseEntity<?> deleteSubContractor(@PathVariable int id) {
        if (subContractorService.getSubContractorById(id) != null) {
            subContractorService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("SubContractor has been deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SubContractor not found");
        }
    }
}
