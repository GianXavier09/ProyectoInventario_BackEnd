package pe.edu.cibertec.proyecto_inventario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.cibertec.proyecto_inventario.model.Brand;
import pe.edu.cibertec.proyecto_inventario.repository.BrandRepository;

@RequiredArgsConstructor
@Service
public class BrandService {
	
	private final BrandRepository brandRepository;
   
	public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

	public Optional<Brand> getBrandById(Integer id){
		Optional<Brand> brand =
				brandRepository.findById(id);
		if(brand.isPresent())
			return brand;
		return Optional.empty();
	}
	public Brand saveBrad(Brand brand) {
		return brandRepository.save(brand);
	}
}
