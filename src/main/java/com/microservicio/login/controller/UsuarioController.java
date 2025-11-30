package com.microservicio.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.login.dto.LoginRequest;
import com.microservicio.login.dto.LoginResponse;
import com.microservicio.login.dto.MensajeResponse;
import com.microservicio.login.dto.RegistroRequest;
import com.microservicio.login.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
        @Valid @RequestBody LoginRequest request 
    ) {
        LoginResponse response = usuarioService.iniciarSesion(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registro")
    public ResponseEntity<MensajeResponse> registrar(
        @Valid @RequestBody RegistroRequest request
    ) {
        MensajeResponse response = usuarioService.registrar(request);
        return ResponseEntity.ok(response);
    }
}
