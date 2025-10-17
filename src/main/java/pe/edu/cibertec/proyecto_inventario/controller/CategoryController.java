package pe.edu.cibertec.proyecto_inventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.edu.cibertec.proyecto_inventario.model.Category;
import pe.edu.cibertec.proyecto_inventario.service.CategoryService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	private final CategoryService categoryService;

	@GetMapping
	public ResponseEntity<List<Category>> 
			getAllCategories() {
		List<Category> categories = categoryService
				.getAllCategories();
		if (categories.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(categories);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> 
			getCategoryById(@PathVariable Integer id) 
					throws Exception 
	{
		Category category = categoryService
				.getCategoryById(id).orElseThrow(
						() -> new Exception(
								"La categoria no existe"));
		return ResponseEntity.ok(category);
	}
	@PostMapping
	public ResponseEntity<Category> saveCategory(
			@RequestBody Category category){
		
		return new ResponseEntity<Category>(
				categoryService.saveCategory(category),
				HttpStatus.CREATED);
	}
	@PutMapping("/{id}")
	public ResponseEntity<Category> updateCategory(
			@PathVariable int id, 
			@RequestBody Category category)
					throws Exception
	{
		Category currentCategory = categoryService
				.getCategoryById(id).orElseThrow(
						() -> new Exception(
								"La categoria no existe"));
		currentCategory.setCategoryname(
				category.getCategoryname());
		currentCategory.setDescription(
				category.getDescription());
		return new ResponseEntity<Category>(
				categoryService.saveCategory(currentCategory),
				HttpStatus.OK);
	}
	

}
