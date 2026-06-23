# Frontend estatico

Este directorio contiene una interfaz HTML/CSS/JS simple para consumir el backend HTTP del proyecto.

## Estructura

```text
frontend/
|-- index.html
|-- css/
|   `-- estilos.css
`-- js/
    `-- app.js
```

## Uso

1. Levantar el backend:

```bash
docker compose up -d --build
```

2. Abrir `frontend/index.html` en el navegador.

La interfaz consulta la API en:

```text
http://localhost:18080
```

La interfaz consume el CRUD completo de Donantes, Beneficiarios, Productos y Entregas:

- `GET`: carga tablas y contadores.
- `POST`: crea registros desde los formularios.
- `PUT`: actualiza registros usando la accion Editar.
- `DELETE`: elimina registros usando la accion Eliminar.

Si el backend corre en otro puerto, actualizar `API_BASE_URL` en `frontend/js/app.js`.
