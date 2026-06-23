const API_BASE_URL = "http://localhost:18080";

const endpoints = {
    donantes: "/api/donantes",
    beneficiarios: "/api/beneficiarios",
    productos: "/api/productos",
    entregas: "/api/entregas"
};

async function fetchJson(path) {
    const response = await fetch(`${API_BASE_URL}${path}`);
    if (!response.ok) {
        throw new Error(`HTTP ${response.status}`);
    }
    return response.json();
}

function setStatus(ok) {
    const status = document.querySelector("#api-status");
    status.textContent = ok ? "API: conectada" : "API: sin conexión";
    status.classList.toggle("ok", ok);
    status.classList.toggle("error", !ok);
}

function renderCount(id, value) {
    document.querySelector(id).textContent = value;
}

function actionButtons() {
    return '<button class="editar" type="button">Editar</button> <button class="eliminar" type="button">Eliminar</button>';
}

function renderRows(tableId, rows, columns) {
    const tbody = document.querySelector(tableId);
    if (!rows.length) {
        tbody.innerHTML = `<tr><td colspan="${columns.length + 1}">No hay registros.</td></tr>`;
        return;
    }

    tbody.innerHTML = rows.map((row) => {
        const cells = columns.map((column) => `<td>${row[column] ?? ""}</td>`).join("");
        return `<tr>${cells}<td>${actionButtons()}</td></tr>`;
    }).join("");
}

async function loadDashboard() {
    try {
        const [donantes, beneficiarios, productos, entregas] = await Promise.all([
            fetchJson(endpoints.donantes),
            fetchJson(endpoints.beneficiarios),
            fetchJson(endpoints.productos),
            fetchJson(endpoints.entregas)
        ]);

        setStatus(true);
        renderCount("#count-donantes", donantes.length);
        renderCount("#count-beneficiarios", beneficiarios.length);
        renderCount("#count-productos", productos.length);
        renderCount("#count-entregas", entregas.length);

        renderRows("#tabla-donantes", donantes, ["idDonante", "nombre", "telefono", "correo", "direccion"]);
        renderRows("#tabla-beneficiarios", beneficiarios, ["idBeneficiario", "nombre", "documento", "telefono", "condicion"]);
        renderRows("#tabla-productos", productos, ["idProducto", "nombreProducto", "categoria", "unidadMedida", "fechaVencimiento"]);
        renderRows("#tabla-entregas", entregas, ["idEntrega", "idBeneficiario", "fechaEntrega", "responsable", "observacion"]);
    } catch (error) {
        setStatus(false);
        console.error("No se pudo cargar la API", error);
    }
}

document.addEventListener("DOMContentLoaded", loadDashboard);
document.addEventListener("submit", (event) => {
    event.preventDefault();
});
