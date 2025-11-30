package com.microservicio.login.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Filtro que intercepta todas las peticiones para validar el token JWT.
 * Se ejecuta una vez por cada peticion gracias a OncePerRequestFilter.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{

  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain) throws ServletException, IOException {

      String path = request.getServletPath();
      System.out.println("Interceptando ruta: " + path);


      // Excluir por completo las rutas de Swagger y login
      if (path.equals("/api/usuarios/registro") ||
          path.equals("/api/usuarios/login")) {
        filterChain.doFilter(request, response);
        return;
      }

      //1. Obtener el header 'Authorization'
      final String authHeader = request.getHeader("Authorization");

      //2. Vaildar si el header es nulo o no empieza con "Bearer"
      if(authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
      }

      //3. Extraer el token (quitando el prefijo "Bearer ")
      final String jwt = authHeader.substring(7);

      //4. Validar el token usando el JwtService
      if (jwtService.isTokenValid(jwt)){

        //5. Extraer el username y role del token
        String username = jwtService.extractEmail(jwt);
        String role = jwtService.extractRole(jwt);

        //6. Crear la autenticacion con los datos extraidos
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          username,
          null,
          Collections.singletonList(authority)
          );

          /**
           * 7. Establecer la autenticacion en el contexto de seguridad de Spring
           *   Esto indica que el usuario esta autenticado para esta peticion
           */
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }

      //8. Continuar con la cadena de filtros
      filterChain.doFilter(request, response);
    }
}
