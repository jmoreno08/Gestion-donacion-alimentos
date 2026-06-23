# Evidencias de ejecucion y pruebas

Este documento resume las verificaciones realizadas para el backend HTTP y la base de datos ejecutados con Docker Compose.

## 1. Servicios Docker

Comando:

```bash
docker compose ps
```

Resultado esperado:

```text
gestion-donacion-app   Up   0.0.0.0:18080->8080/tcp
gestion-donacion-db    Up   healthy   0.0.0.0:3306->3306/tcp
```

## 2. Health check del backend

Comando:

```bash
curl http://localhost:18080/health
```

Respuesta esperada:

```json
{"status":"ok"}
```

## 3. Consulta de datos

Comando:

```bash
curl http://localhost:18080/api/donantes
```

Respuesta esperada:

```json
[
  {
    "idDonante": 1,
    "nombre": "Juan Perez",
    "tipoDonante": "Persona",
    "telefono": "3009999999",
    "correo": "juan@gmail.com",
    "direccion": "Cali"
  }
]
```

La respuesta real puede incluir mas registros si se han ejecutado pruebas de creacion desde Postman o curl.

## 4. Prueba CRUD manual

Crear un donante:

```bash
curl -X POST http://localhost:18080/api/donantes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Donante Prueba","tipoDonante":"Persona","telefono":"3000000000","correo":"prueba@example.com","direccion":"Cali"}'
```

Respuesta esperada:

```json
{"ok":true}
```

Consultar, actualizar y eliminar se prueban con las rutas documentadas en el README y en la coleccion Postman:

```text
GET    /api/donantes/{id}
PUT    /api/donantes/{id}
DELETE /api/donantes/{id}
```

## 5. Coleccion Postman

Archivo:

```text
Gestion_Donacion_Alimentos_Postman_Collection.json
```

La coleccion contiene pruebas manuales para:

- Health check.
- CRUD de Donantes.
- CRUD de Productos.
- CRUD de Beneficiarios.
- CRUD de Entregas.
