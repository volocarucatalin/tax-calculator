package com.services;

import com.repositoris.UserRepository;
import com.request.SubContractorRequest;
import com.entities.SubContractor;
import com.repositoris.SubContractorRepository;
import com.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubContractorService {

    private final SubContractorRepository subContractorRepository;
    private final UserRepository userRepository;

    @Autowired
    public SubContractorService(SubContractorRepository subContractorRepository, UserRepository userRepository) {
        this.subContractorRepository = subContractorRepository;
        this.userRepository = userRepository;
    }

    public List<SubContractor> getAllSubContractorsByContractorId(Integer contractorId) {
        return subContractorRepository.findAllByContractorId(contractorId);
    }

    public SubContractor getSubContractorById(int id) {
        Optional<SubContractor> subContractor = subContractorRepository.findById(id);
        if (subContractor.isPresent()) {
            return subContractor.get();
        } else {
            throw new RuntimeException("SubContractor not found");
        }
    }

    public SubContractor updateSubContractor(Integer subContractorId, String newSubContractorRequestUTR) {
        Optional<SubContractor> subContractor = subContractorRepository.findById(subContractorId);
        if (subContractor.isEmpty()) {
            return null;
        }
        subContractor.get().setUtr(newSubContractorRequestUTR);

        Optional<User> userOptional = userRepository.findById(subContractor.get().getUserId());
        if (userOptional.isEmpty()) {
            return null;
        }

        subContractorRepository.save(subContractor.get());
        userRepository.save(userOptional.get());
        return subContractor.get();
    }

    public void deleteById(int id) {
        Optional<SubContractor> subContractor = subContractorRepository.findById(id);
        if (subContractor.isPresent()) {
            subContractorRepository.deleteById(id);
        } else {
            throw new RuntimeException("SubContractor not found");
        }
    }

    public SubContractor getSubContractorByContractorId(Integer contractorId) {
        return subContractorRepository.findByContractorId(contractorId);
    }
}
