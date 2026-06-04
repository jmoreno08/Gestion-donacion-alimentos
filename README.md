# Sistema de Gestión de Donación de Alimentos

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

## Integrante 1 – Análisis de la Problemática y Descripción del Sistema

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

## Integrante 4 – Implementación de DAO y DTO

### Responsabilidades

- Crear las clases DTO.
- Crear las clases DAO.
- Demostrar:
  - Inserciones
  - Actualizaciones
  - Consultas
- Explicar las ventajas de DAO y DTO.

---

# Definición de Entidades

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

---

## Detalle_Donacion

| Campo | Tipo | Descripción |
|---------|---------|---------|
| id_detalle_donacion | INT PK | Identificador único |
| id_donacion | INT FK | Donación relacionada |
| id_producto | INT FK | Producto relacionado |
| cantidad | DECIMAL(10,2) | Cantidad donada |

---

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

---

## Detalle_Entrega

| Campo | Tipo | Descripción |
|---------|---------|---------|
| id_detalle_entrega | INT PK | Identificador único |
| id_entrega | INT FK | Entrega relacionada |
| id_producto | INT FK | Producto relacionado |
| cantidad | DECIMAL(10,2) | Cantidad entregada |

---

# Relaciones Principales

- Un Donante puede realizar muchas Donaciones.
- Una Donación puede contener varios Productos.
- Un Producto puede estar presente en varias Donaciones.
- Un Beneficiario puede recibir varias Entregas.
- Una Entrega puede contener varios Productos.
- Un Producto puede aparecer en múltiples Entregas.

---

# Estructura del Repositorio

```text
sistema-donacion-alimentos/
│
├── README.md
│
├── docs/
│   ├── problematica.md
│   ├── modelo-relacional.md
│   ├── normalizacion.md
│   ├── evidencias.md
│   └── guion-video.md
│
├── database/
│   ├── 01_crear_base_datos.sql
│   ├── 02_crear_tablas.sql
│   ├── 03_insertar_datos.sql
│   └── 04_consultas.sql
│
├── diagrams/
│   ├── modelo-logico.png
│   └── modelo-relacional.png
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
│   └── Main.java
│
└── evidencias/
    ├── modelo.png
    ├── tablas.png
    ├── inserciones.png
    ├── consultas.png
    └── dao-dto.png
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

