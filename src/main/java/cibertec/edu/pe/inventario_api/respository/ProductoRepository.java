package cibertec.edu.pe.inventario_api.respository;

import cibertec.edu.pe.inventario_api.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByCodigo(String codigo);
    List<Producto> findByActivoTrue();
    List<Producto> findByCategoriaId(Long categoriaId);
    @Query("SELECT p from Producto p WHERE p.stock<= p.stockMinimo and p.activo=true")
    List<Producto> findProductoBajoStockMinimo();
    boolean existsByCodigo (String codigo);
    boolean existsByCodigoAndIdNot(String codigo, Long id);
}
