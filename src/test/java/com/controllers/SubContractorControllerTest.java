package com.controllers;

import com.request.SubContractorRequest;
import com.response.SubContractorResponse;
import com.services.SubContractorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubContractorController.class)
public class SubContractorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubContractorService subContractorService;

    private SubContractorResponse subContractorResponse;
    private SubContractorRequest subContractorRequest;

    @BeforeEach
    public void setup() {
        subContractorResponse = SubContractorResponse.builder()
                .userId(1)
                .firstName("John")
                .lastName("Doe")
                .utr("1234567890")
                .build();

        subContractorRequest = new SubContractorRequest("John", "Doe", "1234567890");
    }

    @Test
    public void testGetSubContractorsByContractorIdWhenServiceReturnsNonEmptyListThenReturn200AndList() throws Exception {
        List<SubContractorResponse> subContractorResponses = Arrays.asList(subContractorResponse);
        Mockito.when(subContractorService.getAllSubContractorsByContractorId(anyInt())).thenReturn(subContractorResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/sub-contractors/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userId").value(subContractorResponse.getUserId()))
                .andExpect(jsonPath("$[0].firstName").value(subContractorResponse.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(subContractorResponse.getLastName()))
                .andExpect(jsonPath("$[0].utr").value(subContractorResponse.getUtr()));
    }

    @Test
    public void testGetSubContractorByContractorIdWhenServiceReturnsNullThenReturn404AndMessage() throws Exception {
        Mockito.when(subContractorService.getSubContractorById(anyInt())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/sub-contractors/get-sub/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("SubContractor not found"));
    }

    @Test
    public void testUpdateSubContractorWhenServiceSuccessfullyUpdatesThenReturn200AndResponse() throws Exception {
        Mockito.when(subContractorService.updateSubContractor(anyInt(), any(SubContractorRequest.class))).thenReturn(subContractorResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/sub-contractors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"utr\":\"1234567890\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(subContractorResponse.getUserId()))
                .andExpect(jsonPath("$.firstName").value(subContractorResponse.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(subContractorResponse.getLastName()))
                .andExpect(jsonPath("$.utr").value(subContractorResponse.getUtr()));
    }

    @Test
    public void testDeleteSubContractorWhenServiceReturnsNullThenReturn404AndMessage() throws Exception {
        Mockito.when(subContractorService.getSubContractorById(anyInt())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/sub-contractors/sub/delete/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("SubContractor not found"));
    }
}
