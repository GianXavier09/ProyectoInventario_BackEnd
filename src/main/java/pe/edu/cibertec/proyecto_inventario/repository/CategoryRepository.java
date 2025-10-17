package pe.edu.cibertec.proyecto_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.cibertec.proyecto_inventario.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer>{


}