package com.microservicio.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "El correo no puede estar vacio.")
    @Email
    String email,
    @NotBlank(message = "La contra no puede estar vacia.")
    String password
) {}
