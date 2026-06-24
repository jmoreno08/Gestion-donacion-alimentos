# Sistema de Gestión de Donación de Alimentos
## Entrega final — Procedimiento Almacenado y Transacción

**Autor:** Keith Dunwell Villalobos

---

## 1. ¿Qué es un Procedimiento Almacenado?

Un **procedimiento almacenado** (Stored Procedure) es un bloque de código SQL que se guarda directamente en el motor de base de datos y se puede invocar por su nombre desde cualquier aplicación o cliente SQL.

Sus características principales son:

- Se compila y almacena en la BD una sola vez; las siguientes ejecuciones son más rápidas.
- Acepta parámetros de entrada (`IN`) y salida (`OUT`), funcionando como una función.
- Permite incluir lógica condicional (`IF`, `CASE`), bucles y manejo de errores (`HANDLER`).
- Reduce el tráfico de red: en lugar de enviar múltiples sentencias SQL, se envía solo una llamada `CALL`.
- Centraliza reglas de negocio en la BD, independientemente del lenguaje de programación usado.

---

## 2. ¿Qué es una Transacción?

Una **transacción** es un conjunto de operaciones SQL que se ejecutan como una unidad indivisible. Se rige por las propiedades **ACID**:

| Propiedad | Significado |
|-----------|-------------|
| **Atomicidad** | Todas las operaciones se completan, o ninguna se aplica. |
| **Consistencia** | La BD pasa de un estado válido a otro estado válido. |
| **Aislamiento** | Las operaciones intermedias no son visibles para otras sesiones. |
| **Durabilidad** | Una vez confirmados con `COMMIT`, los cambios son permanentes. |

Las instrucciones clave son:

```sql
START TRANSACTION;  -- inicia la transacción
COMMIT;             -- confirma todos los cambios
ROLLBACK;           -- revierte todo si algo falla
```

---

## 3. Procedimiento Implementado: `sp_registrar_entrega_completa`

### 3.1 ¿Por qué se implementó?

En el sistema de donación de alimentos, registrar una entrega implica **dos operaciones que deben ocurrir juntas**:

1. Insertar un nuevo registro en la tabla `entregas`.
2. Actualizar el estado de la `donacion` relacionada a `'Entregada'`.

Si solo se ejecuta una de las dos (por ejemplo, por un error de red o de sistema), los datos quedan **inconsistentes**: habría una entrega registrada cuya donación sigue marcada como disponible, o viceversa. La transacción dentro del procedimiento garantiza que **ambas ocurran o ninguna**.

### 3.2 Parámetros

| Parámetro | Tipo | Dirección | Descripción |
|-----------|------|-----------|-------------|
| `p_id_beneficiario` | `INT` | `IN` | ID del beneficiario que recibe la entrega |
| `p_fecha_entrega` | `DATE` | `IN` | Fecha de la entrega |
| `p_responsable` | `VARCHAR(100)` | `IN` | Nombre del responsable |
| `p_observacion` | `VARCHAR(255)` | `IN` | Observaciones adicionales |
| `p_id_donacion` | `INT` | `IN` | Donación de origen |
| `p_resultado` | `VARCHAR(255)` | `OUT` | Mensaje de éxito o error |

### 3.3 Flujo del procedimiento

```
CALL sp_registrar_entrega_completa(...)
        │
        ├── START TRANSACTION
        │
        ├── PASO 1: ¿Existe el beneficiario?
        │       └── NO → ROLLBACK + mensaje de error
        │
        ├── PASO 2: ¿Existe la donación? ¿Ya fue entregada?
        │       ├── NO existe  → ROLLBACK + mensaje de error
        │       └── Ya entregada → ROLLBACK + mensaje de error
        │
        ├── PASO 3: INSERT INTO entregas (...)
        │
        ├── PASO 4: UPDATE donaciones SET estado = 'Entregada'
        │
        └── PASO 5: ¿Ocurrió algún error interno?
                ├── SÍ → ROLLBACK
                └── NO → COMMIT + mensaje de éxito
```

### 3.4 Código del procedimiento

```sql
DELIMITER $$

CREATE PROCEDURE sp_registrar_entrega_completa(
    IN  p_id_beneficiario  INT,
    IN  p_fecha_entrega    DATE,
    IN  p_responsable      VARCHAR(100),
    IN  p_observacion      VARCHAR(255),
    IN  p_id_donacion      INT,
    OUT p_resultado        VARCHAR(255)
)
BEGIN
    DECLARE v_existe_beneficiario INT DEFAULT 0;
    DECLARE v_existe_donacion     INT DEFAULT 0;
    DECLARE v_estado_donacion     VARCHAR(50);
    DECLARE v_id_entrega_nuevo    INT;
    DECLARE v_error               TINYINT DEFAULT 0;

    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
    BEGIN
        SET v_error = 1;
    END;

    START TRANSACTION;

    -- PASO 1: Verificar beneficiario
    SELECT COUNT(*) INTO v_existe_beneficiario
    FROM beneficiarios WHERE id_beneficiario = p_id_beneficiario;

    IF v_existe_beneficiario = 0 THEN
        SET p_resultado = 'ERROR: El beneficiario indicado no existe en el sistema.';
        ROLLBACK;
    ELSE

        -- PASO 2: Verificar donación
        SELECT COUNT(*), estado INTO v_existe_donacion, v_estado_donacion
        FROM donaciones WHERE id_donacion = p_id_donacion GROUP BY estado;

        IF v_existe_donacion = 0 THEN
            SET p_resultado = 'ERROR: La donación indicada no existe en el sistema.';
            ROLLBACK;

        ELSEIF v_estado_donacion = 'Entregada' THEN
            SET p_resultado = 'ERROR: La donación ya fue entregada anteriormente.';
            ROLLBACK;

        ELSE
            -- PASO 3: Insertar entrega
            INSERT INTO entregas (id_beneficiario, fecha_entrega, responsable, observacion)
            VALUES (p_id_beneficiario, p_fecha_entrega, p_responsable, p_observacion);

            SET v_id_entrega_nuevo = LAST_INSERT_ID();

            -- PASO 4: Actualizar donación
            UPDATE donaciones
            SET estado = 'Entregada',
                observacion = CONCAT(observacion, ' | Entregada en id_entrega: ',
                                     v_id_entrega_nuevo)
            WHERE id_donacion = p_id_donacion;

            -- PASO 5: Confirmar o revertir
            IF v_error = 1 THEN
                ROLLBACK;
                SET p_resultado = 'ERROR: Fallo interno. Se revirtieron todos los cambios.';
            ELSE
                COMMIT;
                SET p_resultado = CONCAT(
                    'ÉXITO: Entrega #', v_id_entrega_nuevo,
                    ' registrada para beneficiario ID ', p_id_beneficiario,
                    '. Donación ID ', p_id_donacion, ' marcada como Entregada.'
                );
            END IF;
        END IF;
    END IF;

END$$

DELIMITER ;
```

---

## 4. Ejemplos de Ejecución

### Ejemplo 1 — Ejecución exitosa

Registrar entrega para **Maria Rojas** (ID 3) usando la donación 3 (estado actual: `Pendiente`).

```sql
CALL sp_registrar_entrega_completa(
    3,
    '2026-06-10',
    'Pedro Salcedo',
    'Entrega por procedimiento almacenado',
    3,
    @resultado
);
SELECT @resultado AS resultado;
```

**Resultado esperado:**

```
+------------------------------------------------------------------------------------+
| resultado                                                                          |
+------------------------------------------------------------------------------------+
| ÉXITO: Entrega #6 registrada para beneficiario ID 3. Donación ID 3 marcada como   |
| Entregada.                                                                         |
+------------------------------------------------------------------------------------+
```

### Ejemplo 2 — Error: beneficiario inexistente

```sql
CALL sp_registrar_entrega_completa(
    999,
    '2026-06-10',
    'Pedro Salcedo',
    'Prueba de error',
    3,
    @resultado
);
SELECT @resultado AS resultado;
```

**Resultado esperado:**

```
+----------------------------------------------------------+
| resultado                                                |
+----------------------------------------------------------+
| ERROR: El beneficiario indicado no existe en el sistema. |
+----------------------------------------------------------+
```

### Ejemplo 3 — Error: donación ya entregada

```sql
CALL sp_registrar_entrega_completa(
    1,
    '2026-06-10',
    'Pedro Salcedo',
    'Prueba donacion ya usada',
    4,
    @resultado
);
SELECT @resultado AS resultado;
```

**Resultado esperado:**

```
+-------------------------------------------------------+
| resultado                                             |
+-------------------------------------------------------+
| ERROR: La donación ya fue entregada anteriormente.    |
+-------------------------------------------------------+
```

### Verificación tras el Ejemplo 1

```sql
-- Ver la nueva entrega registrada
SELECT * FROM entregas ORDER BY id_entrega DESC LIMIT 3;

-- Verificar que la donación cambió de estado
SELECT * FROM donaciones WHERE id_donacion = 3;
```

---

## 5. Evidencia de Transacción

El procedimiento usa `START TRANSACTION` / `COMMIT` / `ROLLBACK` explícitos. La tabla siguiente muestra qué pasa en cada escenario:

| Escenario | INSERT entregas | UPDATE donaciones | Resultado final |
|-----------|:-:|:-:|---------|
| Todo correcto | ✅ Se ejecuta | ✅ Se ejecuta | `COMMIT` — cambios permanentes |
| Beneficiario no existe | ❌ No se ejecuta | ❌ No se ejecuta | `ROLLBACK` — nada cambia |
| Donación ya entregada | ❌ No se ejecuta | ❌ No se ejecuta | `ROLLBACK` — nada cambia |
| Error interno de SQL | ✅ Intentado | ✅ Intentado | `ROLLBACK` — todo revertido |

---

## 6. Beneficios para el Proyecto

| Beneficio | Descripción |
|-----------|-------------|
| **Integridad de datos** | Nunca quedará una entrega sin su donación actualizada, ni viceversa. |
| **Validaciones centralizadas** | Las reglas (beneficiario válido, donación disponible) viven en la BD, no en el código Java. |
| **Reutilización** | Cualquier aplicación (Java, web, móvil) puede llamar `CALL sp_registrar_entrega_completa(...)` sin duplicar lógica. |
| **Seguridad** | Al no exponer SQL directo desde la app, se reduce la superficie de ataque. |
| **Trazabilidad** | El procedimiento actualiza la observación de la donación con el ID de entrega generado, facilitando auditorías. |
| **Rendimiento** | El procedimiento se compila en la BD; sucesivas llamadas son más rápidas que enviar SQL desde Java. |

---

## 7. Conclusiones

- Un procedimiento almacenado permite centralizar lógica compleja directamente en el motor de base de datos, haciéndola accesible desde cualquier tecnología.
- La transacción dentro del procedimiento garantiza la propiedad ACID: si cualquier paso falla, todos los cambios se revierten con `ROLLBACK`, manteniendo la consistencia de los datos.
- En el sistema de donación de alimentos, este procedimiento es especialmente útil porque garantiza que registrar una entrega y actualizar el estado de la donación siempre ocurran juntos, eliminando el riesgo de datos inconsistentes.
- El parámetro `OUT p_resultado` permite que la aplicación Java (a través del DAO) reciba un mensaje claro del resultado sin necesidad de hacer consultas adicionales.

---

## 8. Referencias

- MariaDB Foundation. (2024). *Stored Procedures*. https://mariadb.com/kb/en/stored-procedures/
- MariaDB Foundation. (2024). *Transactions and Locking*. https://mariadb.com/kb/en/transactions/
- Silberschatz, A., Korth, H., & Sudarshan, S. (2019). *Database System Concepts* (7th ed.). McGraw-Hill.

---