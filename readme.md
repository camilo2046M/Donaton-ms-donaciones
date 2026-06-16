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

##  Cobertura de Código con JaCoCo

Este microservicio utiliza **JaCoCo (Java Code Coverage)** para medir la cobertura de las pruebas unitarias y de integración, asegurando que se cumpla con el estándar mínimo requerido del 60%.

### 🛠 Paso a Paso para Inicializar y Ver el Reporte

Sigue estos pasos para ejecutar la suite de pruebas y generar el reporte analítico y gráfico:

#### 1. Agregar el Plugin al Proyecto (Ya configurado)
El archivo `pom.xml` incluye el plugin oficial de JaCoCo en la sección de construcción (`<build>` -> `<plugins>`):
```xml`

    <plugin>
       <groupId>org.jacoco</groupId>
       <artifactId>jacoco-maven-plugin</artifactId>
       <version>0.8.11</version>
       <executions>
           <execution>
               <goals>
                   <goal>prepare-agent</goal>
               </goals>
           </execution>
           <execution>
               <id>report</id>
               <phase>test</phase>
               <goals>
                   <goal>report</goal>
               </goals>
           </execution>
       </executions>
      </plugin> 
   
## 2. Ejecutar los Tests y Generar las Métricas

En Windows (PowerShell):
 
 ```.\mvnw clean test ```

En Linux / macOS o Git Bash:

   ```./mvnw clean test```

## Alternativa Gráfica (IntelliJ IDEA):
Abre la pestaña lateral Maven ➡️ Despliega Lifecycle ➡️ Presiona Ctrl y haz doble clic en clean y luego en test.

Al finalizar con éxito, verás el mensaje BUILD SUCCESS en la consola.

## 3. Visualizar el Reporte Gráfico Interactivos
   Una vez finalizados los tests, JaCoCo compila un sitio web local con tablas y gráficos de barras de cobertura.

Navega en tu explorador de archivos hasta la siguiente ruta dentro del proyecto:
target/site/jacoco/

Busca y abre el archivo index.html haciendo doble clic (se abrirá en tu navegador web como Chrome o Edge).

Ahí podrás analizar el desglose detallado por paquetes (controller, service, repository), líneas ejecutadas y caminos lógicos cubiertos.