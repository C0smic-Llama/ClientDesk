package com.project.ClientDesk.service.impl;

import com.project.ClientDesk.dto.ProjectRequestDTO;
import com.project.ClientDesk.dto.ProjectResponseDTO;
import com.project.ClientDesk.entity.Client;
import com.project.ClientDesk.entity.Project;
import com.project.ClientDesk.entity.User;
import com.project.ClientDesk.exception.DuplicateResourceException;
import com.project.ClientDesk.exception.ResourceNotFoundException;
import com.project.ClientDesk.mapper.ProjectMapper;
import com.project.ClientDesk.repository.ClientRepository;
import com.project.ClientDesk.repository.ProjectRepository;
import com.project.ClientDesk.repository.ProjectServiceRepository;
import com.project.ClientDesk.repository.UserRepository;
import com.project.ClientDesk.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ProjectServiceRepository projectServiceRepository;
    private final ProjectMapper projectMapper;




    @Override
    public ProjectResponseDTO createProject(ProjectRequestDTO requestDTO) {
        if(projectRepository.existsByProjectName(requestDTO.getProjectName())){
            throw new DuplicateResourceException("Project name already exists with name : "+requestDTO.getProjectName());
        }

        Client client  = clientRepository.findById(requestDTO.getClientId()).orElseThrow(()->
                new ResourceNotFoundException("Client not found with ID : "+requestDTO.getClientId()));

        Set<User> assignedUsers = new HashSet<>(userRepository.findAllById(requestDTO.getAssignedUserIds()));

        if(assignedUsers.size() != requestDTO.getAssignedUserIds().size()){
            throw new ResourceNotFoundException("One or more users were not found ");
        }
        Project project = projectMapper.toEntity(requestDTO);
        project.setClient(client);
        project.setAssignedUsers(assignedUsers);

        Project savedProject = projectRepository.save(project);

        return projectMapper.toResponseDTO(savedProject);
    }

    @Override
    public ProjectResponseDTO updateProject(Long projectId, ProjectRequestDTO requestDTO) {
        Project existingProject  = projectRepository.findById(projectId).orElseThrow(()->
                new ResourceNotFoundException("Project not found with ID : "+projectId));

        if(!existingProject.getProjectName().equalsIgnoreCase(requestDTO.getProjectName())
        && projectRepository.existsByProjectName(requestDTO.getProjectName())){
            throw new DuplicateResourceException("Project already exists with the name : "+requestDTO.getProjectName());
        }

        Client client = clientRepository.findById(requestDTO.getClientId()).orElseThrow(()->
                new ResourceNotFoundException("Client not found with ID : "+requestDTO.getClientId()));

        Set<User> assignedUsers = new HashSet<>(userRepository.findAllById(requestDTO.getAssignedUserIds()));

        if(assignedUsers.size() != requestDTO.getAssignedUserIds().size()){
            throw new ResourceNotFoundException("One or more users not found");
        }

        projectMapper.updateEntityFromDTO(requestDTO,existingProject);

        existingProject.setClient(client);
        existingProject.setAssignedUsers(assignedUsers);

        Project updatedProject = projectRepository.save(existingProject);

        return projectMapper.toResponseDTO(updatedProject);

    }

    @Override
    public void deleteProject(Long projectId) {

        Project project = projectRepository.findById(projectId).orElseThrow(()->
                new ResourceNotFoundException("Project not found with ID : "+projectId));

        projectRepository.delete(project);

    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponseDTO getProjectById(Long projectId) {

        Project project = projectRepository.findById(projectId).orElseThrow(()->
                new ResourceNotFoundException("Project not found with ID : "+projectId));



        return mapToResponse(project);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectResponseDTO> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable).map(projectMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectResponseDTO> searchProjects(String keyword, Pageable pageable) {
        return projectRepository.findByProjectNameContainingIgnoreCase(keyword, pageable)
                .map(projectMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectResponseDTO> getProjectsByStatus(Project.ProjectStatus status, Pageable pageable) {
        return projectRepository.findByStatus(status, pageable)
                .map(projectMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectResponseDTO> getProjectsByClient(Long clientId, Pageable pageable) {

        Client client = clientRepository.findById(clientId).orElseThrow(()->
                new ResourceNotFoundException("Client not found with Id : "+clientId));
        return projectRepository.findByClient(client, pageable).map(projectMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectResponseDTO> getProjectByAssignedUser(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("user not found with ID : "+ userId));

        return projectRepository.findByAssignedUsersContaining(user, pageable)
                .map(projectMapper::toResponseDTO);
    }

    private BigDecimal calculateProjectTotal(Project project){
        return projectServiceRepository.findByProject(project)
                .stream()
                .map(com.project.ClientDesk.entity.ProjectService::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private ProjectResponseDTO mapToResponse(Project project){
        ProjectResponseDTO dto = projectMapper.toResponseDTO(project);
        dto.setTotalCost(calculateProjectTotal(project));
        return dto;
    }
}
