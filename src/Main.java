import config.ConexionBD;
import dao.*;
import dto.*;
import server.BackendServer;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase principal que demestra el uso de los patrones DAO y DTO
 * en el Sistema de Gestión de Donación de Alimentos.
 *
 * Operaciones demostradas:
 *   - Inserción de registros
 *   - Consulta por ID y consulta general
 *   - Actualización de datos
 */
public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length > 0 && "server".equalsIgnoreCase(args[0])) {
            BackendServer.start();
            return;
        }

        System.out.println("=================================================");
        System.out.println("  SISTEMA DE GESTIÓN DE DONACIÓN DE ALIMENTOS   ");
        System.out.println("         Demo de patrones DAO y DTO              ");
        System.out.println("=================================================\n");

        demoDonantes();
        demoDonaciones();
        demoProductos();
        demoBeneficiarios();
        demoEntregas();

        ConexionBD.cerrarConexion();
        System.out.println("\n=== Demo finalizada ===");
    }

    // ── DONANTES ──────────────────────────────────────────────────────────
    static void demoDonantes() {
        System.out.println("--- DONANTES ---");
        DonanteDAO dao = new DonanteDAO();

        // 1. Insertar
        DonanteDTO d1 = new DonanteDTO(0, "Carlos Pérez", "Persona",
                                       "3001234567", "carlos@email.com", "Calle 10 #5-20");
        DonanteDTO d2 = new DonanteDTO(0, "Supermercado La 14", "Empresa",
                                       "6044567890", "la14@empresa.com", "Av. El Poblado #45-80");
        dao.insertar(d1);
        dao.insertar(d2);

        // 2. Consultar todos
        List<DonanteDTO> todos = dao.consultarTodos();
        System.out.println("Total donantes registrados: " + todos.size());
        todos.forEach(d -> System.out.println("  -> " + d));

        // 3. Consultar por ID
        DonanteDTO encontrado = dao.consultarPorId(1);
        if (encontrado != null)
            System.out.println("Donante ID=1: " + encontrado);

        // 4. Actualizar teléfono
        if (encontrado != null) {
            encontrado.setTelefono("3009999999");
            boolean ok = dao.actualizar(encontrado);
            System.out.println("Actualización donante ID=1: " + (ok ? "EXITOSA" : "FALLIDA"));
        }

        System.out.println();
    }

    // ── DONACIONES ────────────────────────────────────────────────────────
    static void demoDonaciones() {
        System.out.println("--- DONACIONES ---");
        DonacionDAO dao = new DonacionDAO();

        DonacionDTO don1 = new DonacionDTO(0, 1, LocalDate.of(2025, 3, 10),
                                           "Recibida", "Donación de granos y aceite");
        DonacionDTO don2 = new DonacionDTO(0, 2, LocalDate.of(2025, 4, 5),
                                           "Distribuida", "Donación mensual supermercado");
        dao.insertar(don1);
        dao.insertar(don2);

        // Consultar donaciones del donante 1
        List<DonacionDTO> porDonante = dao.consultarPorDonante(1);
        System.out.println("Donaciones del donante ID=1: " + porDonante.size());
        porDonante.forEach(d -> System.out.println("  -> " + d));

        // Actualizar estado
        DonacionDTO d = dao.consultarPorId(1);
        if (d != null) {
            d.setEstado("Revisada");
            dao.actualizar(d);
            System.out.println("Estado actualizado a: " + dao.consultarPorId(1).getEstado());
        }

        System.out.println();
    }

    // ── PRODUCTOS ─────────────────────────────────────────────────────────
    static void demoProductos() {
        System.out.println("--- PRODUCTOS ---");
        ProductoDAO dao = new ProductoDAO();

        dao.insertar(new ProductoDTO(0, "Arroz blanco", "Granos",
                                    "Kg", LocalDate.of(2026, 1, 1)));
        dao.insertar(new ProductoDTO(0, "Aceite de girasol", "Aceites",
                                    "Litro", LocalDate.of(2025, 12, 31)));
        dao.insertar(new ProductoDTO(0, "Leche entera", "Lácteos",
                                    "Litro", LocalDate.of(2025, 8, 15)));

        List<ProductoDTO> todos = dao.consultarTodos();
        System.out.println("Total productos: " + todos.size());
        todos.forEach(p -> System.out.println("  -> " + p));

        // Filtrar por categoría
        List<ProductoDTO> granos = dao.consultarPorCategoria("Granos");
        System.out.println("Productos en categoría 'Granos': " + granos.size());

        System.out.println();
    }

    // ── BENEFICIARIOS ─────────────────────────────────────────────────────
    static void demoBeneficiarios() {
        System.out.println("--- BENEFICIARIOS ---");
        BeneficiarioDAO dao = new BeneficiarioDAO();

        dao.insertar(new BeneficiarioDTO(0, "Ana Gómez", "1020304050",
                                         "3112223344", "Carrera 8 #12-30", 4, "Desplazado"));
        dao.insertar(new BeneficiarioDTO(0, "Luis Martínez", "9988776655",
                                         "3001112233", "Calle 50 #20-10", 6, "Adulto mayor"));

        List<BeneficiarioDTO> todos = dao.consultarTodos();
        System.out.println("Total beneficiarios: " + todos.size());
        todos.forEach(b -> System.out.println("  -> " + b));

        // Actualizar número de integrantes
        BeneficiarioDTO b = dao.consultarPorId(1);
        if (b != null) {
            b.setNumeroIntegrantes(5);
            dao.actualizar(b);
            System.out.println("Integrantes actualizados: " + dao.consultarPorId(1).getNumeroIntegrantes());
        }

        System.out.println();
    }

    // ── ENTREGAS ──────────────────────────────────────────────────────────
    static void demoEntregas() {
        System.out.println("--- ENTREGAS ---");
        EntregaDAO dao = new EntregaDAO();

        dao.insertar(new EntregaDTO(0, 1, LocalDate.of(2025, 4, 20),
                                   "María Torres", "Entrega exitosa"));
        dao.insertar(new EntregaDTO(0, 2, LocalDate.of(2025, 4, 21),
                                   "Pedro López", "Sin novedad"));

        // Consultar entregas del beneficiario 1
        List<EntregaDTO> entregasB1 = dao.consultarPorBeneficiario(1);
        System.out.println("Entregas para beneficiario ID=1: " + entregasB1.size());
        entregasB1.forEach(e -> System.out.println("  -> " + e));

        System.out.println();
    }
}
