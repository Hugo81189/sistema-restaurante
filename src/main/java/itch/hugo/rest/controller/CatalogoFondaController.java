package itch.hugo.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itch.hugo.rest.cliente.FondaClienteFeign;
import itch.hugo.rest.dto.ProductoDto;
import itch.hugo.rest.dto.TipoDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/restaurantes/fonda")
public class CatalogoFondaController {

    private final FondaClienteFeign fondaCliente;

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDto>> obtenerProductosDeFonda() {
        List<ProductoDto> productos = fondaCliente.listarTodosLosProductos();
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/tipos")
    public ResponseEntity<List<TipoDto>> obtenerTiposDeFonda() {
        // Usamos el nuevo método del cliente Feign
        List<TipoDto> tipos = fondaCliente.listarTodosLosTipos();
        return ResponseEntity.ok(tipos);
    }
}