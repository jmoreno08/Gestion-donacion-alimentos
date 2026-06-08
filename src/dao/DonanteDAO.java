package dao;

import config.ConexionBD;
import dto.DonanteDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para la entidad Donante.
 * Centraliza todas las operaciones SQL sobre la tabla Donantes.
 */
public class DonanteDAO {

    // ── INSERT ────────────────────────────────────────────────────────────
    /**
     * Inserta un nuevo donante en la base de datos.
     *
     * @param dto objeto DonanteDTO con los datos a insertar
     * @return true si la insercion fue exitosa, false en caso contrario
     */
    public boolean insertar(DonanteDTO dto) {
        String sql = "INSERT INTO Donantes (nombre, tipo_donante, telefono, correo, direccion) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getTipoDonante());
            ps.setString(3, dto.getTelefono());
            ps.setString(4, dto.getCorreo());
            ps.setString(5, dto.getDireccion());

            int filas = ps.executeUpdate();
            System.out.println("Donante insertado. Filas afectadas: " + filas);
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar donante: " + e.getMessage());
            return false;
        }
    }

    // ── SELECT por ID ─────────────────────────────────────────────────────
    /**
     * Consulta un donante por su identificador único.
     *
     * @param id identificador del donante
     * @return DonanteDTO con los datos encontrados, o null si no existe
     */
    public DonanteDTO consultarPorId(int id) {
        String sql = "SELECT * FROM Donantes WHERE id_donante = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar donante: " + e.getMessage());
        }
        return null;
    }

    // ── SELECT todos ──────────────────────────────────────────────────────
    /**
     * Retorna todos los donantes registrados en la base de datos
     *
     * @return Lista de objetos DonanteDTO
     */
    public List<DonanteDTO> consultarTodos() {
        List<DonanteDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM Donantes ORDER BY nombre";
        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar donantes: " + e.getMessage());
        }
        return lista;
    }

    // ── UPDATE ────────────────────────────────────────────────────────────
    /**
     * Actualiza los datos de un donante existente.
     *
     * @param dto objeto DonanteDTO con los nuevos valores (el id debe estar seteado)
     * @return true si la actualización fue exitosa
     */
    public boolean actualizar(DonanteDTO dto) {
        String sql = "UPDATE Donantes SET nombre=?, tipo_donante=?, telefono=?, "
                   + "correo=?, direccion=? WHERE id_donante=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getTipoDonante());
            ps.setString(3, dto.getTelefono());
            ps.setString(4, dto.getCorreo());
            ps.setString(5, dto.getDireccion());
            ps.setInt   (6, dto.getIdDonante());

            int filas = ps.executeUpdate();
            System.out.println("Donante actualizado. Filas afectadas: " + filas);
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar donante: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────
    /**
     * Elimina un donante de la base de datos por su ID.
     *
     * @param id identificador del donante a eliminar
     * @return true si la eliminación fue exitosa
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Donantes WHERE id_donante = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            System.out.println("Donante eliminado. Filas afectadas: " + filas);
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar donante: " + e.getMessage());
            return false;
        }
    }

    // ── Método auxiliar: mapea ResultSet → DonanteDTO ─────────────────────
    private DonanteDTO mapearResultSet(ResultSet rs) throws SQLException {
        return new DonanteDTO(
            rs.getInt   ("id_donante"),
            rs.getString("nombre"),
            rs.getString("tipo_donante"),
            rs.getString("telefono"),
            rs.getString("correo"),
            rs.getString("direccion")
        );
    }
}
