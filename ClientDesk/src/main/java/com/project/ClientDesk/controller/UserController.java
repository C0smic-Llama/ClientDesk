package com.project.ClientDesk.controller;


import com.project.ClientDesk.dto.UserRequestDTO;
import com.project.ClientDesk.dto.UserResponseDTO;
import com.project.ClientDesk.dto.UserUpdateRequestDTO;
import com.project.ClientDesk.entity.User;
import com.project.ClientDesk.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "User Management")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Add a new User")
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid
            @RequestBody
            UserRequestDTO userRequestDTO){
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid
            @RequestBody UserUpdateRequestDTO userRequestDTO) {

        UserResponseDTO updatedUser =
                userService.updateUser(id, userRequestDTO);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDTO> deleteUser(
            @PathVariable
            Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable
            Long id){
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            Pageable pageable){
        Page<UserResponseDTO> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserResponseDTO>> searchUsers(
            @RequestParam
            String keyword,
            Pageable pageable){
        Page<UserResponseDTO> users = userService.searchUsers(keyword, pageable);
        return ResponseEntity.ok(users);

    }

    @GetMapping("/role")
    public ResponseEntity<Page<UserResponseDTO>> getUsersByRole(
            @RequestParam
            User.Role role,
            Pageable pageable){

        Page<UserResponseDTO> users = userService.getUsersByRole(role, pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/status")
    public ResponseEntity<Page<UserResponseDTO>> getUsersByStatus(
            @RequestParam
            boolean active,
            Pageable pageable){

        Page<UserResponseDTO> users = userService.getUsersByStatus(active, pageable);
        return ResponseEntity.ok(users);
    }

}
