package com.project.ClientDesk.service;

import com.project.ClientDesk.dto.ServiceCatalogueRequestDTO;
import com.project.ClientDesk.dto.ServiceCatalogueResponseDTO;
import com.project.ClientDesk.entity.ServiceCatalogue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceCatalogueService {

    ServiceCatalogueResponseDTO createService(ServiceCatalogueRequestDTO requestDTO);

    ServiceCatalogueResponseDTO updateService(Long serviceId, ServiceCatalogueRequestDTO requestDTO);

    void deleteService(Long serviceId);

    ServiceCatalogueResponseDTO getServiceById(Long serviceId);

    Page<ServiceCatalogueResponseDTO> getAllServices(Pageable pageable);

    Page<ServiceCatalogueResponseDTO> getActiveServices(Pageable pageable);

    Page<ServiceCatalogueResponseDTO> searchServices(String keyword, Pageable pageable);

    Page<ServiceCatalogueResponseDTO> getServicesByCategory(ServiceCatalogue.ServiceCategory category , Pageable pageable);

    ServiceCatalogueResponseDTO activateService(Long serviceId);

    ServiceCatalogueResponseDTO deactivateService(Long serviceId);


}
