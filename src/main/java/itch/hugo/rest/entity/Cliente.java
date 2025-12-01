package itch.hugo.rest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Entity
	@Table(name = "cliente")
	public class Cliente {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int Id;
		
		@Column(name = "nombreCliente")
		private String nombreCliente;
		
		@Column(name = "telefono")
		private String telefono;
		
		@Column(name = "correo")
		private String correo;
		
		@Column(name = "status", nullable = false)
	    private int status = 1;

		@Column(name = "username")
		private String username;
	}


