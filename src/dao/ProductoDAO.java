package dao;

import config.ConexionBD;
import dto.ProductoDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Producto.
 * Gestiona las operaciones CRUD sobre la tabla Productos.
 */
public class ProductoDAO {

    public boolean insertar(ProductoDTO dto) {
        String sql = "INSERT INTO productos (nombre_producto, categoria, unidad_medida, fecha_vencimiento) "
                   + "VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dto.getNombreProducto());
            ps.setString(2, dto.getCategoria());
            ps.setString(3, dto.getUnidadMedida());
            ps.setDate  (4, Date.valueOf(dto.getFechaVencimiento()));

            int filas = ps.executeUpdate();
            System.out.println("Producto insertado. Filas: " + filas);
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }

    public ProductoDTO consultarPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id_producto = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);

        } catch (SQLException e) {
            System.err.println("Error al consultar producto: " + e.getMessage());
        }
        return null;
    }

    public List<ProductoDTO> consultarTodos() {
        List<ProductoDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos ORDER BY nombre_producto";
        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) lista.add(mapear(rs));

        } catch (SQLException e) {
            System.err.println("Error al consultar productos: " + e.getMessage());
        }
        return lista;
    }

    public List<ProductoDTO> consultarPorCategoria(String categoria) {
        List<ProductoDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE categoria = ? ORDER BY nombre_producto";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, categoria);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));

        } catch (SQLException e) {
            System.err.println("Error al consultar por categoría: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(ProductoDTO dto) {
        String sql = "UPDATE productos SET nombre_producto=?, categoria=?, "
                   + "unidad_medida=?, fecha_vencimiento=? WHERE id_producto=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dto.getNombreProducto());
            ps.setString(2, dto.getCategoria());
            ps.setString(3, dto.getUnidadMedida());
            ps.setDate  (4, Date.valueOf(dto.getFechaVencimiento()));
            ps.setInt   (5, dto.getIdProducto());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id_producto = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    private ProductoDTO mapear(ResultSet rs) throws SQLException {
        return new ProductoDTO(
            rs.getInt   ("id_producto"),
            rs.getString("nombre_producto"),
            rs.getString("categoria"),
            rs.getString("unidad_medida"),
            rs.getDate  ("fecha_vencimiento").toLocalDate()
        );
    }
}
