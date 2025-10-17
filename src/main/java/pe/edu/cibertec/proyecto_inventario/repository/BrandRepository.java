package pe.edu.cibertec.proyecto_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.cibertec.proyecto_inventario.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Integer>{

}
