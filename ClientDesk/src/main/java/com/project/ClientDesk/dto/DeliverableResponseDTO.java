package com.project.ClientDesk.dto;

import com.project.ClientDesk.entity.Deliverable;
import com.project.ClientDesk.entity.ProjectService;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliverableResponseDTO {

    private Long id;
    private String deliverableName;

    private Long projectId;
    private String projectName;

    private Long projectServiceId;
    private Long serviceCatalogueId;
    private String serviceName;
    private Deliverable.DeliverableStatus status;

    private LocalDate dueDate;
    private LocalDate completedDate;

    private String remarks;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
