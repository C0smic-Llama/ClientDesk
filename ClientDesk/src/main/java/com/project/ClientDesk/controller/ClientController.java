package com.project.ClientDesk.controller;


import com.project.ClientDesk.dto.ClientRequestDTO;
import com.project.ClientDesk.dto.ClientResponseDTO;
import com.project.ClientDesk.entity.Client;
import com.project.ClientDesk.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(
            @Valid
            @RequestBody
            ClientRequestDTO requestDTO) {
        ClientResponseDTO response = clientService.createClient(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @GetMapping
    public ResponseEntity<Page<ClientResponseDTO>> getAllClients(
            @PageableDefault(size = 10, sort = "companyName")
            Pageable pageable) {
        return ResponseEntity.ok(clientService.getAllClients(pageable));

    }

    @GetMapping("/search")
    public ResponseEntity<Page<ClientResponseDTO>> searchClients(
            @RequestParam
            String keyword,
            @PageableDefault(size = 10, sort = "companyName")
            Pageable pageable) {
        return ResponseEntity.ok(clientService.searchClients(keyword, pageable));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientResponseDTO> getClientById(
            @PathVariable
            Long clientId) {
        return ResponseEntity.ok(clientService.getClientById(clientId));
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<ClientResponseDTO> updateClient(
            @PathVariable
            Long clientId,
            @Valid
            @RequestBody
            ClientRequestDTO requestDTO) {
        return ResponseEntity.ok(clientService.updateClient(clientId, requestDTO));

    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClient(
            @PathVariable
            Long clientId) {
        clientService.deleteClient(clientId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<Page<ClientResponseDTO>> getClientsbyStatus(
            @PathVariable
            Client.ClientStatus status,
            @PageableDefault(size = 10, sort = "companyName")
            Pageable pageable) {
        return ResponseEntity.ok(clientService.getClientsByStatus(status, pageable));
    }


}
