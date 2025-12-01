package itch.hugo.rest.cliente;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import itch.hugo.rest.dto.ProductoDto;
import itch.hugo.rest.dto.TipoDto;


@FeignClient(name = "fonda")
public interface FondaClienteFeign {

    @GetMapping("/api/producto")
    List<ProductoDto> listarTodosLosProductos();
    
    @GetMapping("/api/tipo")
    List<TipoDto> listarTodosLosTipos();
}
