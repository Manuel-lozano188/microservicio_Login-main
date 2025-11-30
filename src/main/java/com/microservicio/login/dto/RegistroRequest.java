package com.microservicio.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistroRequest(
    @NotBlank(message = "El nombre no puede estar vacio.")
    String nombre,

    @NotBlank(message = "El apellido no puede estar vacio.")
    String apellido,

    @NotBlank(message = "El email no puede estar vacio.")
    @Email
    String email,
    
    @NotBlank(message = "La contra no puede estar vacio.")
    String password,

    @NotBlank(message = "La confirmacion no puede estar vacia.")
    String confirmarPassword
) {

}