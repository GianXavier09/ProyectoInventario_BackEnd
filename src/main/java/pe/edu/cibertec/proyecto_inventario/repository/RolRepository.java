package pe.edu.cibertec.proyecto_inventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.cibertec.proyecto_inventario.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
	
	Optional<Rol> findByNomrol(String nomrol);
	
}