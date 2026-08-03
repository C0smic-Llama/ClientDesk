package com.project.ClientDesk.dto;


import com.project.ClientDesk.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean active;
    private User.Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
