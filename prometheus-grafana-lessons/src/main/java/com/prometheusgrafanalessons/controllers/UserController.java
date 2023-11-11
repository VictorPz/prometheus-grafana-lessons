package com.prometheusgrafanalessons.controllers;

import com.prometheusgrafanalessons.domain.User;
import com.prometheusgrafanalessons.dtos.UserRequestDTO;
import com.prometheusgrafanalessons.dtos.UserResponseDTO;
import com.prometheusgrafanalessons.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) throws Exception {
        User user = userService.saveUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user.toResponse());
    }

    @GetMapping("/list")
    public  ResponseEntity<?> listUsers() {
        List<UserResponseDTO> userList = userService.findAllUsers();

        if (userList.isEmpty()) {
            return ResponseEntity.ok("Nenhum usuário encontrado");
        }
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @DeleteMapping("/delete/{cpf}")
    public ResponseEntity<?> deleteUserByCpf(@PathVariable String cpf) {
        try {
            userService.deleteUser(cpf);
            return ResponseEntity.ok("Usuário com CPF " + cpf + " foi deletado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a solicitação");
        }
    }

    @PutMapping("/update/{cpf}")
    public ResponseEntity<?> updateUserByCpf(@PathVariable String cpf, @RequestBody UserRequestDTO updatedUser) {
        try {
            userService.updateUser(cpf, updatedUser);
            return ResponseEntity.ok("Usuário com CPF " + cpf + " foi atualizado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a solicitação");
        }
    }
}
