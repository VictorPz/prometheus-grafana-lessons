package com.prometheusgrafanalessons.repositories;

import com.prometheusgrafanalessons.domain.User;
import com.prometheusgrafanalessons.dtos.UserResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByCpf(String cpf);
}
