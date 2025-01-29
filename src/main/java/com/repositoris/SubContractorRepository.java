package com.repositoris;

import com.entities.SubContractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubContractorRepository extends JpaRepository<SubContractor, Integer> {

}
