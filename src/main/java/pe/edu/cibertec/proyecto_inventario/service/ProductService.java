package pe.edu.cibertec.proyecto_inventario.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.cibertec.proyecto_inventario.dto.ProductDTO;
import pe.edu.cibertec.proyecto_inventario.dto.ProductListDTO;
import pe.edu.cibertec.proyecto_inventario.model.Product;
import pe.edu.cibertec.proyecto_inventario.repository.ProductRepository;
import pe.edu.cibertec.proyecto_inventario.repository.BrandRepository;
import pe.edu.cibertec.proyecto_inventario.repository.CategoryRepository;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public List<ProductListDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToListDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(Integer id) {
        return productRepository.findById(id)
                .map(this::mapToDTO);
    }

    public ProductDTO saveProduct(ProductDTO dto) {
    	if (dto.getStock() != null) {
			dto.setEstado(dto.getStock() > 0);
		}
        Product product = mapToEntity(dto);
        Product savedProduct = productRepository.save(product);
        return mapToDTO(savedProduct);
    }

    public ProductDTO updateProduct(ProductDTO dto) {
    	if (dto.getStock() != null) {
			dto.setEstado(dto.getStock() > 0);
		}
        Product product = mapToEntity(dto);
        Product updated = productRepository.save(product);
        return mapToDTO(updated);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    private Product mapToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setProductid(dto.getProductid());
        product.setProductname(dto.getProductname());
        product.setPrice(dto.getPrice()); 
        product.setStock(dto.getStock()); 
        product.setEstado(dto.getEstado());
        product.setTalla(dto.getTalla());

        product.setCategories(categoryRepository.findById(dto.getCategoryid())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada")));
        
        product.setBrands(brandRepository.findById(dto.getBrandid())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada")));
        
        return product;
    }

    private ProductDTO mapToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductid(product.getProductid());
        dto.setProductname(product.getProductname());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setEstado(product.getEstado());
        dto.setTalla(product.getTalla());
        dto.setCategoryid(product.getCategories().getCategoryid());
        dto.setBrandid(product.getBrands().getBrandid());
        return dto;
    }

    private ProductListDTO mapToListDTO(Product product) {
        ProductListDTO dto = new ProductListDTO();
        dto.setProductid(product.getProductid());
        dto.setProductname(product.getProductname());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setEstado(product.getEstado());
        dto.setTalla(product.getTalla());
        dto.setCategoryname(product.getCategories() != null ? product.getCategories().getCategoryname() : null);
        dto.setMarcanombre(product.getBrands() != null ? product.getBrands().getMarcanombre() : null);
        return dto;
    }
}
