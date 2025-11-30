package com.microservicio.login.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservicio.login.dto.LoginRequest;
import com.microservicio.login.dto.LoginResponse;
import com.microservicio.login.dto.MensajeResponse;
import com.microservicio.login.dto.RegistroRequest;
import com.microservicio.login.model.Rol;
import com.microservicio.login.model.Usuario;
import com.microservicio.login.repository.UsuarioRepository;
import com.microservicio.login.security.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Logica de negocios = validaciones, funciones, etc.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioService {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse iniciarSesion(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.email())
            .orElseThrow(()-> new IllegalStateException("Credenciales invalidas."));

        if(!passwordEncoder.matches(request.password(), usuario.getPassword())) {
            throw new IllegalStateException("Credenciales invalidas.");
        }

        String token = jwtService.generateToken(usuario.getEmail(), usuario.getRol().name());

        return new LoginResponse(token, usuario.getRol().name());
    }

    public MensajeResponse registrar(RegistroRequest request) {

        if(usuarioRepository.findByEmail(request.email()).isPresent()) {
            log.error("El correo ya esta registrado.");
            throw new IllegalStateException("El correo ya esta registrado.");
        }

        if(!request.password().equals(request.confirmarPassword())){
            log.error("Las contras no coinciden.");
            throw new IllegalStateException("Las contras no coinciden.");
        }
    
        Usuario usuario = new Usuario();
        usuario.setName(request.nombre());
        usuario.setLastName(request.apellido());
        usuario.setEmail(request.email());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setRol(Rol.ROLE_USUARIO);  
        
        usuarioRepository.save(usuario);

        return new MensajeResponse("Usuario registrado exitosamente.");
    }   
}
