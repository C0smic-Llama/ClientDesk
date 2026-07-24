package com.project.ClientDesk.service;

import com.project.ClientDesk.dto.ClientRequestDTO;
import com.project.ClientDesk.dto.ClientResponseDTO;
import com.project.ClientDesk.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {


    ClientResponseDTO createClient(ClientRequestDTO requestDTO);

    ClientResponseDTO updateClient(Long clientId, ClientRequestDTO requestDTO);

    void deleteClient(Long clientId);

    ClientResponseDTO getClientById(Long clientId);

    Page<ClientResponseDTO> getAllClients(Pageable pageable);

    Page<ClientResponseDTO> getClientsByStatus(Client.ClientStatus status, Pageable pageable);

    Page<ClientResponseDTO> searchClients(String keyword, Pageable pageable);
}
