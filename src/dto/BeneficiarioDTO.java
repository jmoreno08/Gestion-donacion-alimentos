package dto;

/**
 * DTO para la entidad Beneficiario.
 * Almacena los datos personales y sociales del beneficiario.
 */
public class BeneficiarioDTO {

    private int    idBeneficiario;
    private String nombre;
    private String documento;
    private String telefono;
    private String direccion;
    private int    numeroIntegrantes;
    private String condicion;          // Condición social del beneficiario

    public BeneficiarioDTO() {}

    public BeneficiarioDTO(int idBeneficiario, String nombre, String documento,
                           String telefono, String direccion,
                           int numeroIntegrantes, String condicion) {
        this.idBeneficiario   = idBeneficiario;
        this.nombre           = nombre;
        this.documento        = documento;
        this.telefono         = telefono;
        this.direccion        = direccion;
        this.numeroIntegrantes = numeroIntegrantes;
        this.condicion        = condicion;
    }

    public int    getIdBeneficiario()                    { return idBeneficiario; }
    public void   setIdBeneficiario(int id)              { this.idBeneficiario = id; }

    public String getNombre()                            { return nombre; }
    public void   setNombre(String n)                    { this.nombre = n; }

    public String getDocumento()                         { return documento; }
    public void   setDocumento(String d)                 { this.documento = d; }

    public String getTelefono()                          { return telefono; }
    public void   setTelefono(String t)                  { this.telefono = t; }

    public String getDireccion()                         { return direccion; }
    public void   setDireccion(String d)                 { this.direccion = d; }

    public int    getNumeroIntegrantes()                 { return numeroIntegrantes; }
    public void   setNumeroIntegrantes(int n)            { this.numeroIntegrantes = n; }

    public String getCondicion()                         { return condicion; }
    public void   setCondicion(String c)                 { this.condicion = c; }

    @Override
    public String toString() {
        return "BeneficiarioDTO{id=" + idBeneficiario + ", nombre='" + nombre +
               "', doc='" + documento + "', integrantes=" + numeroIntegrantes +
               ", condicion='" + condicion + "'}";
    }
}
