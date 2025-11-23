package cibertec.edu.pe.inventario_api.service;

import cibertec.edu.pe.inventario_api.model.Categoria;
import cibertec.edu.pe.inventario_api.respository.CategoriaRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRespository categoriaRespository;

    public List<Categoria> listarTodos(){
        return categoriaRespository.findAll();
    }

    public Optional<Categoria> buscarPorId(Long id){
        return categoriaRespository.findById(id);
    }

    public Categoria guardar(Categoria categoria){
        if(categoriaRespository.existsByNombre(categoria.getNombre())){
            throw new IllegalArgumentException(
                    "Ya existe una categoria con el nomhbe "+ categoria.getNombre()
            );
        }
        return categoriaRespository.save(categoria);
    }

    public Categoria actualizar (Long id, Categoria categoriaActualizada){
        return categoriaRespository.findById(id)
                .map(categoria -> {
                    // Validamos si nuevo nombre ya existe
                    if(!categoria.getNombre().equals(categoriaActualizada.getNombre())
                            && categoriaRespository.existsByNombre(categoriaActualizada.getNombre())){
                        throw  new IllegalArgumentException(
                                "Ya existe categoria con con el nomhre "+ categoriaActualizada.getNombre()
                        );
                    }
                    categoria.setNombre(categoriaActualizada.getNombre());
                    categoria.setDescripcion(categoriaActualizada.getDescripcion());
                    categoria.setActiva(categoriaActualizada.getActiva());

                    return categoriaRespository.save(categoria);
                })
                .orElseThrow(() -> new IllegalArgumentException("Categoria no encontrada"));

    }

    public void eliminar(Long id){
        if(!categoriaRespository.existsById(id)){
            throw new IllegalArgumentException("Categoria no encontrada con el ID: "+ id);
        }
        categoriaRespository.deleteById(id);
    }
}
