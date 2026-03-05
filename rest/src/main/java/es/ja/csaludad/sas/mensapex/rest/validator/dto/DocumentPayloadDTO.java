package es.ja.csaludad.sas.mensapex.rest.validator.dto;

public class DocumentPayloadDTO {

    private String documentType; // ej: "NuevoDocumento", "modificacionDocumento"
    //TODO: Ver que campos vamos a validar en cada documento y meterlo aqui
    private String nuhsa;
    private String version;
    private String module;
    private String protocolo;
    private String seguimientoClinico;

    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }

    public String getNuhsa() { return nuhsa; }
    public void setNuhsa(String nuhsa) { this.nuhsa = nuhsa; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getModule() { return module; }
    public void setModule(String module) { this.module = module; }

    public String getProtocolo() { return protocolo; }
    public void setProtocolo(String protocolo) { this.protocolo = protocolo; }

    public String getSeguimientoClinico() { return seguimientoClinico; }
    public void setSeguimientoClinico(String seguimientoClinico) { this.seguimientoClinico = seguimientoClinico; }
}