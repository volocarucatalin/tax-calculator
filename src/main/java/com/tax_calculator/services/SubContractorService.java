package com.tax_calculator.services;

import com.tax_calculator.Requests.SubContractorRequest;
import com.tax_calculator.entities.SubContractor;
import com.tax_calculator.repositoris.SubContractorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubContractorService {

    private  final SubContractorRepository subContractorRepository;

    public SubContractorService(SubContractorRepository subContractorRepository) {
        this.subContractorRepository = subContractorRepository;
    }


    public void creteSubContractor(SubContractorRequest subContractorRequest) {
        SubContractor subContractor = new SubContractor();
        subContractor.setFirstName(subContractorRequest.getFirstName());
        subContractor.setLastName(subContractorRequest.getLastName());

        if(!subContractorRepository.existsById(subContractor.getId())) {
            subContractorRepository.save(subContractor);
        }else{
            System.out.println("SubContractor already exists");
        }
    }

    public List<SubContractor> getAllSubContractors() {
        return subContractorRepository.findAll();
    }
}
