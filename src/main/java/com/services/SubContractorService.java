package com.services;

import com.repositoris.UserRepository;
import com.request.SubContractorRequest;
import com.entities.SubContractor;
import com.repositoris.SubContractorRepository;
import com.response.SubContractorResponse;
import com.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<SubContractorResponse> getAllSubContractorsByContractorId(Integer contractorId) {
        List<SubContractorResponse> subContractorResponses = new ArrayList<>();
        List<SubContractor> subContractors = subContractorRepository.findAllByContractorId(contractorId);
        for (SubContractor subContractor : subContractors) {
            Optional<User> userOptional = userRepository.findById(subContractor.getUserId());
            if (userOptional.isEmpty()) {
                continue;
            }
            subContractorResponses.add(SubContractorResponse.builder()
                    .userId(subContractor.getUserId())
                    .utr(subContractor.getUtr())
                    .lastName(userOptional.get().getLastName())
                    .firstName(userOptional.get().getFirstName()).build());
        }
        return subContractorResponses;
    }

    public SubContractor getSubContractorById(int id) {
        Optional<SubContractor> subContractor = subContractorRepository.findById(id);
        if (subContractor.isPresent()) {
            return subContractor.get();
        } else {
            throw new RuntimeException("SubContractor not found");
        }
    }

    public SubContractorResponse updateSubContractor(Integer subContractorId, SubContractorRequest subContractorRequest) {
        SubContractor subContractor = subContractorRepository.findByUserId(subContractorId);
        if (subContractor == null) {
            return null;
        }
        subContractor.setUtr(subContractorRequest.getUtr());

        Optional<User> userOptional = userRepository.findById(subContractor.getUserId());
        if (userOptional.isEmpty()) {
            return null;
        }
        userOptional.get().setFirstName(subContractorRequest.getFirstName());
        userOptional.get().setLastName(subContractorRequest.getLastName());
        subContractorRepository.save(subContractor);
        userRepository.save(userOptional.get());
        return SubContractorResponse.builder()
                .userId(subContractor.getUserId())
                .utr(subContractor.getUtr())
                .lastName(userOptional.get().getLastName())
                .firstName(userOptional.get().getLastName()).build();
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
