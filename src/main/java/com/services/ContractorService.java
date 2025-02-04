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

    public List<Contractor> getAllContractors() {
        return contractorRepository.findAll();
    }
}
