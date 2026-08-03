package com.project.ClientDesk.service;

import com.project.ClientDesk.dto.ProjectRequestDTO;
import com.project.ClientDesk.dto.ProjectResponseDTO;
import com.project.ClientDesk.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {



    ProjectResponseDTO createProject(ProjectRequestDTO requestDTO);

    ProjectResponseDTO updateProject(Long projectId, ProjectRequestDTO requestDTO);

    void deleteProject(Long projectId);

    ProjectResponseDTO getProjectById(Long projectId);

    Page<ProjectResponseDTO> getAllProjects(Pageable pageable);

    Page<ProjectResponseDTO> searchProjects(String keyword, Pageable pageable);

    Page<ProjectResponseDTO> getProjectsByStatus(Project.ProjectStatus status, Pageable pageable);

    Page<ProjectResponseDTO> getProjectsByClient(Long clientId, Pageable pageable);

    Page<ProjectResponseDTO> getProjectByAssignedUser(Long userId, Pageable pageable);
}
