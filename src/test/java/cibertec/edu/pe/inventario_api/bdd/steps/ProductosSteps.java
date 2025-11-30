package cibertec.edu.pe.inventario_api.bdd.steps;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static  io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class ProductosSteps {

    private Response reponse;
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
                .statusCode(anyOf(is(200), is(200)));
    }

    // Scenario: Registrar un producto exitosamente

    @When()

}
