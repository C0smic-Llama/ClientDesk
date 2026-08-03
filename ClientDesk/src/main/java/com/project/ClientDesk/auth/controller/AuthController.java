package com.project.ClientDesk.auth.controller;


import com.project.ClientDesk.auth.service.AuthenticationService;
import com.project.ClientDesk.dto.LoginRequestDTO;
import com.project.ClientDesk.dto.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentications api endpoints")
public class AuthController {

    private final AuthenticationService authenticationService;


    @Operation(summary = "Authentication and JWT generation")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid
            @RequestBody
            LoginRequestDTO requestDTO){
        LoginResponseDTO responseDTO = authenticationService.login(requestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

}
