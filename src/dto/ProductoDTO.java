package dto;

import java.time.LocalDate;

/**
 * DTO para la entidad Producto.
 * Contiene los datos de un producto alimenticio donado.
 */
public class ProductoDTO {

    private int       idProducto;
    private String    nombreProducto;
    private String    categoria;       // Granos, Bebidas, Lácteos, etc.
    private String    unidadMedida;    // Kg, Litro, Unidad
    private LocalDate fechaVencimiento;

    public ProductoDTO() {}

    public ProductoDTO(int idProducto, String nombreProducto, String categoria,
                       String unidadMedida, LocalDate fechaVencimiento) {
        this.idProducto      = idProducto;
        this.nombreProducto  = nombreProducto;
        this.categoria       = categoria;
        this.unidadMedida    = unidadMedida;
        this.fechaVencimiento = fechaVencimiento;
    }

    public int       getIdProducto()                      { return idProducto; }
    public void      setIdProducto(int id)                { this.idProducto = id; }

    public String    getNombreProducto()                  { return nombreProducto; }
    public void      setNombreProducto(String n)          { this.nombreProducto = n; }

    public String    getCategoria()                       { return categoria; }
    public void      setCategoria(String c)               { this.categoria = c; }

    public String    getUnidadMedida()                    { return unidadMedida; }
    public void      setUnidadMedida(String u)            { this.unidadMedida = u; }

    public LocalDate getFechaVencimiento()                { return fechaVencimiento; }
    public void      setFechaVencimiento(LocalDate f)     { this.fechaVencimiento = f; }

    @Override
    public String toString() {
        return "ProductoDTO{id=" + idProducto + ", nombre='" + nombreProducto +
               "', cat='" + categoria + "', unidad='" + unidadMedida +
               "', vence=" + fechaVencimiento + "}";
    }
}
