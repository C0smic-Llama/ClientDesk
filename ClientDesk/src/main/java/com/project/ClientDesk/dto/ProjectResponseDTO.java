package com.project.ClientDesk.dto;

import com.project.ClientDesk.entity.Project;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDTO {

    private Long id;
    private String projectName;
    private String description;
    private Project.ProjectStatus status;
    private LocalDate startDate;
    private LocalDate deadLine;
    private BigDecimal quota;
    private String notes;
    private Long clientId;
    private String clientName;
    private Set<UserEssentialDTO> assignedUsers;
    private BigDecimal totalCost;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
