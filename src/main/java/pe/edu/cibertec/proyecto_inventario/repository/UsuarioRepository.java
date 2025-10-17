package pe.edu.cibertec.proyecto_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.proyecto_inventario.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository 
       extends JpaRepository<Usuario, Integer> {
   
    Optional<Usuario> findByNomusuario(String nomUsuario);
}