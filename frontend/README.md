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

Por ahora los formularios son visuales. Las tablas y contadores cargan datos reales desde los endpoints `GET` del backend.
