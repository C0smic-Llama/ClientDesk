package com.project.ClientDesk.service.impl;


import com.project.ClientDesk.dto.DeliverableRequestDTO;
import com.project.ClientDesk.dto.DeliverableResponseDTO;
import com.project.ClientDesk.entity.Deliverable;
import com.project.ClientDesk.entity.ProjectService;
import com.project.ClientDesk.exception.DuplicateResourceException;
import com.project.ClientDesk.exception.ResourceNotFoundException;
import com.project.ClientDesk.mapper.DeliverableMapper;
import com.project.ClientDesk.repository.DeliverableRepository;
import com.project.ClientDesk.repository.ProjectServiceRepository;
import com.project.ClientDesk.service.DeliverableService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliverableServiceImpl implements DeliverableService {

    private final DeliverableRepository deliverableRepository;
    private final ProjectServiceRepository projectServiceRepository;
    private final DeliverableMapper deliverableMapper;

    private void updateCompletedDate(Deliverable deliverable) {
        if (deliverable.getStatus() == Deliverable.DeliverableStatus.COMPLETED
                && deliverable.getCompletedDate() == null)
            deliverable.setCompletedDate(LocalDate.now());
    }

    private ProjectService getProjectService(Long projectServiceId) {
        return projectServiceRepository.findById(projectServiceId).orElseThrow(() ->
                new ResourceNotFoundException("Project service not found with ID : " + projectServiceId));
    }

    @Override
    public DeliverableResponseDTO createDeliverable(DeliverableRequestDTO requestDTO) {
        ProjectService projectService = getProjectService(requestDTO.getProjectServiceId());

        if (deliverableRepository.existsByProjectServiceAndDeliverableName(projectService, requestDTO.getDeliverableName())) {
            throw new DuplicateResourceException("Deliverable already exists for this project service");
        }

        Deliverable deliverable = deliverableMapper.toEntity(requestDTO);
        deliverable.setProjectService(projectService);
        updateCompletedDate(deliverable);
        Deliverable savedDeliverable = deliverableRepository.save(deliverable);
        return deliverableMapper.toResponseDTO(savedDeliverable);
    }

    @Override
    public DeliverableResponseDTO updateDeliverable(Long id, DeliverableRequestDTO requestDTO) {
        Deliverable existingDeliverable = deliverableRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Deliverable not found with ID : " + id));
        ProjectService projectService = getProjectService(requestDTO.getProjectServiceId());

        if (!existingDeliverable.getProjectService().getId().equals(projectService.getId())
                || !existingDeliverable.getDeliverableName().equalsIgnoreCase(requestDTO.getDeliverableName())) {
            if (deliverableRepository.existsByProjectServiceAndDeliverableName(projectService, requestDTO.getDeliverableName())) {
                throw new DuplicateResourceException("Deliverable already exists for this project service");
            }
        }
        deliverableMapper.updateEntityFromDTO(requestDTO, existingDeliverable);
        existingDeliverable.setProjectService(projectService);
        updateCompletedDate(existingDeliverable);
        Deliverable updatedDeliverable = deliverableRepository.save(existingDeliverable);
        return deliverableMapper.toResponseDTO(updatedDeliverable);
    }

    @Override
    public void deleteDeliverable(Long id) {
        Deliverable deliverable  = deliverableRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Deliverable not found with id : "+id));
        deliverableRepository.delete(deliverable);

    }

    @Override
    @Transactional(readOnly = true)
    public DeliverableResponseDTO getDeliverableById(Long id) {
        Deliverable deliverable  = deliverableRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Deliverable not found with id : "+id));
        return deliverableMapper.toResponseDTO(deliverable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeliverableResponseDTO> getDeliverablesByProjectService(Long projectServiceId, Pageable pageable) {
        ProjectService projectService = getProjectService(projectServiceId);
        return deliverableRepository.findByProjectService(projectService, pageable)
                .map(deliverableMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeliverableResponseDTO> getDeliverablesByProject(Long projectId, Pageable pageable) {
        return deliverableRepository.findByProjectId(projectId, pageable)
                .map(deliverableMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeliverableResponseDTO> getDeliverablesByStatus(Deliverable.DeliverableStatus status, Pageable pageable) {
        return deliverableRepository.findByStatus(status, pageable)
                .map(deliverableMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeliverableResponseDTO> getDeliverablesByProjectAndStatus(Long projectId, Deliverable.DeliverableStatus status, Pageable pageable) {
        return deliverableRepository.findByProjectIdAndStatus(projectId,status,pageable)
                .map(deliverableMapper::toResponseDTO);
    }
}
