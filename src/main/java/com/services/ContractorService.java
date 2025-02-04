package com.services;

import com.request.ContractorRequest;
import com.entities.Contractor;
import com.repositoris.ContractorRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class ContractorService {

    private final ContractorRepository contractorRepository;

    public ContractorService(ContractorRepository contractorRepository) {
        this.contractorRepository = contractorRepository;
    }

    public void createContractor(ContractorRequest contractorRequest) throws IOException {
        
        Contractor contractor = new Contractor();
        contractor.setCompanyName(contractorRequest.getName());
        contractor.setAddress(contractorRequest.getAddress());
       contractor.setEmail(contractorRequest.getEmail());
       contractor.setPassword(contractorRequest.getPassword());
        if(contractor.equals(contractorRepository.findById(contractor.getId()))){
            System.out.println("Contractor already exists");
        }else{
            contractorRepository.save(contractor);
        }

    }

    public List<Contractor> getAllContractors() {
        return contractorRepository.findAll();
    }
}
