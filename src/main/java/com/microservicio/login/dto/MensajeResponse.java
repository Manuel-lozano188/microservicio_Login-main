package com.microservicio.login.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO (Data Transfer Object) 
 */
public record MensajeResponse(
    @NotBlank(message = "El mensaje no puede estar vacio.")
    String mensaje
) {}
