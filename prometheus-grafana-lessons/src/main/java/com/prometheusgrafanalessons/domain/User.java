package com.prometheusgrafanalessons.domain;

import com.prometheusgrafanalessons.dtos.UserResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "usuario")
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "nome")
    private String firstName;
    @Column(name = "sobrenome")
    private String lastName;
    @Column(name = "cpf",unique = true)
    private String cpf;
    @Column(name = "email", unique = true)
    private String email;

    @Deprecated
    public User(){
    }

    public User(String firstName, String lastName, String cpf, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.email = email;
    }

    public UserResponseDTO toResponse() {
        return new UserResponseDTO(firstName, lastName, cpf, email);
    }

    public String getId() {
        return id;
    }
}
