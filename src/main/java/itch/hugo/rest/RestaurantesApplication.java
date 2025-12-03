package itch.hugo.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableFeignClients
public class RestaurantesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantesApplication.class, args);
	}
	
	@Configuration
	public class GlobalCorsConfig {

		@Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**")
	                        // ⚠️ IMPORTANTE: Usa allowedOriginPatterns para permitir subdominios de Vercel
	                        .allowedOriginPatterns(
	                            "http://localhost:*", 
	                            "https://*.vercel.app" 
	                        )
	                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	                        .allowedHeaders("*")
	                        .allowCredentials(true);
	            }
	        };
	    }
	}

}
