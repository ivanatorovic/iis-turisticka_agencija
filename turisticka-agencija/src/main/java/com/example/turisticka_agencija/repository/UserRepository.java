package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}