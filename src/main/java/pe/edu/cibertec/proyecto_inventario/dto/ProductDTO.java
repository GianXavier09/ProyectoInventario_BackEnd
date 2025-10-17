package pe.edu.cibertec.proyecto_inventario.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
	private Integer productid;
    private String productname;
    private Double price;
    private Integer stock;
    private Boolean estado;
    private String talla;
    private Integer categoryid;
    private Integer brandid;

}
