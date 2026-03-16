package com.bloodconnector.config;

import com.bloodconnector.entity.User;
import com.bloodconnector.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (!userRepository.existsByEmail("admin@bloodconnector.com")) {

            User admin = new User();

            admin.setFullName("System Admin");
            admin.setEmail("admin@bloodconnector.com");
            admin.setPassword(passwordEncoder.encode("Admin@1234"));
            admin.setPhone("0000000000");
            admin.setRole(User.Role.ROLE_ADMIN);

            userRepository.save(admin);

            System.out.println(
                    "✅ Default admin created — email: admin@bloodconnector.com | password: Admin@1234"
            );
        }
    }
}