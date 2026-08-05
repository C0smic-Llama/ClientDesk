package com.project.ClientDesk.controller;


import com.project.ClientDesk.dto.ProjectServiceRequestDTO;
import com.project.ClientDesk.dto.ProjectServiceResponseDTO;
import com.project.ClientDesk.service.ProjectServiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project-services")
@RequiredArgsConstructor
@Tag(name = "Project Service Management", description = "APIs to manage the services for a project")
public class ProjectServiceController {

    public final ProjectServiceService projectServiceService;

    @PostMapping
    public ResponseEntity<ProjectServiceResponseDTO> assignServiceToProject(
            @Valid
            @RequestBody
            ProjectServiceRequestDTO requestDTO){
        ProjectServiceResponseDTO responseDTO = projectServiceService.assignServiceToProject(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProjectServiceResponseDTO> updateProjectService(
            @PathVariable
            Long id,
            @Valid
            @RequestBody
            ProjectServiceRequestDTO requestDTO){
        return ResponseEntity.ok(projectServiceService.updateProjectService(id, requestDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProjectService(
            @PathVariable
            Long id){
        projectServiceService.removeServiceFromProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{projectServiceId}")
    public ResponseEntity<ProjectServiceResponseDTO> getProjectServiceById(
            @PathVariable
            Long id){
        return ResponseEntity.ok(projectServiceService.getProjectServiceById(id));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Page<ProjectServiceResponseDTO>> getServicesByProject(
            @PathVariable
            Long projectId,
            Pageable pageable){
        return ResponseEntity.ok(projectServiceService.getAllServicesByProject(projectId, pageable));
    }

    @GetMapping("/{serviceCatalogueID}")
    public ResponseEntity<Page<ProjectServiceResponseDTO>> getProjectsByService(
            @PathVariable
            Long serviceCatalogueId,
            Pageable pageable){
        return ResponseEntity.ok(projectServiceService.getProjectByService(serviceCatalogueId,pageable));
    }

}
