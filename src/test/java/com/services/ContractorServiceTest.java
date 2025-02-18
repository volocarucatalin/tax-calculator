package com.services;

import com.entities.Contractor;
import com.repositoris.ContractorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ContractorServiceTest {

    @Mock
    private ContractorRepository contractorRepository;

    @InjectMocks
    private ContractorService contractorService;

    @Test
    public void testGetAllContractorsWhenRepositoryReturnsListThenReturnList() {
        // Arrange
        Contractor contractor1 = new Contractor(1, "Company A", "Address A");
        Contractor contractor2 = new Contractor(2, "Company B", "Address B");
        List<Contractor> contractors = Arrays.asList(contractor1, contractor2);
        Mockito.when(contractorRepository.findAll()).thenReturn(contractors);

        // Act
        List<Contractor> result = contractorService.getAllContractors();

        // Assert
        assertEquals(contractors, result);
    }

    @Test
    public void testFindByIdWhenRepositoryReturnsContractorThenReturnContractor() {
        // Arrange
        Contractor contractor = new Contractor(1, "Company A", "Address A");
        Mockito.when(contractorRepository.findById(1)).thenReturn(Optional.of(contractor));

        // Act
        Optional<Contractor> result = contractorService.findById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(contractor, result.get());
    }

    @Test
    public void testFindByIdWhenRepositoryDoesNotFindContractorThenReturnEmptyOptional() {
        // Arrange
        Mockito.when(contractorRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Optional<Contractor> result = contractorService.findById(1);

        // Assert
        assertTrue(result.isEmpty());
    }
}
