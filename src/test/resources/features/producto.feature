Feature: Gestion de Prodcutos en el Inveratio
  Como usuario del sistema de inventario
  Quiero gestionar productos
  Para mantener actualizado el catalogo

  Background:
    Given la aplicacion esta ejecutandose
    And existe una categoria "Electronica y Teconologia" con id 1

    Scenario: Registrar un producto exitosamente
      When registro un producto con lo siguientes datos:
      | nombre      | Laptop HP |
      | codigo      | PROD001   |
      | precio      | 2500.00   |
      | stock       | 10        |
      | categoriaId | 1         |
      Then el producto se crea exitosamente
      And el codigo de respuesta es 201
      And el producto contiene "Laptop HP"

    Scenario: Listar todos los productos
      Given existen los siguientes productos:
      | nombre      | codigo    | precio    | stock   | categoriaId   |
      | Laptop HP   | PROD001   | 2500.00   | 10      | 1             |
      | Mouse Logi  | PROD002   | 50.00     | 25      | 1             |
      When solicito la lista de productos
      Then el codigo de respuesta es 200
      And la lista contiene 2 productos

    Scenario: Buscar prodcuto por ID
      Given existe un producto con nombre "Laptop HP" y id 1
      When busco el producto con id 1
      Then el codigo de respuesta es 200
      And el producto contiene "Laptop HP"

    Scenario: Eliminar un producto
      Given existe un producto con id 1
      When elimino un producto con id 1
      Then el codigo de respuesta es 204