package com.prometheusgrafanalessons.services;

import com.prometheusgrafanalessons.domain.User;
import com.prometheusgrafanalessons.dtos.UserRequestDTO;
import com.prometheusgrafanalessons.dtos.UserResponseDTO;
import com.prometheusgrafanalessons.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        return this.repository.findAll().stream().map(User::toResponse).collect(Collectors.toList());
    }

    public void deleteUser(String userCpf) throws Exception {
        Optional<User> possibleUser = this.repository.findUserByCpf(userCpf);

        if (possibleUser.isPresent()) {
            this.repository.delete(possibleUser.get());
        } else {
            throw new Exception("Nenhum usuário com este cpf foi encontrado.");
        }
    }
    public void updateUser(String cpf, UserRequestDTO userRequestDTO) throws Exception {
        validateUserUpdate(userRequestDTO);
        Optional<User> searchUser = this.repository.findUserByCpf(cpf);
        if (searchUser.isPresent()) {
            User existentUser = searchUser.get();
            copyNonNullProperties(userRequestDTO, existentUser);
            this.repository.save(existentUser);
        } else {
            throw new Exception("Usuário não encontrado com o CPF: " + cpf);
        }
    }

    private void copyNonNullProperties(UserRequestDTO source, User target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    private String[] getNullPropertyNames(UserRequestDTO source) {
        final BeanWrapper srcWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = srcWrapper.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object sourceValue = srcWrapper.getPropertyValue(pd.getName());
            if (sourceValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
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

    private void validateUserUpdate(UserRequestDTO userRequestDTO) throws Exception {
        if (userRequestDTO == null) {
            throw new Exception("Os dados do usuário não podem ser nulos.");
        }
    }
}
