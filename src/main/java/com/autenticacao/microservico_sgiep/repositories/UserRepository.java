package com.autenticacao.microservico_sgiep.repositories;

import com.autenticacao.microservico_sgiep.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
