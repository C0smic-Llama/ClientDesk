package com.project.ClientDesk.service;

import com.project.ClientDesk.dto.ProjectServiceRequestDTO;
import com.project.ClientDesk.dto.ProjectServiceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectServiceService {

    ProjectServiceResponseDTO assignServiceToProject(ProjectServiceRequestDTO requestDTO);

    ProjectServiceResponseDTO updateProjectService(Long projectServiceId, ProjectServiceRequestDTO requestDTO);

    void removeServiceFromProject(Long projectServiceId);

    ProjectServiceResponseDTO getProjectServiceById(Long projectServiceId);

    Page<ProjectServiceResponseDTO> getAllServicesByProject(Long projectId, Pageable pageable);

    Page<ProjectServiceResponseDTO> getProjectByService(Long serviceCatalogueId, Pageable pageable);
}
