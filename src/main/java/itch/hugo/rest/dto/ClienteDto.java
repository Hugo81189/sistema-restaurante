package itch.hugo.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClienteDto {

	 private int id;  
	 private String nombreCliente; 
	 
	 private String telefono;
	 
	 private String correo;
	 
	 private int status;
	 
	 private String username;
	 
}
