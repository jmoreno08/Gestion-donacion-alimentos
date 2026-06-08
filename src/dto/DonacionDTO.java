package dto;

import java.time.LocalDate;

/**
 * DTO para la entidad Donacion.
 * Transfiere datos de donaciones entre capas sin lógica de negocio.
 */
public class DonacionDTO {

    private int       idDonacion;
    private int       idDonante;       // FK → Donantes
    private LocalDate fechaDonacion;
    private String    estado;          // Recibida, Revisada, Distribuida
    private String    observacion;

    public DonacionDTO() {}

    public DonacionDTO(int idDonacion, int idDonante, LocalDate fechaDonacion,
                       String estado, String observacion) {
        this.idDonacion   = idDonacion;
        this.idDonante    = idDonante;
        this.fechaDonacion = fechaDonacion;
        this.estado       = estado;
        this.observacion  = observacion;
    }

    public int       getIdDonacion()                    { return idDonacion; }
    public void      setIdDonacion(int id)              { this.idDonacion = id; }

    public int       getIdDonante()                     { return idDonante; }
    public void      setIdDonante(int id)               { this.idDonante = id; }

    public LocalDate getFechaDonacion()                 { return fechaDonacion; }
    public void      setFechaDonacion(LocalDate f)      { this.fechaDonacion = f; }

    public String    getEstado()                        { return estado; }
    public void      setEstado(String e)                { this.estado = e; }

    public String    getObservacion()                   { return observacion; }
    public void      setObservacion(String o)           { this.observacion = o; }

    @Override
    public String toString() {
        return "DonacionDTO{id=" + idDonacion + ", idDonante=" + idDonante +
               ", fecha=" + fechaDonacion + ", estado='" + estado +
               "', obs='" + observacion + "'}";
    }
}
