package com.project.ClientDesk.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true,length = 100)
    private String email;

    @Column(nullable = false,length = 15)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private Role role;


    @Builder.Default
    @Column(nullable = false)
    boolean active = true;



    public enum Role{
        ADMIN,
        STAFF
    }
}

