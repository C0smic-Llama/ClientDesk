package com.project.ClientDesk.service.impl;

import com.project.ClientDesk.dto.UserRequestDTO;
import com.project.ClientDesk.dto.UserResponseDTO;
import com.project.ClientDesk.dto.UserUpdateRequestDTO;
import com.project.ClientDesk.entity.User;
import com.project.ClientDesk.exception.DuplicateResourceException;
import com.project.ClientDesk.exception.ResourceNotFoundException;
import com.project.ClientDesk.mapper.UserMapper;
import com.project.ClientDesk.repository.UserRepository;
import com.project.ClientDesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
       if(userRepository.existsByEmail(userRequestDTO.getEmail())){
           throw new DuplicateResourceException("Email already exists");
       }
       User user = userMapper.toEntity(userRequestDTO);
       user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
       user.setActive(true);
       User savedUser = userRepository.save(user);
       return userMapper.toResponseDTO(savedUser);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserUpdateRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("User not found with Id : "+id));

        if(!existingUser.getEmail().equals(userRequestDTO.getEmail())
        && userRepository.existsByEmail(userRequestDTO.getEmail())){
            throw new DuplicateResourceException("Email already exists");
        }
        userMapper.updateEntityFromDTO(userRequestDTO, existingUser);

        if(userRequestDTO.getPassword()!=null && !userRequestDTO.getPassword().isBlank()){
            existingUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toResponseDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user  = userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("user not found with Id : "+id));
        userRepository.delete(user);

    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user  = userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("User not found with ID : "+id));
        return userMapper.toResponseDTO(user);
    }

    @Override
    public Page<UserResponseDTO> searchUsers(String keyword, Pageable pageable) {
       return userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(keyword,keyword, pageable).
               map(userMapper::toResponseDTO);
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toResponseDTO);
    }

    @Override
    public Page<UserResponseDTO> getUsersByRole(User.Role role, Pageable pageable) {
        return userRepository.findByRole(role, pageable).map(
                userMapper::toResponseDTO);
    }

    @Override
    public Page<UserResponseDTO> getUsersByStatus(boolean active, Pageable pageable) {
        return userRepository.findByActive(active, pageable).map(
                userMapper::toResponseDTO);
    }
}
