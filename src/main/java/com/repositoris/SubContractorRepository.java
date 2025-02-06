package com.repositoris;

import com.entities.SubContractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubContractorRepository extends JpaRepository<SubContractor, Integer> {

   List< SubContractor> findAllByContractorId(Integer contractorId);


}
