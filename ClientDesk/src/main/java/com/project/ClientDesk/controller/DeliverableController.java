package com.project.ClientDesk.controller;


import com.project.ClientDesk.dto.DeliverableRequestDTO;
import com.project.ClientDesk.dto.DeliverableResponseDTO;
import com.project.ClientDesk.entity.Deliverable;
import com.project.ClientDesk.service.DeliverableService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deliverables")
@RequiredArgsConstructor
@Tag(name = "Deliverables Management", description = "APIs to manage the deliverables")
public class DeliverableController {

    private final DeliverableService deliverableService;

    @PostMapping
    public ResponseEntity<DeliverableResponseDTO> createDeliverable(
            @Valid
            @RequestBody
            DeliverableRequestDTO requestDTO){
        DeliverableResponseDTO responseDTO = deliverableService.createDeliverable(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<DeliverableResponseDTO> updateDeliverable(
            @PathVariable
            Long id,
            @Valid
            @RequestBody
            DeliverableRequestDTO requestDTO){
        return ResponseEntity.ok(deliverableService.updateDeliverable(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliverable(
            @PathVariable
            Long id){
        deliverableService.deleteDeliverable(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public  ResponseEntity<DeliverableResponseDTO> getDeliverableById(
            @PathVariable
            Long id){
        return ResponseEntity.ok(deliverableService.getDeliverableById(id));
    }

    @GetMapping("/project-service/{projectServiceId}")
    public  ResponseEntity<Page<DeliverableResponseDTO>> getDeliverableByProjectService(
            @PathVariable
            Long projectServiceId,
            Pageable pageable){
        return ResponseEntity.ok(deliverableService.getDeliverablesByProjectService(projectServiceId, pageable));
    }

    @GetMapping("/project/{projectId}")
    public  ResponseEntity<Page<DeliverableResponseDTO>> getDeliverablesByProject(
            @PathVariable
            Long projectId,
            Pageable pageable){
        return ResponseEntity.ok(deliverableService.getDeliverablesByProject(projectId, pageable));
    }

    @GetMapping("/status/{status}")
    public  ResponseEntity<Page<DeliverableResponseDTO>> getDeliverablesByStatus(
            @PathVariable
            Deliverable.DeliverableStatus status,
            Pageable pageable){
        return ResponseEntity.ok(deliverableService.getDeliverablesByStatus(status, pageable));
    }

    @GetMapping("/project/{projectId}/status/{status}")
    public  ResponseEntity<Page<DeliverableResponseDTO>> getDeliverablesByProjectAndStatus(
            @PathVariable
            Long projectId,
            @PathVariable
            Deliverable.DeliverableStatus status,
            Pageable pageable){
        return ResponseEntity.ok(deliverableService.getDeliverablesByProjectAndStatus(projectId, status, pageable));
    }

}
