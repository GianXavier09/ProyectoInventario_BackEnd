package pe.edu.cibertec.proyecto_inventario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.edu.cibertec.proyecto_inventario.model.Brand;
import pe.edu.cibertec.proyecto_inventario.service.BrandService;

//@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/brand")
public class BrandController {
	private final BrandService brandService;

	@GetMapping
	public ResponseEntity<List<Brand>> getAllBrands() {
		List<Brand> brands = brandService.getAllBrands();
		if (brands.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(brands);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Brand> getBrandById(@PathVariable Integer id) throws Exception {
		Brand brand = brandService.getBrandById(id).orElseThrow(() -> new Exception("La marca no existe"));
		return ResponseEntity.ok(brand);
	}

	@PostMapping
	public ResponseEntity<Brand> saveBrand(@RequestBody Brand brand) {
		return new ResponseEntity<Brand>(brandService.saveBrad(brand), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Brand> updateBrand(@PathVariable int id, @RequestBody Brand brand) throws Exception {
		Brand currentBrand = brandService.getBrandById(id).orElseThrow(() -> new Exception("La marca no existe"));
		currentBrand.setMarcanombre(brand.getMarcanombre());
		currentBrand.setEmail(brand.getEmail());
		currentBrand.setHomepage(brand.getHomepage());
		return new ResponseEntity<Brand>(brandService.saveBrad(currentBrand), HttpStatus.OK);
	}

}
