package itch.hugo.rest.mapper;

import itch.hugo.rest.dto.ClienteDto;
import itch.hugo.rest.entity.Cliente;

public class ClienteMapper {
	public static ClienteDto mapToClienteDto(Cliente cliente) {
	    if (cliente == null) {
	        return null; // O puedes lanzar una excepción si es preferible
	    }
	    
	    // Mapeo existente
	    ClienteDto clienteDto = new ClienteDto();
	    clienteDto.setId(cliente.getId());
	    clienteDto.setNombreCliente(cliente.getNombreCliente());
	    clienteDto.setTelefono(cliente.getTelefono());
	    clienteDto.setCorreo(cliente.getCorreo());
	    clienteDto.setUsername(cliente.getUsername());
	    
	    // 💡 AÑADIR: Mapear el nuevo campo 'status'
	    clienteDto.setStatus(cliente.getStatus()); 
	    
	    return clienteDto;
	}
	
	public static Cliente mapToCliente(ClienteDto clienteDto) {
	    if (clienteDto == null) {
	        return null;
	    }
	    
	    // Mapeo existente
	    Cliente cliente = new Cliente();
	    // Asignar ID solo si lo trae (para actualizaciones)
	    if (clienteDto.getId() > 0) {
	        cliente.setId(clienteDto.getId());
	    }
	    cliente.setNombreCliente(clienteDto.getNombreCliente());
	    cliente.setTelefono(clienteDto.getTelefono());
	    cliente.setCorreo(clienteDto.getCorreo());
	    
	    // 💡 AÑADIR: Mapear el nuevo campo 'status'
	    // Si el DTO trae el status (ej. en una actualización), úsalo. 
	    // Si no lo trae (ej. en una creación), por defecto será 1, aunque tu Entidad ya lo maneja.
	    // Esto asegura que el status pueda ser manipulado si se recibe desde el DTO (aunque en Soft Delete se recomienda solo cambiarlo en el Service).
	    if (clienteDto.getStatus() != 0) {
	        cliente.setStatus(clienteDto.getStatus());
	    } else {
	        // En creación, si el DTO no lo especifica, forzar el 1
	        cliente.setStatus(1); 
	    }
	    cliente.setUsername(clienteDto.getUsername());
	    
	    return cliente;
	}
}
