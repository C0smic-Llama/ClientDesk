package com.project.ClientDesk.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEssentialDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String role;
}
