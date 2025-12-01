package itch.hugo.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDto {
	private Integer idProducto;
	private String nombre;
	private String descripcion;
	private Double precio;
	private TipoDto idTipo;


}
