package com.project.ClientDesk.repository;

import com.project.ClientDesk.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository <Client,Long>{
    Optional<Client> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Client> findByStatus(Client.ClientStatus status);
    List<Client> findByCompanyNameContainingIgnoreCase(String companyName);
    List<Client> findByContactPersonContainingIgnoreCase(String contactPerson);
    List<Client> findByCompanyNameContainingIgnoreCaseOrContactPersonContainingIgnoreCase(String companyName, String contactPerson);

    Page<Client> findAll(Pageable pageable);
    Page<Client> findByStatus(Client.ClientStatus status, Pageable pageable);
    Page<Client> findByCompanyNameContainingIgnoreCaseOrContactPersonContainingIgnoreCase(String companyName, String contactPerson, Pageable pageable);

}
