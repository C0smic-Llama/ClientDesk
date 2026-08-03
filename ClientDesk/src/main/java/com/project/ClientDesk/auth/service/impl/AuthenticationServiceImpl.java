package com.project.ClientDesk.auth.service.impl;

import com.project.ClientDesk.auth.service.AuthenticationService;
import com.project.ClientDesk.dto.LoginRequestDTO;
import com.project.ClientDesk.dto.LoginResponseDTO;
import com.project.ClientDesk.security.jwt.JwtService;
import com.project.ClientDesk.security.model.CustomUserDetails;
import com.project.ClientDesk.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(loginRequestDTO.getEmail());

        String token = jwtService.generateToken(userDetails);
        return LoginResponseDTO.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(jwtExpiration)
                .userId(userDetails.getUser().getId())
                .firstName(userDetails.getUser().getFirstName())
                .lastName(userDetails.getUser().getLastName())
                .email(userDetails.getUser().getEmail())
                .role(userDetails.getUser().getRole().name())
                .build();
    }
}
