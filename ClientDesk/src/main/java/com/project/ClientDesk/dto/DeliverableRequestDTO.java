package com.project.ClientDesk.dto;

import com.project.ClientDesk.entity.Deliverable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.engine.internal.ImmutableEntityEntry;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliverableRequestDTO {

    @NotNull(message = "Project Service ID is required")
    private Long projectServiceId;

    @NotBlank(message = "Deliverable name is required")
    private String deliverableName;
    private String description;

    @NotNull(message = "Status is required")
    private Deliverable.DeliverableStatus status;

    @FutureOrPresent(message = "Due date must be in the future")
    private LocalDate dueDate;

    //@FutureOrPresent(message = "Completed date must be in the future")
    //private LocalDate completedDate;
    private String remarks;

}
