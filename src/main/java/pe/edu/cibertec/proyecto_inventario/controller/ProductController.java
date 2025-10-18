package pe.edu.cibertec.proyecto_inventario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.edu.cibertec.proyecto_inventario.dto.ProductDTO;
import pe.edu.cibertec.proyecto_inventario.dto.ProductListDTO;
import pe.edu.cibertec.proyecto_inventario.service.ProductService;

//@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

	private final ProductService productService;

	@GetMapping
	public ResponseEntity<List<ProductListDTO>> getAllProducts() {
		List<ProductListDTO> products = productService.getAllProducts();
		if (products.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(products);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) throws Exception {
		ProductDTO product = productService.getProductById(id)
				.orElseThrow(() -> new Exception("El producto no existe"));
		return ResponseEntity.ok(product);
	}

	@PostMapping
	public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDto) {
		ProductDTO savedProduct = productService.saveProduct(productDto);
		return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer id, @RequestBody ProductDTO productDto)
			throws Exception {
		productService.getProductById(id).orElseThrow(() -> new Exception("El producto no existe"));
		productDto.setProductid(id);

		ProductDTO updatedProduct = productService.updateProduct(productDto);
		return ResponseEntity.ok(updatedProduct);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
}