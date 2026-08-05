package com.project.ClientDesk.service;

import com.project.ClientDesk.dto.DeliverableRequestDTO;
import com.project.ClientDesk.dto.DeliverableResponseDTO;
import com.project.ClientDesk.entity.Deliverable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliverableService {

    DeliverableResponseDTO createDeliverable(DeliverableRequestDTO requestDTO);

    DeliverableResponseDTO updateDeliverable(Long id, DeliverableRequestDTO requestDTO);

    void deleteDeliverable(Long id);

    DeliverableResponseDTO getDeliverableById(Long id);

    Page<DeliverableResponseDTO> getDeliverablesByProjectService(Long projectServiceId, Pageable pageable);

    Page<DeliverableResponseDTO> getDeliverablesByProject(Long projectId, Pageable pageable);

    Page<DeliverableResponseDTO> getDeliverablesByStatus(Deliverable.DeliverableStatus status, Pageable pageable);

    Page<DeliverableResponseDTO> getDeliverablesByProjectAndStatus(Long projectId, Deliverable.DeliverableStatus status, Pageable pageable);
}
