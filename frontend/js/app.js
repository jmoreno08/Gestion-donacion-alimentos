const API_BASE_URL = "http://localhost:18080";

const resources = {
    donantes: {
        path: "/api/donantes",
        idKey: "idDonante",
        table: "#tabla-donantes",
        count: "#count-donantes",
        columns: ["idDonante", "nombre", "telefono", "correo", "direccion"],
        numericFields: []
    },
    beneficiarios: {
        path: "/api/beneficiarios",
        idKey: "idBeneficiario",
        table: "#tabla-beneficiarios",
        count: "#count-beneficiarios",
        columns: ["idBeneficiario", "nombre", "documento", "telefono", "condicion"],
        numericFields: ["numeroIntegrantes"]
    },
    productos: {
        path: "/api/productos",
        idKey: "idProducto",
        table: "#tabla-productos",
        count: "#count-productos",
        columns: ["idProducto", "nombreProducto", "categoria", "unidadMedida", "fechaVencimiento"],
        numericFields: []
    },
    entregas: {
        path: "/api/entregas",
        idKey: "idEntrega",
        table: "#tabla-entregas",
        count: "#count-entregas",
        columns: ["idEntrega", "idBeneficiario", "fechaEntrega", "responsable", "observacion"],
        numericFields: ["idBeneficiario"]
    }
};

const state = {
    donantes: [],
    beneficiarios: [],
    productos: [],
    entregas: []
};

async function requestJson(path, options = {}) {
    const response = await fetch(`${API_BASE_URL}${path}`, {
        headers: {
            "Content-Type": "application/json",
            ...(options.headers || {})
        },
        ...options
    });

    if (!response.ok) {
        const message = await response.text();
        throw new Error(message || `HTTP ${response.status}`);
    }

    const text = await response.text();
    return text ? JSON.parse(text) : null;
}

function setStatus(ok) {
    const status = document.querySelector("#api-status");
    status.textContent = ok ? "API: conectada" : "API: sin conexión";
    status.classList.toggle("ok", ok);
    status.classList.toggle("error", !ok);
}

function showToast(message, type = "ok") {
    const toast = document.querySelector("#toast");
    toast.textContent = message;
    toast.className = `toast visible ${type}`;
    window.clearTimeout(showToast.timer);
    showToast.timer = window.setTimeout(() => {
        toast.classList.remove("visible");
    }, 2800);
}

function escapeHtml(value) {
    return String(value ?? "")
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

function actionButtons(entity, id) {
    return `
        <button class="editar" type="button" data-action="edit" data-entity="${entity}" data-id="${id}">Editar</button>
        <button class="eliminar" type="button" data-action="delete" data-entity="${entity}" data-id="${id}">Eliminar</button>
    `;
}

function renderRows(entity) {
    const config = resources[entity];
    const rows = state[entity];
    const tbody = document.querySelector(config.table);

    document.querySelector(config.count).textContent = rows.length;

    if (!rows.length) {
        tbody.innerHTML = `<tr><td colspan="${config.columns.length + 1}">No hay registros.</td></tr>`;
        return;
    }

    tbody.innerHTML = rows.map((row) => {
        const cells = config.columns
            .map((column) => `<td>${escapeHtml(row[column])}</td>`)
            .join("");
        return `<tr>${cells}<td>${actionButtons(entity, row[config.idKey])}</td></tr>`;
    }).join("");
}

async function loadResource(entity) {
    const config = resources[entity];
    state[entity] = await requestJson(config.path);
    renderRows(entity);
}

async function loadDashboard() {
    try {
        await Promise.all(Object.keys(resources).map(loadResource));
        setStatus(true);
    } catch (error) {
        setStatus(false);
        showToast("No se pudo conectar con la API", "error");
        console.error("No se pudo cargar la API", error);
    }
}

function serializeForm(form, entity) {
    const data = Object.fromEntries(new FormData(form).entries());
    delete data.id;

    for (const field of resources[entity].numericFields) {
        if (data[field] !== undefined && data[field] !== "") {
            data[field] = Number(data[field]);
        }
    }

    return data;
}

function resetForm(form) {
    form.reset();
    form.querySelector('[name="id"]').value = "";
    form.classList.remove("editing");
    form.querySelector("[data-cancel]").hidden = true;
}

function fillForm(entity, row) {
    const form = document.querySelector(`form[data-entity="${entity}"]`);
    const config = resources[entity];
    form.querySelector('[name="id"]').value = row[config.idKey];

    for (const [key, value] of Object.entries(row)) {
        const input = form.elements[key];
        if (input) input.value = value ?? "";
    }

    form.classList.add("editing");
    form.querySelector("[data-cancel]").hidden = false;
    form.scrollIntoView({ behavior: "smooth", block: "center" });
}

async function saveEntity(form) {
    const entity = form.dataset.entity;
    const config = resources[entity];
    const id = form.querySelector('[name="id"]').value;
    const body = serializeForm(form, entity);
    const method = id ? "PUT" : "POST";
    const path = id ? `${config.path}/${id}` : config.path;

    await requestJson(path, {
        method,
        body: JSON.stringify(body)
    });

    resetForm(form);
    await loadResource(entity);
    showToast(id ? "Registro actualizado" : "Registro creado");
}

async function deleteEntity(entity, id) {
    const config = resources[entity];
    const confirmed = window.confirm("¿Eliminar este registro?");
    if (!confirmed) return;

    await requestJson(`${config.path}/${id}`, { method: "DELETE" });
    await loadResource(entity);
    showToast("Registro eliminado");
}

document.addEventListener("DOMContentLoaded", loadDashboard);

document.addEventListener("submit", async (event) => {
    const form = event.target.closest("form[data-entity]");
    if (!form) return;

    event.preventDefault();
    try {
        await saveEntity(form);
    } catch (error) {
        showToast("No se pudo guardar el registro", "error");
        console.error(error);
    }
});

document.addEventListener("click", async (event) => {
    const cancel = event.target.closest("[data-cancel]");
    if (cancel) {
        resetForm(cancel.closest("form"));
        return;
    }

    const button = event.target.closest("[data-action]");
    if (!button) return;

    const { action, entity, id } = button.dataset;
    const config = resources[entity];
    const row = state[entity].find((item) => String(item[config.idKey]) === String(id));

    try {
        if (action === "edit" && row) fillForm(entity, row);
        if (action === "delete") await deleteEntity(entity, id);
    } catch (error) {
        showToast("No se pudo completar la acción", "error");
        console.error(error);
    }
});
