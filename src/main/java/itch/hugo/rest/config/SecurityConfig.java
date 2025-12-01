package itch.hugo.rest.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // Para inyectar el filtro
@EnableMethodSecurity
public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthFilter;

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(authz -> authz
            		
            	.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                
                // La regla pública que ya tenías
                .requestMatchers(HttpMethod.POST, "/api/cliente").permitAll()
                
                // (Opcional: si quieres que tus productos de fonda sean públicos)
                .requestMatchers(HttpMethod.GET, "/api/cliente/productos").permitAll()

                // Todo lo demás DEBE estar autenticado
                .anyRequest().authenticated()
            )
            
            // ¡AÑADIR ESTO!
            // Le dice a Spring que use tu filtro JWT *antes* que el filtro de login
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}