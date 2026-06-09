package com.centrotreinamento.bjj.domain;

import java.util.UUID;

import com.centrotreinamento.bjj.domain.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Usuario {

    @Id
    @GeneratedValue
    private UUID id;

    private String login;
    private String senha;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    public Usuario() {
    }

    public Usuario(String login, String senha, Role role) {
        this.login = login;
        this.senha = senha;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public Role getRole() {
        return role;
    }

}
