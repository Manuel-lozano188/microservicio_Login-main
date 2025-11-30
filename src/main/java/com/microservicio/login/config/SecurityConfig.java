package com.microservicio.login.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.microservicio.login.security.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

/**
 * Configuracion central de Spring Security.
 * Habilita la seguridad web y define las reglas de acceso.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        //1. Deshabilitar CSRF
        .csrf(AbstractHttpConfigurer::disable)
        //2. Definir las reglas de autorizacion de peticiones
        .authorizeHttpRequests(auth -> auth
        .anyRequest().permitAll()
        )
        //3. Establecer la politica de sesion como STATELESS
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        //4. Añadir nuestro filtro JWT antes del filtro de login por defecto de Spring
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
