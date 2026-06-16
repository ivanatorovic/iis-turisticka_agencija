package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.*;
import com.example.turisticka_agencija.exception.BadRequestException;
import com.example.turisticka_agencija.model.*;
import com.example.turisticka_agencija.repository.UserRepository;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.turisticka_agencija.dto.ChangePasswordRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RestTemplate restTemplate;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.restTemplate = restTemplate;
    }

    public AuthResponse register(RegisterRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Lozinke se ne poklapaju");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username već postoji");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email već postoji");
        }

        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getRole(),
                request.getContact()
        );

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole().name()
        );

        syncCreateCustomerToRecommendationService(savedUser);

        return new AuthResponse(
                token,
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    public AuthResponse login(LoginRequest request) {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

        } catch (Exception e) {

            throw new BadRequestException(
                    "Pogresan username ili password"
            );
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new BadRequestException("Korisnik nije pronađen"));

        String token = jwtService.generateToken(
                user.getId(),
                user.getUsername(),
                user.getRole().name()
        );

        return new AuthResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    public void changePassword(String username, ChangePasswordRequest request) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new BadRequestException("Korisnik nije pronađen"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Stara lozinka nije ispravna");
        }

        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new BadRequestException("Nova lozinka i potvrda lozinke se ne poklapaju");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }

    private void syncCreateCustomerToRecommendationService(User user) {
        if (user.getRole() != Role.CUSTOMER) {
            return;
        }

        String url = "http://dodatne-aktivnosti-service:8082/customers";

        RecommendationCustomerDto dto = new RecommendationCustomerDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getContact()
        );

        restTemplate.postForEntity(url, dto, Void.class);
    }

}