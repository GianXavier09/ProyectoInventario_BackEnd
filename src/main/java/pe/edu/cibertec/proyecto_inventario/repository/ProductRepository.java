package pe.edu.cibertec.proyecto_inventario.repository;


/*import java.util.List;*/

import org.springframework.data.jpa.repository.JpaRepository;
/*import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.transaction.Transactional;*/
import org.springframework.stereotype.Repository;

import pe.edu.cibertec.proyecto_inventario.model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	

	
	
	
	
	
	
	
	
	
	
	
	
	/*DUDA NO BORRAR
    // Actualizar producto usando SQL nativo
    @Transactional
    @Modifying
    @Query(value = """
        UPDATE producto SET 
            productname = :productname,
            price = :price,
            stock = :stock,
            talla = :talla,
            categoryid = :categoryid,
            brandid = :brandid,
            estado = :estado
        WHERE productid = :productid
        """, nativeQuery = true)
    void actualizarProducto(
			 @Param("productname")String productname, 
			 @Param("price")Double price, 
			 @Param("stock")Integer stock, 
			 @Param("estado")Boolean estado, 
			 @Param("talla")String talla,
			 @Param("categoryid")Integer categoryid, 
			 @Param("brandid")Integer brandid, 
			 @Param("productid")Integer productid);
    
    List<Product> findByCategoriesCategoryid(Integer categoryid);
    List<Product> findByBrandsBrandid(Integer brandid);
    List<Product> findByCategoriesCategoryidAndBrandsBrandid(Integer categoryid, Integer brandid);
*/
}