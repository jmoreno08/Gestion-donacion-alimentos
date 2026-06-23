# Sistema de Gestión de Donación de Alimentos

# Integrantes

1. JADE CUARAN LEURO
2. CARLOS ANDRÉS VARGAS MESA
3. KEITH DUNWEL VILLALOBOS
4. JONATHAN MORENO NÚÑEZ
5. JUAN QUINTERO ATOY

## Descripción

El Sistema de Gestión de Donación de Alimentos es un proyecto académico enfocado en el diseño e implementación de una base de datos relacional normalizada para administrar procesos de donación de alimentos. El sistema permite a fundaciones, organizaciones sociales y grupos comunitarios registrar donantes, donaciones, productos alimenticios, beneficiarios y entregas de manera organizada y trazable.

El proyecto demuestra la aplicación de modelado de bases de datos, técnicas de normalización, consultas SQL y la implementación de los patrones de diseño DAO (Data Access Object) y DTO (Data Transfer Object).

---

# Objetivo General

Diseñar e implementar una base de datos relacional normalizada que facilite la gestión y el seguimiento de donaciones de alimentos, garantizando una distribución eficiente de recursos hacia comunidades vulnerables.

---

# Problemática

Muchas organizaciones sociales reciben donaciones de alimentos provenientes de personas, empresas, supermercados y fundaciones. Sin embargo, estas donaciones suelen gestionarse mediante hojas de cálculo, registros físicos o sistemas dispersos.

Esta situación genera diversos problemas:

- Duplicidad de información.
- Pérdida de registros.
- Dificultad para rastrear donantes y beneficiarios.
- Falta de control sobre los productos donados.
- Limitadas capacidades de consulta y generación de reportes.
- Poca transparencia en la distribución de ayudas.
- Dificultad para identificar productos próximos a vencer.

Como consecuencia, las organizaciones tienen dificultades para administrar eficientemente los programas de ayuda alimentaria y garantizar una distribución equitativa de los recursos.

---

# Solución Propuesta

Desarrollar un sistema de información basado en una base de datos relacional que centralice toda la información relacionada con las donaciones de alimentos y los beneficiarios.

El sistema permitirá:

- Registrar donantes.
- Registrar donaciones.
- Gestionar productos alimenticios.
- Registrar beneficiarios.
- Gestionar entregas de alimentos.
- Mantener trazabilidad de las donaciones y entregas.
- Generar reportes y estadísticas.
- Mejorar la transparencia y el control de recursos.

---

# Distribución del Trabajo

## Jonathan Moreno Nuñez – Análisis de la Problemática y Descripción del Sistema

### Responsabilidades

- Presentar la problemática social.
- Definir los objetivos del proyecto.
- Explicar la solución propuesta.
- Describir el alcance del sistema.
- Presentar las entidades principales.

---

## Integrante 2 – Modelo Relacional y Normalización

### Responsabilidades

- Diseñar el modelo lógico-relacional.
- Definir llaves primarias y foráneas.
- Establecer las relaciones entre entidades.
- Definir cardinalidades.
- Explicar la normalización:
  - Primera Forma Normal (1FN)
  - Segunda Forma Normal (2FN)
  - Tercera Forma Normal (3FN)

---

## Integrante 3 – Implementación de la Base de Datos y Consultas SQL
## Carlos Andres Vargas Mesa
# Sistema de Gestión de Donación de Alimentos

Proyecto desarrollado en Java y MySQL.

## Funcionalidades

* Gestión de donantes
* Gestión de beneficiarios
* Gestión de productos
* Gestión de donaciones
* Gestión de entregas

## Tecnologías

* Java 17
* JDBC
* MySQL
* Maven
* Docker / Docker Compose
* Postman
* HTML, CSS y JavaScript

## Base de datos

La base de datos se encuentra en el archivo:

gestion_donacion_alimentos.sql

## Ejecucion con Docker

El proyecto puede ejecutarse en contenedores sin instalar Java, Maven, MySQL, XAMPP ni phpMyAdmin en el equipo.

Requisitos:

- Docker Desktop

Comandos principales:

```bash
docker compose up --build
```

Esto crea dos servicios:

- `db`: MySQL 8.4 con la base `gestion_donacion_alimentos`, inicializada desde `database/gestion_donacion_alimentos.sql`.
- `app`: backend HTTP Java que compila con Maven y queda escuchando en `http://localhost:18080`.

Endpoints disponibles:

```text
GET http://localhost:18080/health
```

CRUD disponible:

| Entidad | Listar | Consultar | Crear | Actualizar | Eliminar |
|---------|--------|-----------|-------|------------|----------|
| Donantes | `GET /api/donantes` | `GET /api/donantes/{id}` | `POST /api/donantes` | `PUT /api/donantes/{id}` | `DELETE /api/donantes/{id}` |
| Productos | `GET /api/productos` | `GET /api/productos/{id}` | `POST /api/productos` | `PUT /api/productos/{id}` | `DELETE /api/productos/{id}` |
| Beneficiarios | `GET /api/beneficiarios` | `GET /api/beneficiarios/{id}` | `POST /api/beneficiarios` | `PUT /api/beneficiarios/{id}` | `DELETE /api/beneficiarios/{id}` |
| Entregas | `GET /api/entregas` | `GET /api/entregas/{id}` | `POST /api/entregas` | `PUT /api/entregas/{id}` | `DELETE /api/entregas/{id}` |

Ejemplo para crear un donante:

```bash
curl -X POST http://localhost:18080/api/donantes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Donante Nuevo","tipoDonante":"Persona","telefono":"3000000000","correo":"donante@example.com","direccion":"Cali"}'
```

Los campos de fecha se envian en formato `YYYY-MM-DD`, por ejemplo `fechaVencimiento` o `fechaEntrega`.

Ejemplos de cuerpos JSON:

Crear producto:

```json
{
  "nombreProducto": "Arroz Postman",
  "categoria": "Granos",
  "unidadMedida": "Kg",
  "fechaVencimiento": "2027-12-31"
}
```

Crear beneficiario:

```json
{
  "nombre": "Beneficiario Postman",
  "documento": "123456789",
  "telefono": "3222222222",
  "direccion": "Palmira",
  "numeroIntegrantes": 4,
  "condicion": "Bajos recursos"
}
```

Crear entrega:

```json
{
  "idBeneficiario": 1,
  "fechaEntrega": "2026-07-01",
  "responsable": "Responsable Postman",
  "observacion": "Entrega creada desde Postman"
}
```

## Pruebas con Postman

El repositorio incluye una coleccion lista para importar en Postman:

```text
Gestion_Donacion_Alimentos_Postman_Collection.json
```

Pasos:

1. Abrir Postman.
2. Seleccionar `Import`.
3. Importar el archivo `Gestion_Donacion_Alimentos_Postman_Collection.json`.
4. Verificar que la variable `baseUrl` tenga el valor `http://localhost:18080`.
5. Ejecutar primero `Health` y luego las solicitudes CRUD.

La coleccion incluye solicitudes para listar, consultar, crear, actualizar y eliminar registros de Donantes, Productos, Beneficiarios y Entregas.

## Frontend estatico

El proyecto incluye una interfaz inicial en:

```text
frontend/index.html
```

La estructura recomendada queda separada del backend Java:

```text
frontend/
├── index.html
├── css/
│   └── estilos.css
└── js/
    └── app.js
```

Para usarla:

1. Levantar el backend con `docker compose up -d --build`.
2. Abrir `frontend/index.html` en el navegador.

La interfaz consume el CRUD completo del backend en `http://localhost:18080`: lista registros, crea desde formularios, edita con la accion `Editar` y elimina con la accion `Eliminar` para Donantes, Beneficiarios, Productos y Entregas. Si el backend corre en otro puerto, ajustar `API_BASE_URL` en `frontend/js/app.js`.

Para ejecutar la demo de consola manualmente:

```bash
docker compose run --rm app demo
```

Para detener y conservar los datos:

```bash
docker compose down
```

Para reiniciar la base de datos desde cero:

```bash
docker compose down -v
docker compose up --build
```

La aplicacion usa variables de entorno para conectarse a MySQL:

```text
DB_HOST=db
DB_PORT=3306
DB_NAME=gestion_donacion_alimentos
DB_USER=root
DB_PASSWORD=root
```

## Arquitectura actual

La aplicacion conserva las capas DAO y DTO existentes y agrega una capa HTTP simple:

- `src/config/ConexionBD.java`: centraliza la conexion JDBC usando variables de entorno.
- `src/dao/`: contiene las operaciones SQL por entidad.
- `src/dto/`: contiene los objetos de transferencia.
- `src/server/BackendServer.java`: expone el backend HTTP y convierte JSON hacia/desde los DTO.
- `Main.java`: arranca el servidor con el argumento `server` o ejecuta la demo de consola sin argumentos.

El contenedor `app` ejecuta por defecto:

```bash
java -jar app.jar server
```

### Responsabilidades

- Crear la base de datos.
- Crear las tablas.
- Insertar datos de prueba.
- Realizar consultas SQL.

### Operaciones SQL requeridas

- SELECT
- INNER JOIN
- UNION
- UPDATE
- WHERE
- ORDER BY
- GROUP BY
- COUNT
- SUM

---

## Keith Dunwell Villalobos – Implementación de DAO y DTO

### Responsabilidades

- Crear las clases DTO.
- Crear las clases DAO.
- Demostrar:
  - Inserciones
  - Actualizaciones
  - Consultas
- Explicar las ventajas de DAO y DTO.

---

# Estado Actual del Modelo Implementado

El SQL ejecutable del proyecto se encuentra en `database/gestion_donacion_alimentos.sql`. Ese script crea la base `gestion_donacion_alimentos` con las tablas usadas por los DAO y por el backend HTTP actual.

El documento `docs/modelo_relacional_donacion_alimentos.md` describe una propuesta normalizada más amplia con `detalle_donacion`, `detalle_entrega`, categorías e inventario por lote. Esa propuesta queda documentada como evolución del modelo, pero no está implementada todavía en el SQL ni en el backend actual.

# Definición de Entidades Implementadas

## Donantes

| Campo | Tipo | Descripción |
|---------|---------|---------|
| id_donante | INT PK | Identificador único del donante |
| nombre | VARCHAR(100) | Nombre del donante |
| tipo_donante | VARCHAR(50) | Persona, Empresa o Fundación |
| telefono | VARCHAR(20) | Número de contacto |
| correo | VARCHAR(100) | Correo electrónico |
| direccion | VARCHAR(150) | Dirección física |

---

## Donaciones

| Campo | Tipo | Descripción |
|---------|---------|---------|
| id_donacion | INT PK | Identificador único de la donación |
| id_donante | INT FK | Donante relacionado |
| fecha_donacion | DATE | Fecha de la donación |
| estado | VARCHAR(50) | Recibida, Revisada o Distribuida |
| observacion | VARCHAR(200) | Observaciones adicionales |

---

## Productos

| Campo | Tipo | Descripción |
|---------|---------|---------|
| id_producto | INT PK | Identificador único del producto |
| nombre_producto | VARCHAR(100) | Nombre del alimento |
| categoria | VARCHAR(50) | Granos, Bebidas, Lácteos, etc. |
| unidad_medida | VARCHAR(20) | Kg, Litro, Unidad |
| fecha_vencimiento | DATE | Fecha de vencimiento |

## Beneficiarios

| Campo | Tipo | Descripción |
|---------|---------|---------|
| id_beneficiario | INT PK | Identificador único |
| nombre | VARCHAR(100) | Nombre del beneficiario |
| documento | VARCHAR(30) | Documento de identidad |
| telefono | VARCHAR(20) | Número de contacto |
| direccion | VARCHAR(150) | Dirección |
| numero_integrantes | INT | Número de integrantes del hogar |
| condicion | VARCHAR(100) | Condición social |

---

## Entregas

| Campo | Tipo | Descripción |
|---------|---------|---------|
| id_entrega | INT PK | Identificador único |
| id_beneficiario | INT FK | Beneficiario relacionado |
| fecha_entrega | DATE | Fecha de entrega |
| responsable | VARCHAR(100) | Responsable de la entrega |
| observacion | VARCHAR(200) | Observaciones |

# Relaciones Principales

- Un Donante puede realizar muchas Donaciones.
- Un Beneficiario puede recibir varias Entregas.
- La tabla `productos` funciona como catálogo simple de alimentos con categoría, unidad y fecha de vencimiento.
- El modelo implementado no registra aún detalle por lote ni detalle de productos entregados.
- La documentación de modelo normalizado propone agregar `detalle_donacion` y `detalle_entrega` para mejorar trazabilidad de inventario.

---

# Estructura del Repositorio

```text
sistema-donacion-alimentos/
│
├── .dockerignore
├── .gitignore
├── README.md
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── Gestion_Donacion_Alimentos_Postman_Collection.json
│
├── frontend/
│   ├── index.html
│   ├── css/
│   │   └── estilos.css
│   └── js/
│       └── app.js
│
├── docs/
│   ├── problematica.md
│   ├── modelo_relacional_donacion_alimentos.md
│   ├── Entrega 2 - Punto 4.md
│   └── evidencias.md
│
├── database/
│   └── gestion_donacion_alimentos.sql
│
├── src/
│   ├── dto/
│   │   ├── DonanteDTO.java
│   │   ├── DonacionDTO.java
│   │   ├── ProductoDTO.java
│   │   ├── BeneficiarioDTO.java
│   │   └── EntregaDTO.java
│   │
│   ├── dao/
│   │   ├── DonanteDAO.java
│   │   ├── DonacionDAO.java
│   │   ├── ProductoDAO.java
│   │   ├── BeneficiarioDAO.java
│   │   └── EntregaDAO.java
│   │
│   ├── config/
│   │   └── ConexionBD.java
│   │
│   ├── server/
│   │   └── BackendServer.java
│   │
│   └── Main.java
```

---

# Entregables Esperados

- Documento de problemática.
- Modelo lógico-relacional.
- Explicación de normalización.
- Scripts SQL.
- Mínimo 5 registros insertados.
- Mínimo 10 consultas SQL.
- Implementación de DAO.
- Implementación de DTO.
- Evidencias mediante capturas de pantalla o video.
- Repositorio GitHub organizado.

