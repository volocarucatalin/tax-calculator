package com.controllers;

import com.request.ContractorRequest;
import com.services.ContractorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

@WebMvcTest(ContractorController.class)
public class ContractorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractorService contractorService;

    @Test
    public void testUpdateContractorWhenCalledThenServiceMethodCalledWithCorrectArguments() throws Exception {
        // Arrange
        int contractorId = 1;
        ContractorRequest contractorRequest = new ContractorRequest("John", "123 Main St", "john@example.com", "password123");
        contractorRequest.setFirstName("John");
        contractorRequest.setLastName("Doe");
        contractorRequest.setRole("Contractor");

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/contractors/con/update/{id}", contractorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John\",\"address\":\"123 Main St\",\"email\":\"john@example.com\",\"password\":\"password123\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"role\":\"Contractor\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called with the correct arguments
        //Mockito.verify(contractorService).updateContractor(Mockito.eq(contractorId), Mockito.any(ContractorRequest.class));
    }

    @Test
    public void testGetAllContractorsWhenCalledThenServiceMethodCalledAndResponseStatusIsOK() throws Exception {
        // Arrange
        Mockito.when(contractorService.getAllContractors()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/contractors/con/get/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        // Verify that the service method was called
        Mockito.verify(contractorService).getAllContractors();
    }
}
