package cibertec.edu.pe.inventario_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name="productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    @NotBlank(message = "El codigo es obligatorio")
    @Size(min=3, max =50, message = "El codigo debe tener entre 3 y 50 caracteres")
    private String codigo;


    @Column(unique = true, nullable = false, length = 50)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min=3, max =200, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message ="El precio es obligatorio" )
    @DecimalMin(value="0.01", message ="El precio debe ser mayor a 0")
    private BigDecimal precio;

    @Column(nullable = false)
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock  no puede ser menor a 0")
    private Integer stock;

    @Column(nullable = false)
    @NotNull(message = "El stock minimo es obligatorio")
    @Min(value = 0, message = "El stock minimo no puede ser menor a 0")
    private Integer stockMinimo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="categoria_id", nullable = false)
    @NotNull(message ="La categoria es obligatoria" )
    private Categoria categoria;

    @Column(nullable = false)
    private Boolean activo = true;


    public boolean tieneStockDisponible(int cantidad){
        return stock>= cantidad;
    }

    public boolean estaBajoStockMinimo(){
        return stock <= stockMinimo;
    }

}
