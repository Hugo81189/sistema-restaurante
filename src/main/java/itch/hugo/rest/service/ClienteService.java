package itch.hugo.rest.service;

import java.util.List;

import itch.hugo.rest.dto.ClienteDto;

public interface ClienteService {

	ClienteDto createCliente (ClienteDto clienteDto);
	
	//Buscar cliente por id
	ClienteDto getClienteById (Integer clienteId);
	
	//Obtener todos los clientes
	List<ClienteDto> getAllCliente(String filtro, String campoFiltro);
	
	//Construir REST API Update Cliente
	ClienteDto updateCliente (Integer clienteId, ClienteDto updateCliente);
	
	//Construir DELETE REST API cliente
	
	
	void deleteCliente (Integer clienteId);
	
	ClienteDto getClientePorUsername(String username);
}
