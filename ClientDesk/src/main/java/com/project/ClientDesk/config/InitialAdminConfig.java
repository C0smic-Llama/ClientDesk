package com.project.ClientDesk.config;


import com.project.ClientDesk.entity.User;
import com.project.ClientDesk.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitialAdminConfig {

    @Bean
    CommandLineRunner createInitialAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (userRepository.count() == 0) {

                User admin = new User();

                admin.setFirstName("Admin");
                admin.setLastName("User");
                admin.setEmail("admin@clientdesk.com");
                admin.setPhoneNumber("9876543210");

                admin.setPassword(
                        passwordEncoder.encode("User@123")
                );

                admin.setRole(User.Role.ADMIN);
                admin.setActive(true);

                userRepository.save(admin);

                System.out.println(
                        "Initial admin user created successfully."
                );
            }
        };
    }
}

