package com.services;

import com.entities.SubContractor;
import com.repositoris.SubContractorRepository;
import com.repositoris.UserRepository;
import com.request.SubContractorRequest;
import com.response.SubContractorResponse;
import com.security.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubContractorServiceTest {

    @Mock
    private SubContractorRepository subContractorRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SubContractorService subContractorService;

    @Test
    public void testGetAllSubContractorsByContractorIdWhenSubContractorsExistThenReturnListOfSubContractorResponses() {
        // Arrange
        Integer contractorId = 1;
        SubContractor subContractor1 = new SubContractor(1, contractorId, "UTR1");
        SubContractor subContractor2 = new SubContractor(2, contractorId, "UTR2");
        List<SubContractor> subContractors = Arrays.asList(subContractor1, subContractor2);

        User user1 = User.builder().id(1).firstName("John").lastName("Doe").build();
        User user2 = User.builder().id(2).firstName("Jane").lastName("Smith").build();

        when(subContractorRepository.findAllByContractorId(contractorId)).thenReturn(subContractors);
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2)).thenReturn(Optional.of(user2));

        // Act
        List<SubContractorResponse> result = subContractorService.getAllSubContractorsByContractorId(contractorId);

        // Assert
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals("UTR1", result.get(0).getUtr());
        assertEquals("Jane", result.get(1).getFirstName());
        assertEquals("Smith", result.get(1).getLastName());
        assertEquals("UTR2", result.get(1).getUtr());
    }

    @Test
    public void testGetAllSubContractorsByContractorIdWhenNoSubContractorsExistThenReturnEmptyList() {
        // Arrange
        Integer contractorId = 1;
        when(subContractorRepository.findAllByContractorId(contractorId)).thenReturn(Collections.emptyList());

        // Act
        List<SubContractorResponse> result = subContractorService.getAllSubContractorsByContractorId(contractorId);

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    public void testGetAllSubContractorsByContractorIdWhenUserNotFoundThenSkipSubContractor() {
        // Arrange
        Integer contractorId = 1;
        SubContractor subContractor1 = new SubContractor(1, contractorId, "UTR1");
        SubContractor subContractor2 = new SubContractor(2, contractorId, "UTR2");
        List<SubContractor> subContractors = Arrays.asList(subContractor1, subContractor2);

        User user1 = User.builder().id(1).firstName("John").lastName("Doe").build();

        when(subContractorRepository.findAllByContractorId(contractorId)).thenReturn(subContractors);
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2)).thenReturn(Optional.empty());

        // Act
        List<SubContractorResponse> result = subContractorService.getAllSubContractorsByContractorId(contractorId);

        // Assert
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals("UTR1", result.get(0).getUtr());
    }

    @Test
    public void testGetSubContractorByIdWhenSubContractorIsFoundThenReturnSubContractor() {
        // Arrange
        int id = 1;
        SubContractor subContractor = new SubContractor(id, 1, "UTR1");
        when(subContractorRepository.findById(id)).thenReturn(Optional.of(subContractor));

        // Act
        SubContractor result = subContractorService.getSubContractorById(id);

        // Assert
        assertEquals(subContractor, result);
    }

    @Test
    public void testGetSubContractorByIdWhenSubContractorIsNotFoundThenThrowRuntimeException() {
        // Arrange
        int id = 1;
        when(subContractorRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> subContractorService.getSubContractorById(id));
    }

    @Test
    public void testUpdateSubContractorWhenSubContractorAndUserAreFoundThenReturnSubContractorResponse() {
        // Arrange
        Integer subContractorId = 1;
        SubContractorRequest subContractorRequest = new SubContractorRequest("John", "Doe", "UTR123");
        SubContractor subContractor = new SubContractor(subContractorId, 1, "UTR1");
        User user = User.builder().id(1).firstName("Jane").lastName("Smith").build();

        when(subContractorRepository.findByUserId(subContractorId)).thenReturn(subContractor);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act
        SubContractorResponse result = subContractorService.updateSubContractor(subContractorId, subContractorRequest);

        // Assert
        assertEquals(subContractorId, result.getUserId());
        assertEquals("Doe", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("UTR123", result.getUtr());
    }

    @Test
    public void testUpdateSubContractorWhenSubContractorIsNotFoundThenReturnNull() {
        // Arrange
        Integer subContractorId = 1;
        SubContractorRequest subContractorRequest = new SubContractorRequest("John", "Doe", "UTR123");

        when(subContractorRepository.findByUserId(subContractorId)).thenReturn(null);

        // Act
        SubContractorResponse result = subContractorService.updateSubContractor(subContractorId, subContractorRequest);

        // Assert
        assertNull(result);
    }

    @Test
    public void testUpdateSubContractorWhenUserIsNotFoundThenReturnNull() {
        // Arrange
        Integer subContractorId = 1;
        SubContractorRequest subContractorRequest = new SubContractorRequest("John", "Doe", "UTR123");
        SubContractor subContractor = new SubContractor(subContractorId, 1, "UTR1");

        when(subContractorRepository.findByUserId(subContractorId)).thenReturn(subContractor);
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        SubContractorResponse result = subContractorService.updateSubContractor(subContractorId, subContractorRequest);

        // Assert
        assertNull(result);
    }
}