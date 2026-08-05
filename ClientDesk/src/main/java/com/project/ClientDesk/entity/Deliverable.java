package com.project.ClientDesk.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "deliverables")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Deliverable extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliverable_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_service_id", nullable = false)
    private ProjectService projectService;

    @Column(nullable = false,length = 150)
    private String deliverableName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliverableStatus status;

    private LocalDate dueDate;
    private LocalDate completedDate;

    @Column(length = 500)
    private String remarks;


    public enum DeliverableStatus{
        PENDING,
        IN_PROGRESS,
        CLIENT_REVIEW,
        APPROVED,
        COMPLETED
    }


}
