package com.project.ClientDesk.repository;

import com.project.ClientDesk.entity.Client;
import com.project.ClientDesk.entity.Project;
import com.project.ClientDesk.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    Optional<Project> findByProjectName(String projectName);

    boolean existsByProjectName(String projectName);

    long countByStatus(Project.ProjectStatus status);

    Page<Project> findByStatus(Project.ProjectStatus status, Pageable pageable);

    Page<Project> findByClient(Client client, Pageable pageable);

    Page<Project> findByProjectNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Project> findByStartDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Project> findByDeadlineBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Project> findByAssignedUsersContaining(User user, Pageable pageable);



}
