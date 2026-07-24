package com.project.ClientDesk.dto;


import com.project.ClientDesk.entity.Client;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequestDTO {


    @NotBlank(message = "Company name is required")
    @Size(max = 100, message = "Company name cannot exceed 100 characters")
    private String companyName;


    @NotBlank(message  = "Contact Person is required")
    private String contactPerson;

    @NotBlank(message  = "Email is required")
    private String email;

    @NotBlank (message = "Contact number is required")
    @Pattern( regexp = "^[0-9]{10}$", message = "Phone Number must contain exactly 10 digits")
    private String contactNumber;

    private Client.ClientStatus status;

    @NotBlank(message  = "Address is required")
    @Size(max = 250)
    private String address;


}
