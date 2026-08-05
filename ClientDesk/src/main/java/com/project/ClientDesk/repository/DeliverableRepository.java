package com.project.ClientDesk.repository;

import com.project.ClientDesk.entity.Deliverable;
import com.project.ClientDesk.entity.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliverableRepository extends JpaRepository<Deliverable,Long> {


    List<Deliverable> findByProjectService(ProjectService projectService);

    Page<Deliverable> findByProjectService(ProjectService projectService, Pageable pageable);

    Page<Deliverable> findByStatus(Deliverable.DeliverableStatus status, Pageable pageable);

    boolean existsByProjectServiceAndDeliverableName(ProjectService projectService, String deliverableName);

    @Query("SELECT d FROM Deliverable d WHERE d.projectService.project.id = :projectId")
    Page<Deliverable> findByProjectId(
            @Param("projectId")
            Long projectId,
            Pageable pageable);

    @Query("SELECT d FROM Deliverable d WHERE d.projectService.project.id = :projectId")
    List<Deliverable> findByProjectId(
            @Param("projectId")
            Long projectId);

    @Query("SELECT COUNT(d) FROM Deliverable d WHERE d.projectService.project.id = :projectId AND d.status = 'COMPLETED'")
    long countCompletedDeliverables(Long projectId);


    @Query("SELECT d FROM Deliverable d WHERE d.projectService.project.id = :projectId AND d.status = :status")
    Page<Deliverable> findByProjectIdAndStatus(
            @Param("projectId")
            Long projectId,
            @Param("status")
            Deliverable.DeliverableStatus status,
            Pageable pageable);

}
