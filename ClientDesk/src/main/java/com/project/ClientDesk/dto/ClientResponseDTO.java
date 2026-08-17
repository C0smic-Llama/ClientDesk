package com.project.ClientDesk.dto;


import com.project.ClientDesk.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponseDTO {

    private Long id;
    private String companyName;
    private String contactPerson;
    private String email;
    private String contactNumber;
    private Client.ClientStatus status;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
