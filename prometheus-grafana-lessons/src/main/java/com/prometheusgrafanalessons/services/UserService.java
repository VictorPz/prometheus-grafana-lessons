package com.prometheusgrafanalessons.services;

import com.prometheusgrafanalessons.domain.User;
import com.prometheusgrafanalessons.dtos.UserRequestDTO;
import com.prometheusgrafanalessons.dtos.UserResponseDTO;
import com.prometheusgrafanalessons.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User saveUser(UserRequestDTO newUser) throws Exception {
        validateNewUser(newUser);
        return this.repository.save(newUser.toEntity());
    }

    public List<UserResponseDTO> findAllUsers() {
        return repository.findAll().stream().map(User::toResponse).collect(Collectors.toList());
    }

    public void deleteUser(String userCpf) throws Exception {
        Optional<User> possibleUser = repository.findUserByCpf(userCpf);

        if (possibleUser.isPresent()) {
            repository.delete(possibleUser.get());
        } else {
            throw new Exception("Nenhum usuário com este cpf foi encontrado.");
        }
    }

    private void validateNewUser(UserRequestDTO newUser) throws Exception {
        if (newUser == null) {
            throw new Exception("O usuário não pode ser nulo.");
        }

        if (this.repository.findUserByCpf(newUser.getCpf()).isPresent()) {
            throw new Exception("O CPF já está em uso por outro usuário.");
        }

        if (this.repository.findUserByCpf(newUser.getEmail()).isPresent()) {
            throw new Exception("O CPF já está em uso por outro usuário.");
        }
    }
}
