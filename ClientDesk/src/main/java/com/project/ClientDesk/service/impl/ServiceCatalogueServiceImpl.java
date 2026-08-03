package com.project.ClientDesk.service.impl;

import com.project.ClientDesk.dto.ServiceCatalogueRequestDTO;
import com.project.ClientDesk.dto.ServiceCatalogueResponseDTO;
import com.project.ClientDesk.entity.ServiceCatalogue;
import com.project.ClientDesk.exception.DuplicateResourceException;
import com.project.ClientDesk.exception.ResourceNotFoundException;
import com.project.ClientDesk.mapper.ServiceCatalogueMapper;
import com.project.ClientDesk.repository.ServiceCatalogueRepository;
import com.project.ClientDesk.service.ServiceCatalogueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ServiceCatalogueServiceImpl implements ServiceCatalogueService {

    private final ServiceCatalogueRepository serviceCatalogueRepository;
    private final ServiceCatalogueMapper serviceCatalogueMapper;

    @Override
    public ServiceCatalogueResponseDTO createService(ServiceCatalogueRequestDTO requestDTO) {
        if(serviceCatalogueRepository.existsByServiceName(requestDTO.getServiceName())){
            throw new DuplicateResourceException("Service already exists with name "+ requestDTO.getServiceName());


        }
        ServiceCatalogue service = serviceCatalogueMapper.toEntity(requestDTO);
        service.setActive(true);
        ServiceCatalogue savedService = serviceCatalogueRepository.save(service);
        return serviceCatalogueMapper.toResponseDTO(savedService);
    }

    @Override
    public ServiceCatalogueResponseDTO updateService(Long serviceId, ServiceCatalogueRequestDTO requestDTO) {
        ServiceCatalogue existingService = serviceCatalogueRepository.findById(serviceId).orElseThrow(()->
                new ResourceNotFoundException("Service not found with ID : "+serviceId));

        if(!existingService.getServiceName().equalsIgnoreCase(requestDTO.getServiceName())
        && serviceCatalogueRepository.existsByServiceName(requestDTO.getServiceName())){
            throw new DuplicateResourceException("Service already exists with name : "+requestDTO.getServiceName());
        }

        serviceCatalogueMapper.updateEntityFromDTO(requestDTO, existingService);
        ServiceCatalogue updatedService = serviceCatalogueRepository.save(existingService);
        return serviceCatalogueMapper.toResponseDTO(updatedService);
    }

    @Override
    public void deleteService(Long serviceId) {

        ServiceCatalogue service = serviceCatalogueRepository.findById(serviceId).orElseThrow(()->
                new DuplicateResourceException("Service not found with ID : "+serviceId));
        service.setActive(false);
        serviceCatalogueRepository.delete(service);

    }

    @Override
    @Transactional(readOnly = true)
    public ServiceCatalogueResponseDTO getServiceById(Long serviceId) {
        ServiceCatalogue service = serviceCatalogueRepository.findById(serviceId).orElseThrow(()->
                new ResourceNotFoundException("Service not found with ID : "+serviceId));
    return serviceCatalogueMapper.toResponseDTO(service);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceCatalogueResponseDTO> getAllServices(Pageable pageable) {
        return serviceCatalogueRepository.findAll(pageable)
                .map(serviceCatalogueMapper::toResponseDTO);
    }

    @Override
    public Page<ServiceCatalogueResponseDTO> getActiveServices(Pageable pageable) {
        return serviceCatalogueRepository.findByActiveTrue(pageable)
                .map(serviceCatalogueMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceCatalogueResponseDTO> searchServices(String keyword, Pageable pageable) {
        return serviceCatalogueRepository.findByServiceNameContainingIgnoreCase(keyword, pageable)
                .map(serviceCatalogueMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceCatalogueResponseDTO> getServicesByCategory(ServiceCatalogue.ServiceCategory category, Pageable pageable) {
        return serviceCatalogueRepository.findByCategory(category, pageable)
                .map(serviceCatalogueMapper::toResponseDTO);
    }

    @Override
    public ServiceCatalogueResponseDTO activateService(Long serviceId) {
        ServiceCatalogue service = serviceCatalogueRepository.findById(serviceId).orElseThrow(()->
                new ResourceNotFoundException("Service not found with ID : "+serviceId));

        service.setActive(true);

        ServiceCatalogue updatedService = serviceCatalogueRepository.save(service);
        return serviceCatalogueMapper.toResponseDTO(updatedService);

    }

    @Override
    public ServiceCatalogueResponseDTO deactivateService(Long serviceId) {
        ServiceCatalogue service  = serviceCatalogueRepository.findById(serviceId).orElseThrow(()->
                new ResourceNotFoundException("Service not found with ID : "+serviceId));

        service.setActive(false);
        ServiceCatalogue updatedService = serviceCatalogueRepository.save(service);
        return serviceCatalogueMapper.toResponseDTO(updatedService);
    }
}
