package com.services;

import com.entities.Contractor;
import com.repositoris.ContractorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ContractorService {

    private final ContractorRepository contractorRepository;

    public ContractorService(ContractorRepository contractorRepository) {
        this.contractorRepository = contractorRepository;
    }

    public List<Contractor> getAllContractors() {
        return contractorRepository.findAll();
    }

    public Optional<Contractor> findById(int contractorId) {
        return contractorRepository.findById(contractorId);
    }
}
