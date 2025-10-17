package pe.edu.cibertec.proyecto_inventario.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductListDTO {
	private Integer productid;
	private String productname;
	private Double price;
	private Integer stock;
	private Boolean estado;
	private String talla;
	private String categoryname;
	private String marcanombre;
}