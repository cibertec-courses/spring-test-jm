package cibertec.edu.pe.inventario_api.respository;

import cibertec.edu.pe.inventario_api.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRespository extends JpaRepository<Categoria, Long> {

    boolean existsByNombre(String nombre);

    Optional<Categoria> findByNombre(String nombre);
}
