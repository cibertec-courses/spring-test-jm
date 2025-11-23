package cibertec.edu.pe.inventario_api.controller;

import cibertec.edu.pe.inventario_api.model.Producto;
import cibertec.edu.pe.inventario_api.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public List<Producto> listar(){
        return  productoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Producto buscar(@PathVariable Long id){
        return productoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear (@RequestBody Producto producto){
        return productoService.crear(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto producto){
        return  productoService.actualizar(id,producto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void elminar(@PathVariable Long id){
        productoService.eliminar(id);
    }


}
