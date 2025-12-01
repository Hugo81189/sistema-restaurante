package itch.hugo.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import itch.hugo.rest.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	List<Cliente> findByStatus(int status);

	Optional<Cliente> findByIdAndStatus(Integer id, int status);
	
	 // 💡 Método para buscar por nombre (LIKE) y status=1
    List<Cliente> findByNombreClienteContainingIgnoreCaseAndStatus(String nombreCliente, int status);

    // 💡 Método para buscar por correo (LIKE) y status=1
    List<Cliente> findByCorreoContainingIgnoreCaseAndStatus(String correo, int status);

    // 💡 Método para buscar por teléfono (LIKE) y status=1
    List<Cliente> findByTelefonoContainingIgnoreCaseAndStatus(String telefono, int status);
    
    // Y el findAllByStatus original (para cuando el filtro está vacío)
    List<Cliente> findAllByStatus(int status);
    
    Optional<Cliente> findByUsername(String username);

}
