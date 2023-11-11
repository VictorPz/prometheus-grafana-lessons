package com.prometheusgrafanalessons.repositories;

import com.prometheusgrafanalessons.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByCpf(String cpf);
}
