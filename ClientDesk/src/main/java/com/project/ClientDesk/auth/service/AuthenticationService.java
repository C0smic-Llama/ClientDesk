package com.project.ClientDesk.auth.service;

import com.project.ClientDesk.dto.LoginRequestDTO;
import com.project.ClientDesk.dto.LoginResponseDTO;

public interface AuthenticationService {

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}
