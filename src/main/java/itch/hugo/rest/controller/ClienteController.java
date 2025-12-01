package itch.hugo.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import itch.hugo.rest.cliente.FondaClienteFeign;
import itch.hugo.rest.dto.ClienteDto;
import itch.hugo.rest.dto.ProductoDto;
import itch.hugo.rest.service.ClienteService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {
	
	//Inyectar la dependencia
	private ClienteService clienteService;
	private FondaClienteFeign fondaCliente;

	
	//Construir el REST API para agregar un cliente
	@PostMapping
	
	public ResponseEntity<ClienteDto> crearCliente(@RequestBody ClienteDto clienteDto){
		ClienteDto guardarCliente = clienteService.createCliente(clienteDto);
		return new ResponseEntity<>(guardarCliente, HttpStatus.CREATED);
	}
	
	//Construir el get del cliente REST API
	@GetMapping("{id}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'CAJERO', 'CLIENTE', 'MESERO')")
	public ResponseEntity<ClienteDto> getClienteById(@PathVariable("id")Integer clienteId){
		ClienteDto clienteDto = clienteService.getClienteById(clienteId);
		return ResponseEntity.ok(clienteDto);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'CAJERO', 'MESERO')")
    public ResponseEntity<List<ClienteDto>> getAllClientes(
            @RequestParam(value = "filtro", required = false, defaultValue = "") String filtro,
            @RequestParam(value = "campo", required = false, defaultValue = "nombre") String campoFiltro) {
        
        List<ClienteDto> clientes = clienteService.getAllCliente(filtro, campoFiltro);
        return ResponseEntity.ok(clientes);
    }
	
	//Construir REST API update Cliente. Exponer un endpoint HTTP PUT para actualizar un cliente
	@PutMapping("{id}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'CAJERO')")
	public ResponseEntity<ClienteDto> updateCliente(@PathVariable("id") Integer clienteId, 
			@RequestBody ClienteDto updateCliente){
		ClienteDto clienteDto = clienteService.updateCliente(clienteId, updateCliente);
		return ResponseEntity.ok(clienteDto);
 	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<String> deleteCliente(@PathVariable("id") Integer clienteId){
		clienteService.deleteCliente(clienteId);
		return ResponseEntity.ok("Registro eliminado con exito");
	}
	
	@GetMapping("/productos")
	@PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ProductoDto>> obtenerProductosDeFonda() {
        // Usamos el cliente Feign para llamar a fonda y obtener la lista
        List<ProductoDto> productos = fondaCliente.listarTodosLosProductos();
        return ResponseEntity.ok(productos);
    }
	
	@GetMapping("/by-username/{username}")
    @PreAuthorize("isAuthenticated()") // ¡Debe estar protegido!
    public ResponseEntity<ClienteDto> getClientePorUsername(
                                @PathVariable("username") String username) {
                                    
        ClienteDto clienteDto = clienteService.getClientePorUsername(username);
        return ResponseEntity.ok(clienteDto);
    }
	

}
