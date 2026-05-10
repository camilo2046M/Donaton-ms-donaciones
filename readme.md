#  Microservicio de Gestión de Donaciones

Este microservicio forma parte del ecosistema **Donatón**. Se encarga de registrar, clasificar y gestionar el ciclo de vida de las donaciones (monetarias y en especie), permitiendo la distinción entre donantes individuales y empresariales.

## Instalación y Ejecución
Clonar el repositorio.

Navegar a la carpeta del proyecto.

Ejecutar el comando Maven:
    mvn spring-boot:run;


##  Tecnologías Utilizadas

* **Java 17/21** (Compatible con versiones experimentales hasta Java 26).
* **Spring Boot 3.3.x**
* **Spring Data JPA**: Gestión de persistencia.
* **PostgreSQL**: Base de datos relacional.
* **Lombok**: Reducción de código boilerplate.
* **JUnit 5 & Mockito**: Pruebas unitarias.

##  Arquitectura de Datos (Herencia)

El microservicio utiliza una estrategia de **Single Table Inheritance** para gestionar diferentes tipos de donaciones en una única tabla (`donaciones`), diferenciadas por la columna `tipo_donacion`.

- **Donacion (Abstracta)**: Contiene campos base como `id`, `monto`, `nombreObjeto` y `estado`.
- **DonacionIndividual**: Implementación para donantes persona natural.
- **DonacionEmpresarial**: Incluye campos específicos como `rutEmpresa` y `certificadoImpuestos`.

##  Configuración del Entorno

### Requisitos Previos
1. Tener instalado **PostgreSQL**.
2. Crear la base de datos:
   ```sql
   CREATE DATABASE bd_donaciones;

## Archivo de Configuración (application.yml)

server:
port: 8081

spring:

datasource:

url: jdbc:postgresql://localhost:5432/bd_donaciones

username: tu_usuario
password: tu_password(contraseña de su postgresql)

jpa:

hibernate:

ddl-auto: update

show-sql: true

## Endpoints Principales (API)
1. Registrar Donación
   POST /api/donaciones/crear
   Permite registrar una nueva donación enviando los parámetros requeridos (tipo, monto, nombre, objeto).

2. Buscador para Logística
   GET /api/donaciones/buscar/{palabra}
   Nota: Este endpoint es consumido por el microservicio de Logística a través de Feign Client para planificar envíos automáticos.

3. Actualizar Estado
   PATCH /api/donaciones/{id}/completar
   Cambia el estado de la donación a COMPLETADA una vez que Logística confirma la entrega.

## Pruebas Unitarias
Para ejecutar los tests de cobertura y asegurar la calidad del código:

Bash
mvn test