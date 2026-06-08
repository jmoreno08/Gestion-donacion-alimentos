package dto;

import java.time.LocalDate;

/**
 * DTO para la entidad Entrega
 * Representa una entrega de alimentos realizada a un beneficiario
 */
public class EntregaDTO {

    private int       idEntrega;
    private int       idBeneficiario;   // FK → Beneficiarios
    private LocalDate fechaEntrega;
    private String    responsable;
    private String    observacion;

    public EntregaDTO() {}

    public EntregaDTO(int idEntrega, int idBeneficiario, LocalDate fechaEntrega,
                      String responsable, String observacion) {
        this.idEntrega      = idEntrega;
        this.idBeneficiario = idBeneficiario;
        this.fechaEntrega   = fechaEntrega;
        this.responsable    = responsable;
        this.observacion    = observacion;
    }

    public int       getIdEntrega()                     { return idEntrega; }
    public void      setIdEntrega(int id)               { this.idEntrega = id; }

    public int       getIdBeneficiario()                { return idBeneficiario; }
    public void      setIdBeneficiario(int id)          { this.idBeneficiario = id; }

    public LocalDate getFechaEntrega()                  { return fechaEntrega; }
    public void      setFechaEntrega(LocalDate f)       { this.fechaEntrega = f; }

    public String    getResponsable()                   { return responsable; }
    public void      setResponsable(String r)           { this.responsable = r; }

    public String    getObservacion()                   { return observacion; }
    public void      setObservacion(String o)           { this.observacion = o; }

    @Override
    public String toString() {
        return "EntregaDTO{id=" + idEntrega + ", idBeneficiario=" + idBeneficiario +
               ", fecha=" + fechaEntrega + ", responsable='" + responsable + "'}";
    }
}
