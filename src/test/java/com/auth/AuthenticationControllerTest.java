package com.auth;

import com.security.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void testRegisterWhenRequestIsValidThenReturnOkResponse() throws Exception {
        // Arrange
        RegContrReq request = RegContrReq.builder()
                .companyName("Test Company")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .address("123 Test St")
                .build();

        AuthenticationResponse response = AuthenticationResponse.builder()
                .token("test-token")
                .build();

        when(authenticationService.register(any(RegContrReq.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/auth/register/contractor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"companyName\":\"Test Company\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"password\":\"password\",\"address\":\"123 Test St\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"test-token\"}"));
    }

    @Test
    public void testRegisterSubContractorWhenRequestIsValidThenReturnOkResponse() throws Exception {
        // Arrange
        RegSubContractReq request = RegSubContractReq.builder()
                .contractorId(1)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .password("password")
                .utr("1234567890")
                .build();

        AuthenticationResponse response = AuthenticationResponse.builder()
                .token("test-token")
                .userId(1)
                .build();

        when(authenticationService.register(any(RegSubContractReq.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/auth/register/sub-contractor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contractorId\":1,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.com\",\"password\":\"password\",\"utr\":\"1234567890\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"test-token\",\"userId\":1}"));
    }

    @Test
    public void testAuthenticateWhenRequestIsValidThenReturnOkResponse() throws Exception {
        // Arrange
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("john.doe@example.com")
                .password("password")
                .build();

        AuthenticationResponse response = AuthenticationResponse.builder()
                .token("test-token")
                .userId(1)
                .role(Role.CONTRACTOR)
                .build();

        when(authenticationService.authenticate(any(AuthenticationRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"john.doe@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"test-token\",\"userId\":1,\"role\":\"CONTRACTOR\"}"));
    }
}
