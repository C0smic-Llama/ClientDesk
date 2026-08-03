package com.project.ClientDesk.service.impl;

import com.project.ClientDesk.dto.ClientRequestDTO;
import com.project.ClientDesk.dto.ClientResponseDTO;
import com.project.ClientDesk.entity.Client;
import com.project.ClientDesk.exception.DuplicateResourceException;
import com.project.ClientDesk.exception.ResourceNotFoundException;
import com.project.ClientDesk.mapper.ClientMapper;
import com.project.ClientDesk.repository.ClientRepository;
import com.project.ClientDesk.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {


    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;


    @Override
    public ClientResponseDTO createClient(ClientRequestDTO requestDTO) {
        if(clientRepository.existsByEmail(requestDTO.getEmail())){
            throw new DuplicateResourceException("Email already exists");
        }
        Client client = clientMapper.toEntity(requestDTO);
        Client savedClient = clientRepository.save(client);

        return clientMapper.toResponseDTO(savedClient);
    }

    @Override
    public ClientResponseDTO updateClient(Long clientId, ClientRequestDTO requestDTO) {

        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(()->new ResourceNotFoundException(
                        "Client not found with ID: "+ clientId));

        if(!existingClient.getEmail().equals(requestDTO.getEmail())
                && clientRepository.existsByEmail(requestDTO.getEmail())){
            throw new DuplicateResourceException("Email already exists");
        }

        clientMapper.updateEntityFromDTO(requestDTO, existingClient);
        Client updatedClient = clientRepository.save(existingClient);
        return clientMapper.toResponseDTO(updatedClient);
    }

    @Override
    public void deleteClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Client not found with ID : "+ clientId));

        clientRepository.delete(client);

    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponseDTO getClientById(Long clientId) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(()->new ResourceNotFoundException(
                        "Client not found with ID : "+clientId));
        return clientMapper.toResponseDTO(client);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponseDTO> getAllClients(Pageable pageable) {

        return clientRepository.findAll(pageable).map(clientMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponseDTO> getClientsByStatus(Client.ClientStatus status, Pageable pageable) {
        return clientRepository.findByStatus(status, pageable).map(clientMapper::toResponseDTO);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponseDTO> searchClients(String keyword, Pageable pageable) {
        return clientRepository
                .findByCompanyNameContainingIgnoreCaseOrContactPersonContainingIgnoreCase(keyword,keyword,pageable)
                .map(clientMapper::toResponseDTO);
    }
}
