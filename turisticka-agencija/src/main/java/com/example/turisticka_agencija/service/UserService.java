package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.UpdateProfileRequest;
import com.example.turisticka_agencija.dto.UpdateProfileResponse;
import com.example.turisticka_agencija.dto.UserResponse;
import com.example.turisticka_agencija.exception.BadRequestException;
import com.example.turisticka_agencija.model.Role;
import com.example.turisticka_agencija.model.User;
import com.example.turisticka_agencija.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new BadRequestException("Korisnik nije pronađen"));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BadRequestException("Korisnik nije pronađen");
        }

        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("Korisnik nije pronađen"));
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Korisnik nije pronađen"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + user.getRole().name()
                        )
                )
        );
    }

    public UpdateProfileResponse updateProfile(String username, UpdateProfileRequest request) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new BadRequestException("Korisnik nije pronađen"));

        if (request.getFirstName() != null && !request.getFirstName().isBlank()) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null && !request.getLastName().isBlank()) {
            user.setLastName(request.getLastName());
        }

        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            if (!request.getUsername().equals(user.getUsername())
                    && userRepository.existsByUsername(request.getUsername())) {
                throw new BadRequestException("Username već postoji");
            }

            user.setUsername(request.getUsername());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (!request.getEmail().equals(user.getEmail())
                    && userRepository.existsByEmail(request.getEmail())) {
                throw new BadRequestException("Email već postoji");
            }

            user.setEmail(request.getEmail());
        }

        if (request.getContact() != null) {
            user.setContact(request.getContact().isBlank() ? null : request.getContact());
        }

        User savedUser = userRepository.save(user);

        String newToken = jwtService.generateToken(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole().name()
        );

        UserResponse userResponse = new UserResponse(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getContact(),
                savedUser.getRole()
        );

        return new UpdateProfileResponse(userResponse, newToken);
    }

    public List<UserResponse> getGuides() {

        List<User> guides = userRepository.findByRole(Role.GUIDE);

        if (guides.isEmpty()) {
            throw new BadRequestException("Trenutno nema dostupnih vodiča");
        }

        return guides.stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getContact(),
                        user.getRole()
                ))
                .toList();
    }
}