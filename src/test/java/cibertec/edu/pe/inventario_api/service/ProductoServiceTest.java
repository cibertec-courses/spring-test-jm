package cibertec.edu.pe.inventario_api.service;

import cibertec.edu.pe.inventario_api.model.Categoria;
import cibertec.edu.pe.inventario_api.model.Producto;
import cibertec.edu.pe.inventario_api.respository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private  ProductoService productoService;

    private Producto producto;
    private Categoria categoria;

    @BeforeEach
    void setUp(){
        categoria = new Categoria();

        categoria.setId(1L);
        categoria.setNombre("Electronica");
        categoria.setActiva(true);

        producto = new Producto();
        producto.setId(1L);
        producto.setCodigo("PROD001");
        producto.setNombre("Laptop HP");
        producto.setPrecio(new BigDecimal("2500.00"));
        producto.setStock(10);
        producto.setStockMinimo(3);
        producto.setCategoria(categoria);
        producto.setActivo(true);
    }

    @Test
    @DisplayName("Test 1: Listar producto por ID")
    void testBuscarPorIdExitoso(){
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.buscarPorId(1L);

        assertNotNull("PROD001", resultado.getCodigo());
        assertEquals("Laptop HP", resultado.getNombre());
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test 2: Crear Producto")
    void testCrearProdcutoExistoso(){
        when(productoRepository.existsByCodigo("PROD001")).thenReturn(false);
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = productoService.crear(producto);

        assertNotNull(resultado);
        assertEquals("PROD001", resultado.getCodigo());
        assertEquals(true, resultado.getActivo());
        verify(productoRepository, times(1)).existsByCodigo("PROD001");
        verify(productoRepository, times(1)).save(producto);


    }
}
