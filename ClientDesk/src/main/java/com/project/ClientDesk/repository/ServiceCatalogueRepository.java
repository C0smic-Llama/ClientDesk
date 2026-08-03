package com.project.ClientDesk.repository;

import com.project.ClientDesk.entity.ServiceCatalogue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceCatalogueRepository extends JpaRepository<ServiceCatalogue,Long> {


    Optional<ServiceCatalogue> findByServiceName(String serviceName);

    boolean existsByServiceName(String serviceName);

    Page<ServiceCatalogue> findByCategory(ServiceCatalogue.ServiceCategory category, Pageable pageable);

    Page<ServiceCatalogue> findByServiceNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<ServiceCatalogue> findByActiveTrue(Pageable pageable);

}
