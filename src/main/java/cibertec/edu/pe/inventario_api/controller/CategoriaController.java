package cibertec.edu.pe.inventario_api.controller;

import cibertec.edu.pe.inventario_api.model.Categoria;
import cibertec.edu.pe.inventario_api.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodas(){
        return  ResponseEntity.ok(categoriaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id){
        return categoriaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Categoria> crear (@Valid @RequestBody Categoria categoria){
        try{
            Categoria nuevaCategoria = categoriaService.guardar(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
        }catch (IllegalArgumentException e){
            return  ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Categoria categoria
    ){
        try{
            Categoria categoriaActualizada = categoriaService.actualizar(id,categoria);
            return ResponseEntity.ok(categoriaActualizada);
        }catch (IllegalArgumentException e){
            return  ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        try{
            categoriaService.eliminar(id);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
            return  ResponseEntity.badRequest().build();
        }
    }


}
