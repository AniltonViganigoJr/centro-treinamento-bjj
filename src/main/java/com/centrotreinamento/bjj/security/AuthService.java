package com.centrotreinamento.bjj.security;

import org.springframework.stereotype.Service;

import com.centrotreinamento.bjj.domain.Usuario;
import com.centrotreinamento.bjj.dto.request.LoginRequestDTO;
import com.centrotreinamento.bjj.dto.response.LoginResponseDTO;
import com.centrotreinamento.bjj.repository.UsuarioRepository;

@Service
public class AuthService {

    private UsuarioRepository usuarioRepository;
    private JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        
        Usuario usuario = usuarioRepository.findByLogin(dto.login())
        .orElseThrow(() -> new RuntimeException("Usuário inválido"));

        if (!usuario.getSenha().equals(dto.senha())) {
            throw new RuntimeException("Usuário inválido");   
        }

        String token = jwtService.gerarToken(usuario.getLogin());

        return new LoginResponseDTO(token);
    }
}
