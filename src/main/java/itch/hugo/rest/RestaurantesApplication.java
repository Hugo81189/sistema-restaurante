package itch.hugo.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RestaurantesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantesApplication.class, args);
	}

}
