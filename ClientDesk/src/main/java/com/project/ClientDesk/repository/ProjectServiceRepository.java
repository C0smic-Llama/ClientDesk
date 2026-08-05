package com.project.ClientDesk.repository;

import com.project.ClientDesk.entity.Project;
import com.project.ClientDesk.entity.ProjectService;
import com.project.ClientDesk.entity.ServiceCatalogue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectServiceRepository extends JpaRepository<ProjectService,Long> {

    //to check whether a service has already been assigned to a project
    boolean existsByProjectAndServiceCatalogue(Project project, ServiceCatalogue serviceCatalogue);

    List<ProjectService> findByProject(Project project);

    Page<ProjectService> findByProject(Project project, Pageable pageable);

    Page<ProjectService> findByServiceCatalogue(ServiceCatalogue serviceCatalogue, Pageable pageable);

    void deleteByProjectAndServiceCatalogue(Project project,ServiceCatalogue serviceCatalogue);

}
