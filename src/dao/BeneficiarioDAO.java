package dao;

import config.ConexionBD;
import dto.BeneficiarioDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Beneficiario.
 * Gestiona las operaciones CRUD sobre la tabla Beneficiarios.
 */
public class BeneficiarioDAO {

    public boolean insertar(BeneficiarioDTO dto) {
        String sql = "INSERT INTO beneficiarios (nombre, documento, telefono, direccion, "
                   + "numero_integrantes, condicion) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getDocumento());
            ps.setString(3, dto.getTelefono());
            ps.setString(4, dto.getDireccion());
            ps.setInt   (5, dto.getNumeroIntegrantes());
            ps.setString(6, dto.getCondicion());

            int filas = ps.executeUpdate();
            System.out.println("Beneficiario insertado. Filas: " + filas);
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar beneficiario: " + e.getMessage());
            return false;
        }
    }

    public BeneficiarioDTO consultarPorId(int id) {
        String sql = "SELECT * FROM beneficiarios WHERE id_beneficiario = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);

        } catch (SQLException e) {
            System.err.println("Error al consultar beneficiario: " + e.getMessage());
        }
        return null;
    }

    public List<BeneficiarioDTO> consultarTodos() {
        List<BeneficiarioDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM beneficiarios ORDER BY nombre";
        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) lista.add(mapear(rs));

        } catch (SQLException e) {
            System.err.println("Error al consultar beneficiarios: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(BeneficiarioDTO dto) {
        String sql = "UPDATE beneficiarios SET nombre=?, documento=?, telefono=?, "
                   + "direccion=?, numero_integrantes=?, condicion=? WHERE id_beneficiario=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getDocumento());
            ps.setString(3, dto.getTelefono());
            ps.setString(4, dto.getDireccion());
            ps.setInt   (5, dto.getNumeroIntegrantes());
            ps.setString(6, dto.getCondicion());
            ps.setInt   (7, dto.getIdBeneficiario());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar beneficiario: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM beneficiarios WHERE id_beneficiario = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar beneficiario: " + e.getMessage());
            return false;
        }
    }

    private BeneficiarioDTO mapear(ResultSet rs) throws SQLException {
        return new BeneficiarioDTO(
            rs.getInt   ("id_beneficiario"),
            rs.getString("nombre"),
            rs.getString("documento"),
            rs.getString("telefono"),
            rs.getString("direccion"),
            rs.getInt   ("numero_integrantes"),
            rs.getString("condicion")
        );
    }
}
