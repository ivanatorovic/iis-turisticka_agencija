package com.example.turisticka_agencija.config;

import com.example.turisticka_agencija.model.Role;
import com.example.turisticka_agencija.model.User;
import com.example.turisticka_agencija.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createOrUpdateUser(
                "Operater",
                "Zalbi",
                "operater",
                "operater@test.com",
                "12345",
                Role.COMPLAINT_OPERATOR,
                "0602222222"
        );

        createOrUpdateUser(
                "Tim",
                "Smestaj",
                "smestaj",
                "smestaj@test.com",
                "12345",
                Role.COMPLAINT_TEAM_ACCOMMODATION,
                "0603333333"
        );

        createOrUpdateUser(
                "Tim",
                "Prevoz",
                "prevoz",
                "prevoz@test.com",
                "12345",
                Role.COMPLAINT_TEAM_TRANSPORT,
                "0604444444"
        );

        createOrUpdateUser(
                "Tim",
                "Dokumentacija",
                "dokumentacija",
                "dokumentacija@test.com",
                "12345",
                Role.COMPLAINT_TEAM_DOCUMENTATION,
                "0605555555"
        );

        createOrUpdateUser(
                "Tim",
                "Ostalo",
                "ostalo",
                "ostalo@test.com",
                "12345",
                Role.COMPLAINT_TEAM_OTHER,
                "0606666666"
        );

        createOrUpdateUser(
                "Menadzer",
                "Zalbi",
                "menadzer-zalbi",
                "menadzer.zalbi@test.com",
                "12345",
                Role.COMPLAINT_MANAGER,
                "0607777777"
        );

        createOrUpdateUser(
                "Admin",
                "Test",
                "admin",
                "admin@test.com",
                "12345",
                Role.ADMIN,
                "0608888888"
        );

        createOrUpdateUser(
                "Direktor",
                "Test",
                "direktor",
                "direktor@test.com",
                "12345",
                Role.DIRECTOR,
                "0609999999"
        );
    }

    private void createOrUpdateUser(
            String firstName,
            String lastName,
            String username,
            String email,
            String password,
            Role role,
            String contact
    ) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            user = new User(
                    firstName,
                    lastName,
                    username,
                    email,
                    passwordEncoder.encode(password),
                    role,
                    contact
            );

            userRepository.save(user);
            return;
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setContact(contact);

        userRepository.save(user);
    }
}