package com.project.ClientDesk.controller;


import com.project.ClientDesk.dto.ProjectRequestDTO;
import com.project.ClientDesk.dto.ProjectResponseDTO;
import com.project.ClientDesk.entity.Project;
import com.project.ClientDesk.service.ProjectService;
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
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Project Management", description = "APIs for managing the projects")
public class ProjectController {

    private final ProjectService projectService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(
            @Valid
            @RequestBody
            ProjectRequestDTO requestDTO) {
        return new ResponseEntity<>(projectService.createProject(requestDTO), HttpStatus.CREATED);
    }


    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable
            Long id,
            @Valid
            @RequestBody
            ProjectRequestDTO requestDTO) {
        return ResponseEntity.ok(projectService.updateProject(id, requestDTO));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable
            Long id) {

        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @GetMapping
    public ResponseEntity<Page<ProjectResponseDTO>> getAllProjects(Pageable pageable) {
        return ResponseEntity.ok(projectService.getAllProjects(pageable));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(
            @PathVariable
            Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @GetMapping("/search")
    public ResponseEntity<Page<ProjectResponseDTO>> searchProjects(
            @RequestParam
            String keyword,
            Pageable pageable) {
        return ResponseEntity.ok(projectService.searchProjects(keyword, pageable));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @GetMapping("/status")
    public ResponseEntity<Page<ProjectResponseDTO>> searchByStatus(
            @RequestParam
            Project.ProjectStatus status,
            Pageable pageable) {
        return ResponseEntity.ok(projectService.getProjectsByStatus(status, pageable));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<Page<ProjectResponseDTO>> getProjectByClient(
            @PathVariable
            Long clientId,
            Pageable pageable) {
        return ResponseEntity.ok(projectService.getProjectsByClient(clientId, pageable));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ProjectResponseDTO>> getProjectByAssignedUser(
            @PathVariable
            Long userId,
            Pageable pageable) {
        return ResponseEntity.ok(projectService.getProjectByAssignedUser(userId, pageable));
    }


}