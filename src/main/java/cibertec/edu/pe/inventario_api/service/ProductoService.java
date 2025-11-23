package cibertec.edu.pe.inventario_api.service;

import cibertec.edu.pe.inventario_api.model.Producto;
import cibertec.edu.pe.inventario_api.respository.CategoriaRespository;
import cibertec.edu.pe.inventario_api.respository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;

    public List<Producto> listarTodos(){
        return productoRepository.findAll();
    }

    public Producto buscarPorId(Long id){
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Producto crear(Producto producto){
        if(productoRepository.existsByCodigo(producto.getCodigo())){
            throw  new RuntimeException("Codigo ya existe");
        }
        if(producto.getStockMinimo()> producto.getStock()){
            throw  new RuntimeException("Stock minimo no puede ser mayor al stock ");
        }
        return  productoRepository.save(producto);
    }


    public Producto actualizar (Long id, Producto producto){
        Producto existe = buscarPorId(id);
        existe.setNombre(producto.getNombre());
        existe.setPrecio(producto.getPrecio());
        existe.setStock(producto.getStock());
        existe.setStockMinimo(producto.getStockMinimo());
        return  productoRepository.save(existe);
    }

    public void eliminar(Long id){
        Producto producto = buscarPorId(id);
        producto.setActivo(false);
        productoRepository.save(producto);
    }

}
