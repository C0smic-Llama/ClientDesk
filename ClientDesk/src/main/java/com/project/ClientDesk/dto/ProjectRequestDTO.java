package com.project.ClientDesk.dto;


import com.project.ClientDesk.entity.Project;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequestDTO {

    @NotBlank(message = "Project name is required")
    @Size(max = 70, message = "Project name cannot exceed 150 characters")
    private String projectName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Project status is required")
    private Project.ProjectStatus status;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "Deadline is required")
    private LocalDate deadline;

    @NotNull(message = "Quota is requierd")
    @DecimalMin(value ="0.0",inclusive = true,message = "Quota cannot be negative")
    private BigDecimal quota;

    @Size(max= 1000,message = "Notes cannot exceed 1000 characters")
    private String notes;

    @NotNull(message = "Client is required")
    private Long clientId;

    @NotEmpty(message = "Atleast one staff must be assigned")
    private Set<Long> assignedUserIds;
}
