package com.microservicio.login.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginResponse(
    @NotBlank(message = "El token no puede estar vacio.")
    String token,
    @NotBlank(message = "El rol no puede estar vacio.")
    String rol
) {}
