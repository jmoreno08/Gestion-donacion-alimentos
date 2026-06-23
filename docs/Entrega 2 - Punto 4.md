# Sistema de Gestión de Donación de Alimentos
## Implementación de Patrones DAO y DTO

**Autor:** Keith Dunwell Villalobos

---

## 1. Introducción

El presente documento describe la implementación de los patrones de diseño **DAO (Data Access Object)** y **DTO (Data Transfer Object)** en el Sistema de Gestión de Donación de Alimentos. Estos patrones son fundamentales en el desarrollo de aplicaciones empresariales con acceso a bases de datos relacionales.

El proyecto utiliza **Java** como lenguaje de programación y **MySQL** como motor de base de datos. La implementación cubre las cinco entidades principales del sistema: Donantes, Donaciones, Productos, Beneficiarios y Entregas.

---

## 2. Marco Teórico

### 2.1 Patrón DTO (Data Transfer Object)

Un **DTO** es un objeto simple cuyo único propósito es transportar datos entre capas de una aplicación. Sus características principales son:

- No contiene lógica de negocio; solo atributos privados con getters y setters.
- Reduce el número de llamadas entre capas al agrupar los datos en un solo objeto.
- Desacopla la representación interna de los datos (tablas de BD) de la capa de presentación.
- Puede implementar `Serializable` para transmitirse por red si se requiere.

### 2.2 Patrón DAO (Data Access Object)

Un **DAO** es una clase que centraliza todo el acceso a la base de datos para una entidad específica. Sus características son:

- Encapsula las operaciones CRUD: INSERT, SELECT, UPDATE y DELETE.
- Usa JDBC con `PreparedStatement` para ejecutar SQL parametrizado, previniendo inyección SQL.
- Facilita el cambio de motor de base de datos: solo se modifica el DAO, no la lógica de negocio.
- Implementa el principio de responsabilidad única (SRP).

### 2.3 Relación DAO ↔ DTO

Los dos patrones trabajan juntos: el **DAO** recibe o retorna objetos **DTO**.  
Por ejemplo:
- `DonanteDAO.insertar(DonanteDTO dto)` → recibe un DTO, construye el SQL y lo ejecuta.
- `DonanteDAO.consultarPorId(int id)` → ejecuta el SELECT y devuelve un `DonanteDTO` con los resultados.

---

## 3. Arquitectura del Proyecto

```
src/
  Main.java                ← Clase principal / demo de operaciones o arranque del servidor
  config/
    ConexionBD.java        ← Conexión JDBC a MySQL usando variables de entorno
  server/
    BackendServer.java     ← Backend HTTP para exponer endpoints CRUD en JSON
  dto/
    DonanteDTO.java        ← DTO de la entidad Donante
    DonacionDTO.java       ← DTO de la entidad Donacion
    ProductoDTO.java       ← DTO de la entidad Producto
    BeneficiarioDTO.java   ← DTO de la entidad Beneficiario
    EntregaDTO.java        ← DTO de la entidad Entrega
  dao/
    DonanteDAO.java        ← CRUD para Donantes
    DonacionDAO.java       ← CRUD para Donaciones
    ProductoDAO.java       ← CRUD para Productos
    BeneficiarioDAO.java   ← CRUD para Beneficiarios
    EntregaDAO.java        ← CRUD para Entregas
```

---

## 4. Configuración de Conexión — `ConexionBD.java`

La clase `ConexionBD` centraliza la conexión JDBC a MySQL y lee sus valores desde variables de entorno. Esto permite ejecutar el proyecto tanto localmente como dentro de Docker Compose sin modificar el código fuente.

```java
public static Connection getConexion() throws SQLException {
    if (instancia == null || instancia.isClosed()) {
        Class.forName("com.mysql.cj.jdbc.Driver");
        instancia = DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
    return instancia;
}
```

Variables usadas por la aplicación:

```text
DB_HOST=db
DB_PORT=3306
DB_NAME=gestion_donacion_alimentos
DB_USER=root
DB_PASSWORD=root
```

En Docker Compose, el servicio `app` usa `DB_HOST=db` para conectarse al servicio MySQL llamado `db`.

---

## 5. Clases DTO

### 5.1 DonanteDTO

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `idDonante` | `int` | Identificador único (PK) |
| `nombre` | `String` | Nombre del donante |
| `tipoDonante` | `String` | Persona, Empresa o Fundación |
| `telefono` | `String` | Número de contacto |
| `correo` | `String` | Correo electrónico |
| `direccion` | `String` | Dirección física |

```java
public class DonanteDTO {
    private int    idDonante;
    private String nombre;
    private String tipoDonante;
    private String telefono;
    private String correo;
    private String direccion;

    // Constructor vacío
    public DonanteDTO() {}

    // Constructor completo
    public DonanteDTO(int idDonante, String nombre, String tipoDonante,
                      String telefono, String correo, String direccion) { ... }

    // Getters y Setters para cada campo
}
```

### 5.2 DonacionDTO

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `idDonacion` | `int` | Identificador único (PK) |
| `idDonante` | `int` | FK → Donantes |
| `fechaDonacion` | `LocalDate` | Fecha de la donación |
| `estado` | `String` | Recibida, Revisada o Distribuida |
| `observacion` | `String` | Observaciones adicionales |

### 5.3 ProductoDTO

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `idProducto` | `int` | Identificador único (PK) |
| `nombreProducto` | `String` | Nombre del alimento |
| `categoria` | `String` | Granos, Bebidas, Lácteos, etc. |
| `unidadMedida` | `String` | Kg, Litro, Unidad |
| `fechaVencimiento` | `LocalDate` | Fecha de vencimiento |

### 5.4 BeneficiarioDTO

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `idBeneficiario` | `int` | Identificador único (PK) |
| `nombre` | `String` | Nombre del beneficiario |
| `documento` | `String` | Documento de identidad |
| `telefono` | `String` | Número de contacto |
| `direccion` | `String` | Dirección |
| `numeroIntegrantes` | `int` | Integrantes del hogar |
| `condicion` | `String` | Condición social |

### 5.5 EntregaDTO

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `idEntrega` | `int` | Identificador único (PK) |
| `idBeneficiario` | `int` | FK → Beneficiarios |
| `fechaEntrega` | `LocalDate` | Fecha de la entrega |
| `responsable` | `String` | Responsable de la entrega |
| `observacion` | `String` | Observaciones |

---

## 6. Clases DAO — Implementación CRUD

### 6.1 DonanteDAO

#### `insertar(DonanteDTO dto)`
Recibe un `DonanteDTO` y ejecuta un `INSERT` con `PreparedStatement`. Los `?` se asignan con `setString`/`setInt`, previniendo inyección SQL.

```java
String sql = "INSERT INTO donantes (nombre, tipo_donante, telefono, correo, direccion) "
           + "VALUES (?, ?, ?, ?, ?)";
ps.setString(1, dto.getNombre());
ps.setString(2, dto.getTipoDonante());
ps.setString(3, dto.getTelefono());
ps.setString(4, dto.getCorreo());
ps.setString(5, dto.getDireccion());
int filas = ps.executeUpdate();
```

#### `consultarPorId(int id)`
Ejecuta un `SELECT` con `WHERE id_donante = ?` y mapea el `ResultSet` a un `DonanteDTO`.

```java
ResultSet rs = ps.executeQuery();
if (rs.next()) {
    return new DonanteDTO(
        rs.getInt("id_donante"),
        rs.getString("nombre"),
        rs.getString("tipo_donante"),
        rs.getString("telefono"),
        rs.getString("correo"),
        rs.getString("direccion")
    );
}
```

#### `consultarTodos()`
Ejecuta un `SELECT * FROM donantes ORDER BY nombre` y retorna una `List<DonanteDTO>`.

#### `actualizar(DonanteDTO dto)`
Ejecuta un `UPDATE SET` con todos los campos del DTO. El `WHERE` usa el `idDonante` para identificar el registro a modificar.

```java
String sql = "UPDATE donantes SET nombre=?, tipo_donante=?, telefono=?, "
           + "correo=?, direccion=? WHERE id_donante=?";
```

#### `eliminar(int id)`
Ejecuta `DELETE FROM Donantes WHERE id_donante = ?`. Retorna `true` si se eliminó al menos una fila.

---

### 6.2 DonacionDAO

Además del CRUD básico, incluye `consultarPorDonante(int idDonante)` que retorna todas las donaciones de un donante ordenadas por fecha descendente:

```java
String sql = "SELECT * FROM Donaciones WHERE id_donante = ? ORDER BY fecha_donacion DESC";
```

---

### 6.3 ProductoDAO

Incluye `consultarPorCategoria(String categoria)` para filtrar productos por categoría (Granos, Lácteos, Bebidas, etc.), útil para reportes de inventario.

```java
String sql = "SELECT * FROM Productos WHERE categoria = ? ORDER BY nombre_producto";
```

---

### 6.4 BeneficiarioDAO

CRUD completo. `consultarTodos()` retorna la lista ordenada alfabéticamente por nombre.

---

### 6.5 EntregaDAO

Incluye `consultarPorBeneficiario(int idBeneficiario)` para ver el historial de entregas de una familia beneficiaria:

```java
String sql = "SELECT * FROM entregas WHERE id_beneficiario = ? ORDER BY fecha_entrega DESC";
```

---

## 7. Demostración y Backend HTTP — `Main.java`

La clase `Main` puede ejecutarse de dos formas:

- Sin argumentos: ejecuta la demo de consola con operaciones reales contra la base de datos.
- Con el argumento `server`: inicia `BackendServer` y expone endpoints HTTP para el CRUD.

En Docker, el contenedor `app` ejecuta por defecto `java -jar app.jar server`.

### 7.1 Inserción de un Donante

```java
DonanteDAO dao = new DonanteDAO();

DonanteDTO d1 = new DonanteDTO(0, "Carlos Pérez", "Persona",
                               "3001234567", "carlos@email.com", "Calle 10 #5-20");
dao.insertar(d1);
```

### 7.2 Consulta de todos los Donantes

```java
List<DonanteDTO> todos = dao.consultarTodos();
todos.forEach(d -> System.out.println(d));
```

### 7.3 Actualización de un Donante

```java
DonanteDTO encontrado = dao.consultarPorId(1);
encontrado.setTelefono("3009999999");
boolean ok = dao.actualizar(encontrado);
System.out.println("Actualización: " + (ok ? "EXITOSA" : "FALLIDA"));
```

### 7.4 Salida esperada en consola

```
Conexión establecida correctamente.
Donante insertado. Filas afectadas: 1
Donante insertado. Filas afectadas: 1
Total donantes registrados: 2
  -> DonanteDTO{id=1, nombre='Carlos Pérez', tipo='Persona', tel='3001234567', ...}
  -> DonanteDTO{id=2, nombre='Supermercado La 14', tipo='Empresa', ...}
Donante ID=1: DonanteDTO{id=1, nombre='Carlos Pérez', ...}
Actualización donante ID=1: EXITOSA
```

---

## 8. Ventajas del Patrón DAO y DTO

| Ventaja | Descripción |
|---------|-------------|
| **Separación de responsabilidades** | El DTO transporta datos; el DAO accede a la BD. Cada clase tiene una única tarea. |
| **Mantenibilidad** | Si cambia el SQL o la BD, solo se modifica el DAO sin afectar la lógica de negocio. |
| **Seguridad** | `PreparedStatement` en los DAO previene ataques de inyección SQL. |
| **Reutilización** | Los DAO se usan desde cualquier parte del sistema sin duplicar código SQL. |
| **Testabilidad** | Los DAO pueden reemplazarse por mocks en pruebas unitarias sin tocar la BD real. |
| **Legibilidad** | Cada operación de BD tiene su propio método con nombre descriptivo. |

---

## 9. Conclusiones

- Los patrones DAO y DTO permiten construir aplicaciones Java con acceso a bases de datos de forma ordenada, segura y mantenible.
- El DTO elimina la dependencia directa entre la capa de presentación y el esquema de la base de datos.
- El DAO centraliza toda la lógica SQL, previniendo duplicación de código y mejorando la seguridad con `PreparedStatement`.
- La clase `ConexionBD` con patrón Singleton garantiza una sola conexión durante la ejecución, optimizando los recursos.
- La implementación cubre las cinco entidades del sistema con operaciones completas de inserción, consulta, actualización y eliminación.

---

## 10. Referencias

- Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994). *Design Patterns: Elements of Reusable Object-Oriented Software*. Addison-Wesley.
- Oracle Corporation. (2023). *JDBC Basics – Java Documentation*. https://docs.oracle.com/javase/tutorial/jdbc/basics/
- MySQL AB. (2024). *MySQL Connector/J Developer Guide*. https://dev.mysql.com/doc/connector-j/en/
- Fowler, M. (2002). *Patterns of Enterprise Application Architecture*. Addison-Wesley.
