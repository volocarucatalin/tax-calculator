package com.auth;

import com.entities.Contractor;
import com.entities.SubContractor;
import com.repositoris.ContractorRepository;
import com.repositoris.SubContractorRepository;
import com.repositoris.UserRepository;
import com.security.Role;
import com.security.User;
import com.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);
    private final UserRepository userRepository;
    private final ContractorRepository contractorRepository;
    private final SubContractorRepository subcontractorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegContrReq request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CONTRACTOR)
                .build();
        userRepository.save(user);

        Contractor contractor = contractorRepository.save(new Contractor(user.getId(), "companyname", "address"));
        LOGGER.info("We create contract with used id:{}", contractor.getUserId());

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    @Transactional
    public AuthenticationResponse register(RegSubContractReq request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.SUBCONTRACTOR)
                .build();
        userRepository.save(user);

            SubContractor subContractor = subcontractorRepository.save(new SubContractor(user.getId(), request.getContractorId(), request.getUtr()));
        LOGGER.info("We create sub-contract with used id:{} for contractor with id:{}", subContractor.getUserId(), subContractor.getContractorId());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                ));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
