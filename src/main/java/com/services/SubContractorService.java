package com.services;

import com.request.SubContractorRequest;
import com.entities.SubContractor;
import com.repositoris.SubContractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubContractorService {

    private  final SubContractorRepository subContractorRepository;

    @Autowired
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

    public SubContractor getSubContractorById(int id) {
            Optional<SubContractor> subContractor = subContractorRepository.findById(id);
            if(subContractor.isPresent()) {
                return subContractor.get();
            }else{
               throw new RuntimeException("SubContractor not found");
            }
    }

    public void updateSubContractor(int id, SubContractorRequest subContractorRequest) {

        Optional<SubContractor> subContractor = subContractorRepository.findById(id);

        if(subContractor.isPresent()) {
            subContractor.get().setFirstName(subContractorRequest.getFirstName());
            subContractor.get().setLastName(subContractorRequest.getLastName());
            subContractor.get().setEmail(subContractorRequest.getEmail());
            subContractor.get().setPassword(subContractorRequest.getPassword());
        } else{
            throw new RuntimeException("SubContractor don't exists");
        }
    }

    public void deleteById(int id) {
        Optional<SubContractor> subContractor = subContractorRepository.findById(id);
        if(subContractor.isPresent()) {
            subContractorRepository.deleteById(id);
        }else {
            throw new RuntimeException("SubContractor not found");
        }
    }
}
