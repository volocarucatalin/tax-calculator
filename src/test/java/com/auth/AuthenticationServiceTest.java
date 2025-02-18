package com.auth;

import com.entities.Contractor;
import com.entities.SubContractor;
import com.repositoris.ContractorRepository;
import com.repositoris.SubContractorRepository;
import com.repositoris.UserRepository;
import com.security.Role;
import com.security.User;
import com.security.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ContractorRepository contractorRepository;

    @Mock
    private SubContractorRepository subcontractorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void testRegisterWhenContractorThenSuccess() {
        // Arrange
        RegContrReq request = RegContrReq.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .companyName("Doe Inc.")
                .address("123 Main St")
                .build();

        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("encodedPassword")
                .role(Role.CONTRACTOR)
                .build();

        Contractor contractor = new Contractor(1, "Doe Inc.", "123 Main St");

        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(contractorRepository.save(any(Contractor.class))).thenReturn(contractor);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        // Act
        AuthenticationResponse response = authenticationService.register(request);

        // Assert
        assertEquals("jwtToken", response.getToken());
        verify(userRepository).save(any(User.class));
        verify(contractorRepository).save(any(Contractor.class));
        verify(jwtService).generateToken(user);
    }

    @Test
    public void testRegisterWhenSubContractorThenSuccess() {
        // Arrange
        RegSubContractReq request = RegSubContractReq.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .password("password")
                .contractorId(1)
                .utr("UTR123")
                .build();

        User user = User.builder()
                .id(2)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .password("encodedPassword")
                .role(Role.SUBCONTRACTOR)
                .build();

        SubContractor subContractor = new SubContractor(2, 1, "UTR123");

        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(subcontractorRepository.save(any(SubContractor.class))).thenReturn(subContractor);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        // Act
        AuthenticationResponse response = authenticationService.register(request);

        // Assert
        assertEquals("jwtToken", response.getToken());
        assertEquals(2, response.getUserId());
        verify(userRepository).save(any(User.class));
        verify(subcontractorRepository).save(any(SubContractor.class));
        verify(jwtService).generateToken(user);
    }

    @Test
    public void testAuthenticateWhenUserThenSuccess() {
        // Arrange
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("john.doe@example.com")
                .password("password")
                .build();

        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("encodedPassword")
                .role(Role.CONTRACTOR)
                .build();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        // Act
        AuthenticationResponse response = authenticationService.authenticate(request);

        // Assert
        assertEquals("jwtToken", response.getToken());
        assertEquals(1, response.getUserId());
        assertEquals(Role.CONTRACTOR, response.getRole());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(request.getEmail());
        verify(jwtService).generateToken(user);
    }
}
