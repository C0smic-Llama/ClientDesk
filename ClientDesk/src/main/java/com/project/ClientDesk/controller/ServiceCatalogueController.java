package com.project.ClientDesk.controller;


import com.project.ClientDesk.dto.ServiceCatalogueRequestDTO;
import com.project.ClientDesk.dto.ServiceCatalogueResponseDTO;
import com.project.ClientDesk.entity.ServiceCatalogue;
import com.project.ClientDesk.service.ServiceCatalogueService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@Tag(name = "Services Management", description = "APIs for managing the services provided by the agency")
public class ServiceCatalogueController {

    private final ServiceCatalogueService serviceCatalogueService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ServiceCatalogueResponseDTO> createService(
            @Valid
            @RequestBody
            ServiceCatalogueRequestDTO requestDTO) {
        return new ResponseEntity<>(serviceCatalogueService.createService(requestDTO), HttpStatus.CREATED);

    }


    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @PutMapping("/{id}")
    public ResponseEntity<ServiceCatalogueResponseDTO> updateService(
            @PathVariable
            Long id,
            @Valid
            @RequestBody
            ServiceCatalogueRequestDTO requestDTO) {
        return ResponseEntity.ok(serviceCatalogueService.updateService(id, requestDTO));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(
            @PathVariable
            Long id) {
        serviceCatalogueService.deleteService(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @GetMapping
    public ResponseEntity<Page<ServiceCatalogueResponseDTO>> getAllServices(Pageable pageable) {
        return ResponseEntity.ok(serviceCatalogueService.getAllServices(pageable));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @GetMapping("/active")
    public ResponseEntity<Page<ServiceCatalogueResponseDTO>> getActiveServices(Pageable pageable) {
        return ResponseEntity.ok(serviceCatalogueService.getActiveServices(pageable));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @GetMapping("/{id}")
    public ResponseEntity<ServiceCatalogueResponseDTO> getServiceById(
            @PathVariable
            Long id) {
        return ResponseEntity.ok(serviceCatalogueService.getServiceById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @GetMapping("/search")
    public ResponseEntity<Page<ServiceCatalogueResponseDTO>> searchServices(
            @RequestParam
            String keyword,
            Pageable pageable) {
        return ResponseEntity.ok(serviceCatalogueService.searchServices(keyword, pageable));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @GetMapping("category")
    public ResponseEntity<Page<ServiceCatalogueResponseDTO>> getServicesByCategory(
            @RequestParam
            ServiceCatalogue.ServiceCategory category,
            Pageable pageable) {
        return ResponseEntity.ok(serviceCatalogueService.getServicesByCategory(category, pageable));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ServiceCatalogueResponseDTO> activateService(
            @PathVariable
            Long id) {
        return ResponseEntity.ok(serviceCatalogueService.activateService(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @PatchMapping("{id}/deactivate")
    public ResponseEntity<ServiceCatalogueResponseDTO> deactivateService(
            @PathVariable
            Long id) {
        return ResponseEntity.ok(serviceCatalogueService.deactivateService(id));
    }

}
