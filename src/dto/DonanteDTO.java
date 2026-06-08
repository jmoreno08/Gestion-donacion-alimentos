package dto;

/**
 * DTO (Data Transfer Object) para la entidad Donante.
 * Encapsula los datos del donante sin lógica de negocio.
 * Se usa para transferir información entre capas (DAO ↔ logica ↔ presentación).
 */
public class DonanteDTO {

    private int    idDonante;
    private String nombre;
    private String tipoDonante;   // Persona, Empresa o Fundación
    private String telefono;
    private String correo;
    private String direccion;

    // ── Constructor vacio ──────────────────────────────────────────
    public DonanteDTO() {}

    // ── Constructor con todos los campos ──────────────────────────
    public DonanteDTO(int idDonante, String nombre, String tipoDonante,
                      String telefono, String correo, String direccion) {
        this.idDonante   = idDonante;
        this.nombre      = nombre;
        this.tipoDonante = tipoDonante;
        this.telefono    = telefono;
        this.correo      = correo;
        this.direccion   = direccion;
    }

    // ── Getters y Setters ─────────────────────────────────────────
    public int    getIdDonante()             { return idDonante; }
    public void   setIdDonante(int id)       { this.idDonante = id; }

    public String getNombre()                { return nombre; }
    public void   setNombre(String n)        { this.nombre = n; }

    public String getTipoDonante()           { return tipoDonante; }
    public void   setTipoDonante(String t)   { this.tipoDonante = t; }

    public String getTelefono()              { return telefono; }
    public void   setTelefono(String t)      { this.telefono = t; }

    public String getCorreo()                { return correo; }
    public void   setCorreo(String c)        { this.correo = c; }

    public String getDireccion()             { return direccion; }
    public void   setDireccion(String d)     { this.direccion = d; }

    @Override
    public String toString() {
        return "DonanteDTO{id=" + idDonante + ", nombre='" + nombre +
               "', tipo='" + tipoDonante + "', tel='" + telefono +
               "', correo='" + correo + "', dir='" + direccion + "'}";
    }
}
