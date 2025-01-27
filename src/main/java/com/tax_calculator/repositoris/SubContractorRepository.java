package com.tax_calculator.repositoris;

import com.tax_calculator.entities.SubContractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubContractorRepository extends JpaRepository<SubContractor, Integer> {

}
