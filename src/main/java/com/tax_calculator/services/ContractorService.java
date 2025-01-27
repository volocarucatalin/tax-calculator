package com.tax_calculator.services;

import com.tax_calculator.Requests.ContractorRequest;
import com.tax_calculator.entities.Contractor;
import com.tax_calculator.repositoris.ContractorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class ContractorService {

    private final ContractorRepository contractorRepository;

    public ContractorService(ContractorRepository contractorRepository) {
        this.contractorRepository = contractorRepository;
    }

    public void createContractor(ContractorRequest contractorRequest) throws IOException {
        
        Contractor contractor = new Contractor();
        contractor.setName(contractorRequest.getName());
        contractor.setAddress(contractorRequest.getAddress());
       contractor.setEmail(contractorRequest.getEmail());
       contractor.setPassword(contractorRequest.getPassword());
        if(contractor.equals(contractorRepository.findById(contractor.getId()))){
            System.out.println("Contractor already exists");
        }else{
            contractorRepository.save(contractor);
        }

    }
}
