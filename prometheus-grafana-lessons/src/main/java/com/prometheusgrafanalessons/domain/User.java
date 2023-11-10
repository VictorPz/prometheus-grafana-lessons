package com.prometheusgrafanalessons.domain;

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
}
