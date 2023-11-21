package com.nikak.pspkurssecurity.repositories;

import com.nikak.pspkurssecurity.entities.Role;
import com.nikak.pspkurssecurity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User findByRole(Role role);
}
