package cibertec.edu.pe.inventario_api.bdd.steps;


import cibertec.edu.pe.inventario_api.bdd.config.CucumberSpringConfiguration;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.Map;

import static  io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class ProductosSteps extends CucumberSpringConfiguration {

    private Response response;
    private String baseUrl;

    @Given("la aplicacion esta ejecutandose")
    public void laAplicacionEstaEjecutandose(){
        baseUrl = "http://localhost:" + port + "/api";
        RestAssured.baseURI = baseUrl;
    }

    @And("existe una categoria {string} con id {int}")
    public void existeUnaCategoriaConId(String nombre, Integer id){
        String categoriaJson = String.format("""
                {
                    "nombre": "%s",
                    "descripcion": "Categoria de prueba",
                    "activa": true
                }
                """, nombre);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(categoriaJson)
                .when()
                .post(baseUrl+"/categorias")
                .then()
                .statusCode(anyOf(is(201), is(200)));
    }

    // Scenario: Registrar un producto exitosamente

    @When("registro un producto con lo siguientes datos:")
    public void registroProductoConLosSiguientesDatos(DataTable dataTable){
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        String productosJson = String.format("""
                {
                    "nombre": "%s",
                    "codigo": "%s",
                    "precio": %s,
                    "stock": %s,
                    "stockMinimo": 1,
                    "categoria": {
                        "id": %s
                    }
                }
                """,
                data.get("nombre"),
                data.get("codigo"),
                data.get("precio"),
                data.get("stock"),
                data.get("categoriaId")
        );

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productosJson)
                .when()
                .post(baseUrl+ "/productos");
    }

    @Then("el producto se crea exitosamente")
    public void elProductoSeCreaExitosamente(){
        assertNotNull(response);
        assertTrue(response.getStatusCode() >= 200 && response.getStatusCode() < 300);
    }

    @And("el codigo de respuesta es {int}")
    public void elCodigoDeRespuestaEs(Integer statusCode){
        assertEquals(statusCode, response.getStatusCode());
    }

    @And("el producto contiene {string}")
    public void elProductoContiene(String nombre){
        response.then()
                .body("nombre", equalTo(nombre));
    }

    // Scenario: Listar todos los productos

    @Given("existen los siguientes productos:")
    public void existenLosSiguientesProductos(DataTable  dataTable){
        dataTable.asMaps(String.class, String.class).forEach(data ->{
            String productosJson = String.format("""
                {
                    "nombre": "%s",
                    "codigo": "%s",
                    "precio": %s,
                    "stock": %s,
                    "stockMinimo": 1,
                    "categoria": {
                        "id": %s
                    }
                }
                """,
                    data.get("nombre"),
                    data.get("codigo"),
                    data.get("precio"),
                    data.get("stock"),
                    data.get("categoriaId")
            );

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productosJson)
                    .when()
                    .post(baseUrl+ "/productos");
        });
    }

    @When("solicito la lista de productos")
    public void solicitoLaListaDeProductos(){
        response = given()
                .when()
                .get(baseUrl+"/productos");
    }

    @And("la lista contiene {int} productos")
    public void laListaContieneProductos(Integer cantidad){
        response.then()
                .body("size()", greaterThanOrEqualTo(cantidad));
    }

    // Scenario: Buscar prodcuto por ID
    @Given("existe un producto con nombre {string} y id {int}")
    public void existeUnProductoConNombreYId(String nombre, Integer id ){
        String productosJson = String.format("""
                {
                    "nombre": "%s",
                    "codigo": "PROD%03d",
                    "precio": 100.00,
                    "stock": 10,
                    "stockMinimo": 1,
                    "categoria": {
                        "id": 1
                    }
                }
                """, nombre, id);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productosJson)
                .when()
                .post(baseUrl+"/productos");
    }

    @When("busco el producto con id {int}")
    public void buscoElProductoConId(Integer id){
        response = given()
                .when()
                .get(baseUrl + "/productos/"+id);
    }

    //  Scenario: Eliminar un producto
    @Given("existe un producto con id {int}")
    public void existeUnProductoConId(Integer id){
        String productosJson = String.format("""
                {
                    "nombre": "Producto Test",
                    "codigo": "TEST%03d",
                    "precio": 50.00,
                    "stock": 5,
                    "stockMinimo": 1,
                    "categoria": {
                        "id": 1
                    }
                }
                """,  id);
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productosJson)
                .when()
                .post(baseUrl + "/productos");
    }


    @When("elimino un producto con id {int}")
    public void eliminoElProductoConId(Integer id){
        response = given()
                .when()
                .delete(baseUrl + "/productos/"+id);
    }



}
