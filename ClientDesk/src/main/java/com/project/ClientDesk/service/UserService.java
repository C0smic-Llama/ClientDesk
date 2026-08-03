package com.project.ClientDesk.service;

import com.project.ClientDesk.dto.UserRequestDTO;
import com.project.ClientDesk.dto.UserResponseDTO;
import com.project.ClientDesk.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {


    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);

    void deleteUser(Long id);

    UserResponseDTO getUserById(Long id);

    Page<UserResponseDTO> searchUsers(String keyword, Pageable pageable);

    Page<UserResponseDTO> getAllUsers(Pageable pageable);

    Page<UserResponseDTO> getUsersByRole(User.Role role, Pageable pageable);

    Page<UserResponseDTO> getUsersByStatus(boolean active, Pageable pageable);
    
}
