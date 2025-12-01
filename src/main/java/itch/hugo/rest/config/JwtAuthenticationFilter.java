package itch.hugo.rest.config;

import itch.hugo.rest.service.JwtService; // Importa tu JwtService
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor // Para inyectar JwtService
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 1. Si no hay token, o no empieza con "Bearer ", pasamos al siguiente filtro
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extraer el token (quitando "Bearer ")
        jwt = authHeader.substring(7);

        try {
            // 3. Validar el token y extraer el username
            username = jwtService.extractUsername(jwt);

            // 4. Si tenemos username Y no está autenticado aún
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // 5. Validar el token (esto verifica la expiración)
                if (jwtService.isTokenValid(jwt)) {
                    
                    // --- ¡LA PARTE CLAVE: LEER ROLES! ---
                    Claims claims = jwtService.extractAllClaims(jwt);
                    List<String> roles = claims.get("roles", List.class);

                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(roleName -> new SimpleGrantedAuthority(roleName))
                            .toList();

                    // 6. Crear el objeto de autenticación
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username, // Usamos el username como "principal"
                            null,     // No necesitamos credenciales (password)
                            authorities // ¡Aquí van los roles!
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 7. Establecer al usuario como AUTENTICADO en el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            
        } catch (Exception e) {
            // Si el token es inválido (expirado, firma incorrecta)
            // Simplemente no hacemos nada. El contexto de seguridad quedará 'null'.
            System.err.println("Error al validar el token: " + e.getMessage());
        }

        // 8. Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
