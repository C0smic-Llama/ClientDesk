package com.project.ClientDesk.service.impl;

import com.project.ClientDesk.dto.ProjectServiceRequestDTO;
import com.project.ClientDesk.dto.ProjectServiceResponseDTO;
import com.project.ClientDesk.entity.Project;
import com.project.ClientDesk.entity.ProjectService;
import com.project.ClientDesk.entity.ServiceCatalogue;
import com.project.ClientDesk.exception.DuplicateResourceException;
import com.project.ClientDesk.exception.ResourceNotFoundException;
import com.project.ClientDesk.mapper.ProjectServiceMapper;
import com.project.ClientDesk.repository.ProjectRepository;
import com.project.ClientDesk.repository.ProjectServiceRepository;
import com.project.ClientDesk.repository.ServiceCatalogueRepository;
import com.project.ClientDesk.service.ProjectServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceServiceImpl implements ProjectServiceService {

    private final ProjectServiceRepository projectServiceRepository;
    private final ProjectRepository projectRepository;
    private final ServiceCatalogueRepository serviceCatalogueRepository;
    private final ProjectServiceMapper projectServiceMapper;

    @Override
    public ProjectServiceResponseDTO assignServiceToProject(ProjectServiceRequestDTO requestDTO) {
        Project project = projectRepository.findById(requestDTO.getProjectId()).orElseThrow(()->
                new ResourceNotFoundException("Project not found with ID : "+ requestDTO.getProjectId()));

        ServiceCatalogue service = serviceCatalogueRepository.findById(requestDTO.getServiceCatalogueId()).orElseThrow(()->
                new ResourceNotFoundException("Service not found with ID : "+requestDTO.getServiceCatalogueId()));

        if(projectServiceRepository.existsByProjectAndServiceCatalogue(project, service)) {
            throw new DuplicateResourceException("Service is already assigned to the project");
        }

        ProjectService projectService = projectServiceMapper.toEntity(requestDTO);
        if (projectService.getDiscount() == null) {
            projectService.setDiscount(BigDecimal.ZERO);
        }
        projectService.setProject(project);
        projectService.setServiceCatalogue(service);

        ProjectService savedProjectService = projectServiceRepository.save(projectService);

        return  projectServiceMapper.toResponseDTO(savedProjectService);
        }


    @Override
    public ProjectServiceResponseDTO updateProjectService(Long projectServiceId, ProjectServiceRequestDTO requestDTO) {
        ProjectService projectService = projectServiceRepository.findById(projectServiceId).orElseThrow(()->
                new ResourceNotFoundException("Project Service not found with ID : "+projectServiceId));

        projectServiceMapper.updateEntityFromDTO(requestDTO, projectService);

        ProjectService updatedProjectService = projectServiceRepository.save(projectService);

        return projectServiceMapper.toResponseDTO(updatedProjectService);
    }

    @Override
    public void removeServiceFromProject(Long projectServiceId) {
        ProjectService projectService = projectServiceRepository.findById(projectServiceId).orElseThrow(()->
                new ResourceNotFoundException("Project Service not found with ID : "+projectServiceId));
        projectServiceRepository.delete(projectService);

    }

    @Override
    @Transactional(readOnly = true)
    public ProjectServiceResponseDTO getProjectServiceById(Long projectServiceId) {
        ProjectService projectService = projectServiceRepository.findById(projectServiceId).orElseThrow(()->
                new ResourceNotFoundException("Project Service not found with ID : "+projectServiceId));
        return projectServiceMapper.toResponseDTO(projectService);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectServiceResponseDTO> getAllServicesByProject(Long projectId, Pageable pageable) {
        Project project  = projectRepository.findById(projectId).orElseThrow(()->
                new ResourceNotFoundException("Project not found with ID : "+projectId));
        return projectServiceRepository.findByProject(project, pageable)
                .map(projectServiceMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectServiceResponseDTO> getProjectByService(Long serviceCatalogueId, Pageable pageable) {
        ServiceCatalogue service = serviceCatalogueRepository.findById(serviceCatalogueId).orElseThrow(()->
                new ResourceNotFoundException("Service not found with ID : "+serviceCatalogueId));
        return projectServiceRepository.findByServiceCatalogue(service,pageable)
                .map(projectServiceMapper::toResponseDTO);
    }
}
