package pe.edu.cibertec.proyecto_inventario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.cibertec.proyecto_inventario.model.Category;
import pe.edu.cibertec.proyecto_inventario.repository.CategoryRepository;

@RequiredArgsConstructor
@Service
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	public List<Category> getAllCategories(){
		
		return categoryRepository.findAll();
	}
	
	public Optional<Category> getCategoryById(Integer id){
		Optional<Category> category =
				categoryRepository.findById(id);
		if(category.isPresent())
			return category;
		return Optional.empty();
	}
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}


}
