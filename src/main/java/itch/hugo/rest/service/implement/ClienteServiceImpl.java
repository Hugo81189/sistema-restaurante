package itch.hugo.rest.service.implement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itch.hugo.rest.dto.ClienteDto;
import itch.hugo.rest.entity.Cliente;
import itch.hugo.rest.mapper.ClienteMapper;
import itch.hugo.rest.repository.ClienteRepository;
import itch.hugo.rest.service.ClienteService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public ClienteDto createCliente(ClienteDto clienteDto) {
		Cliente cliente = ClienteMapper.mapToCliente(clienteDto);
		Cliente savedCliente = clienteRepository.save(cliente);

		return ClienteMapper.mapToClienteDto(savedCliente);
	}

	@Override
    public ClienteDto getClienteById(Integer clienteId) {
        // Usa el nuevo método findByIdAndStatus para filtrar por status = 1
        Optional<Cliente> clienteOptional = clienteRepository.findByIdAndStatus(clienteId, 1); 
        
        // Manejo de error si no se encuentra (borrado o ID inexistente)
        Cliente cliente = clienteOptional.orElseThrow(
            () -> new RuntimeException("Cliente con ID " + clienteId + " no encontrado o inactivo.")
        );
        
        return ClienteMapper.mapToClienteDto(cliente);
    }

	@Override
    public List<ClienteDto> getAllCliente(String filtro, String campoFiltro) { // 💡 NUEVOS PARÁMETROS
        List<Cliente> clientes;
        final int STATUS_ACTIVO = 1;

        if (filtro == null || filtro.isEmpty()) {
            // Si no hay filtro, obtenemos todos los activos
            clientes = clienteRepository.findAllByStatus(STATUS_ACTIVO);
        } else {
            // Aplicamos la búsqueda dinámica según el campo
            String terminoBusqueda = "%" + filtro + "%"; // Usamos % para LIKE
            
            switch (campoFiltro.toLowerCase()) {
                case "nombre":
                    clientes = clienteRepository.findByNombreClienteContainingIgnoreCaseAndStatus(filtro, STATUS_ACTIVO);
                    break;
                case "correo":
                    clientes = clienteRepository.findByCorreoContainingIgnoreCaseAndStatus(filtro, STATUS_ACTIVO);
                    break;
                case "telefono":
                    // Nota: Para teléfono, es mejor usar LIKE sin IgnoreCase, solo si no usas %
                    clientes = clienteRepository.findByTelefonoContainingIgnoreCaseAndStatus(filtro, STATUS_ACTIVO);
                    break;
                default:
                    // Si el campo es inválido, devolvemos todos los activos
                    clientes = clienteRepository.findAllByStatus(STATUS_ACTIVO);
            }
        }

        // Mapear y devolver los DTOs...
        return clientes.stream()
				.map(ClienteMapper::mapToClienteDto)
				.collect(Collectors.toList());
    }

	
	@Override
	public ClienteDto updateCliente(Integer clienteId, ClienteDto updatecliente) {
		// Buscar el registro a modificar
		Cliente cliente = clienteRepository.findByIdAndStatus(clienteId, 1).orElseThrow(
	            () -> new RuntimeException("Cliente con ID " + clienteId + " no encontrado o inactivo."));

		// Modificar los atributos
		cliente.setNombreCliente(updatecliente.getNombreCliente());
		cliente.setTelefono(updatecliente.getTelefono());
		cliente.setCorreo(updatecliente.getCorreo());

		// Guardar cambios
		Cliente updateClienteObj = clienteRepository.save(cliente);

		return ClienteMapper.mapToClienteDto(updateClienteObj);
	}
	
	@Override
    public void deleteCliente(Integer clienteId) {
        // 1. Buscar el registro a "eliminar" (sin importar su status inicial)
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(
            () -> new RuntimeException("Cliente con ID " + clienteId + " no encontrado.")
        );
        
        // 2. Aplicar el Borrado Suave (Soft Delete)
        // En lugar de clienteRepository.delete(cliente), se establece status=0
        cliente.setStatus(0); // Establecer el status a 0 (Inactivo)
        
        // 3. Guardar el cambio en la base de datos
        clienteRepository.save(cliente);
    }
	
		
		public ClienteDto getClientePorUsername(String username) {
        
        // 1. Busca en la base de datos
        Cliente cliente = clienteRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con username: " + username));
        
        // 2. Convierte la Entidad a DTO
        // (Aquí uso el constructor de tu DTO, pero un mapper es mejor)
        return new ClienteDto(
            cliente.getId(),
            cliente.getNombreCliente(),
            cliente.getTelefono(),
            cliente.getCorreo(),
            cliente.getStatus(),
            cliente.getUsername()
        );
    }





}
