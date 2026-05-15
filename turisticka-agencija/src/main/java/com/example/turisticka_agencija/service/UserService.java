package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.ChangePasswordRequest;
import com.example.turisticka_agencija.dto.UpdateProfileRequest;
import com.example.turisticka_agencija.exception.BadRequestException;
import com.example.turisticka_agencija.model.User;
import com.example.turisticka_agencija.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public User updateProfile(String username, UpdateProfileRequest request) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new BadRequestException("Korisnik nije pronađen"));

        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            user.setUsername(request.getUsername());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }

        if (request.getContact() != null && !request.getContact().isBlank()) {
            user.setContact(request.getContact());
        }

        return userRepository.save(user);
    }

}