package pe.edu.cibertec.proyecto_inventario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "producto")
public class Product {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productid;
    private String productname;
    private Double price;
    private Integer stock;
    private Boolean estado;
    private String talla;
    private String imagenUrl;

    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category categories;

    @ManyToOne
    @JoinColumn(name = "brandid")
    private Brand brands;

}
