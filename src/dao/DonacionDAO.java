package dao;

import config.ConexionBD;
import dto.DonacionDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Donacion.
 * Gestiona las operaciones CRUD sobre la tabla Donaciones.
 */
public class DonacionDAO {

    public boolean insertar(DonacionDTO dto) {
        String sql = "INSERT INTO Donaciones (id_donante, fecha_donacion, estado, observacion) "
                   + "VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt   (1, dto.getIdDonante());
            ps.setDate  (2, Date.valueOf(dto.getFechaDonacion()));
            ps.setString(3, dto.getEstado());
            ps.setString(4, dto.getObservacion());

            int filas = ps.executeUpdate();
            System.out.println("Donacion insertada. Filas: " + filas);
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar donacion: " + e.getMessage());
            return false;
        }
    }

    public DonacionDTO consultarPorId(int id) {
        String sql = "SELECT * FROM Donaciones WHERE id_donacion = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);

        } catch (SQLException e) {
            System.err.println("Error al consultar donacion: " + e.getMessage());
        }
        return null;
    }

    public List<DonacionDTO> consultarPorDonante(int idDonante) {
        List<DonacionDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM Donaciones WHERE id_donante = ? ORDER BY fecha_donacion DESC";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idDonante);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));

        } catch (SQLException e) {
            System.err.println("Error al consultar donaciones por donante: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(DonacionDTO dto) {
        String sql = "UPDATE Donaciones SET id_donante=?, fecha_donacion=?, estado=?, "
                   + "observacion=? WHERE id_donacion=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt   (1, dto.getIdDonante());
            ps.setDate  (2, Date.valueOf(dto.getFechaDonacion()));
            ps.setString(3, dto.getEstado());
            ps.setString(4, dto.getObservacion());
            ps.setInt   (5, dto.getIdDonacion());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar donacion: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Donaciones WHERE id_donacion = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar donacion: " + e.getMessage());
            return false;
        }
    }

    private DonacionDTO mapear(ResultSet rs) throws SQLException {
        return new DonacionDTO(
            rs.getInt   ("id_donacion"),
            rs.getInt   ("id_donante"),
            rs.getDate  ("fecha_donacion").toLocalDate(),
            rs.getString("estado"),
            rs.getString("observacion")
        );
    }
}
