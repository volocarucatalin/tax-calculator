package com.controllers;

import com.request.SubContractorRequest;
import com.services.SubContractorService;
import jakarta.servlet.MultipartConfigElement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sub-contractors")
public class SubContractorController {
    private final SubContractorService subContractorService;
    private final MultipartConfigElement multipartConfigElement;


    public SubContractorController(SubContractorService subContractorService, MultipartConfigElement multipartConfigElement) {
        this.subContractorService = subContractorService;
        this.multipartConfigElement = multipartConfigElement;
    }

    @GetMapping("/{contractorId}")
    public ResponseEntity<?> getSubContractorsByContractorId(@PathVariable Integer contractorId ) {
        return ResponseEntity.status(HttpStatus.OK).body(subContractorService.getAllSubContractorsByContractorId(contractorId));
    }

    @GetMapping("sub/get/{id}")
    public ResponseEntity<?> getSubContractorById(@PathVariable int id) {
        if(subContractorService.getSubContractorById(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(subContractorService.getSubContractorById(id));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SubContractor not found");
        }
    }

    @PutMapping("sub/put/{id}")
    public ResponseEntity<?> updateSubContractor(@PathVariable int id, @RequestBody SubContractorRequest subContractorRequest) {
        if(subContractorService.getSubContractorById(id) != null) {
            subContractorService.updateSubContractor(id,subContractorRequest);
            return ResponseEntity.status(HttpStatus.OK).body("SubContractor has been update");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SubContractor not found");
        }
    }

    @DeleteMapping("sub/delete/{id}")
    public ResponseEntity<?> deleteSubContractor(@PathVariable int id) {
        if(subContractorService.getSubContractorById(id) != null) {
            subContractorService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("SubContractor has been deleted");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SubContractor not found");
        }
    }
}
