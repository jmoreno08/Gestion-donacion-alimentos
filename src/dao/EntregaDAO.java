package dao;

import config.ConexionBD;
import dto.EntregaDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Entrega.
 * Gestiona las operaciones CRUD sobre la tabla Entregas.
 */
public class EntregaDAO {

    public boolean insertar(EntregaDTO dto) {
        String sql = "INSERT INTO Entregas (id_beneficiario, fecha_entrega, responsable, observacion) "
                   + "VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt   (1, dto.getIdBeneficiario());
            ps.setDate  (2, Date.valueOf(dto.getFechaEntrega()));
            ps.setString(3, dto.getResponsable());
            ps.setString(4, dto.getObservacion());

            int filas = ps.executeUpdate();
            System.out.println("Entrega insertada. Filas: " + filas);
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar entrega: " + e.getMessage());
            return false;
        }
    }

    public EntregaDTO consultarPorId(int id) {
        String sql = "SELECT * FROM Entregas WHERE id_entrega = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);

        } catch (SQLException e) {
            System.err.println("Error al consultar entrega: " + e.getMessage());
        }
        return null;
    }

    public List<EntregaDTO> consultarPorBeneficiario(int idBeneficiario) {
        List<EntregaDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM Entregas WHERE id_beneficiario = ? ORDER BY fecha_entrega DESC";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idBeneficiario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));

        } catch (SQLException e) {
            System.err.println("Error al consultar entregas por beneficiario: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(EntregaDTO dto) {
        String sql = "UPDATE Entregas SET id_beneficiario=?, fecha_entrega=?, "
                   + "responsable=?, observacion=? WHERE id_entrega=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt   (1, dto.getIdBeneficiario());
            ps.setDate  (2, Date.valueOf(dto.getFechaEntrega()));
            ps.setString(3, dto.getResponsable());
            ps.setString(4, dto.getObservacion());
            ps.setInt   (5, dto.getIdEntrega());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar entrega: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Entregas WHERE id_entrega = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar entrega: " + e.getMessage());
            return false;
        }
    }

    private EntregaDTO mapear(ResultSet rs) throws SQLException {
        return new EntregaDTO(
            rs.getInt   ("id_entrega"),
            rs.getInt   ("id_beneficiario"),
            rs.getDate  ("fecha_entrega").toLocalDate(),
            rs.getString("responsable"),
            rs.getString("observacion")
        );
    }
}
