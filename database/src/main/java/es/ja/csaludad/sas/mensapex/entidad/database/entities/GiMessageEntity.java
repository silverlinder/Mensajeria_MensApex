package es.ja.csaludad.sas.mensapex.entidad.database.entities;

import es.ja.csalud.sas.framework.infraestructura.database.jpa.repository.enitity.JpaEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "S411_VTM_MENS_GI")
public class GiMessageEntity extends JpaEntity<Long> {

    @Id
    @Column(name = "MGI_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MGI_UID", length = 100)
    private String mgiUid;

    @Column(name = "INTERNAL_AH_REGCESS", length = 100)
    private String internalAhRegcess;

    @Column(name = "INTERNAL_PACIENTE_SNS", length = 100)
    private String internalPacienteSns;

    @Column(name = "MGI_PACIENTE_NUHSA", nullable = false, length = 20)
    private String mgiPacienteNuhsa;

    @Column(name = "MGI_HEAD_VERSION", length = 200)
    private String mgiHeadVersion;

    @Column(name = "MGI_HEAD_MODULE", length = 100)
    private String mgiHeadModule;

    @Column(name = "MGI_HEAD_MACO", length = 500)
    private String mgiHeadMaco;

    @Lob
    @Column(name = "MGI_MES_REC_GI_PET")
    private String mgiMesRecGiPet;

    @Lob
    @Column(name = "MGI_MES_ENV_GI_RESP")
    private String mgiMesEnvGiResp;

    @Lob
    @Column(name = "MGI_MES_ENV_SNS_PET")
    private String mgiMesEnvSnsPet;

    @Lob
    @Column(name = "MGI_MES_REC_SNS_RESP")
    private String mgiMesRecSnsResp;

    @Column(name = "MGI_OBSERVACIONES", length = 4000)
    private String mgiObservaciones;

    @Column(name = "MGI_COD_ERROR_SNS", length = 20)
    private String mgiCodErrorSns;

    @Column(name = "MGI_DESC_ERROR_SNS", length = 1000)
    private String mgiDescErrorSns;

    @Column(name = "MGI_GI_RECEIVED")
    private LocalDateTime mgiGiReceived;

    @Column(name = "MGI_SNS_SEND")
    private LocalDateTime mgiSnsSend;

    @Column(name = "MGI_SNS_RECEIVED")
    private LocalDateTime mgiSnsReceived;

    @Column(name = "MGI_CREATED", nullable = false)
    private LocalDateTime mgiCreated;

    @Column(name = "MGI_CREATED_BY", length = 100)
    private String mgiCreatedBy;

    @Column(name = "MGI_UPDATED")
    private LocalDateTime mgiUpdated;

    @Column(name = "MGI_UPDATED_BY", length = 100)
    private String mgiUpdatedBy;

//    TODO FUTURO: mapear relaciones JPA
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "MGI_PROTOCOLO", insertable = false, updatable = false)
//    private ProtocoloEntity protocolo;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "MGI_ESTADO", insertable = false, updatable = false)
//    private EstadoEntity estado;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "MGI_TIPO_OPERACION", insertable = false, updatable = false)
//    private TipoOperacionEntity tipoOperacion;



    public GiMessageEntity() {
        //Empty constructor
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMgiUid() {
        return mgiUid;
    }

    public void setMgiUid(String mgiUid) {
        this.mgiUid = mgiUid;
    }

    public String getInternalAhRegcess() {
        return internalAhRegcess;
    }

    public void setInternalAhRegcess(String internalAhRegcess) {
        this.internalAhRegcess = internalAhRegcess;
    }

    public String getInternalPacienteSns() {
        return internalPacienteSns;
    }

    public void setInternalPacienteSns(String internalPacienteSns) {
        this.internalPacienteSns = internalPacienteSns;
    }

    public String getMgiPacienteNuhsa() {
        return mgiPacienteNuhsa;
    }

    public void setMgiPacienteNuhsa(String mgiPacienteNuhsa) {
        this.mgiPacienteNuhsa = mgiPacienteNuhsa;
    }

    public String getMgiHeadVersion() {
        return mgiHeadVersion;
    }

    public void setMgiHeadVersion(String mgiHeadVersion) {
        this.mgiHeadVersion = mgiHeadVersion;
    }

    public String getMgiHeadModule() {
        return mgiHeadModule;
    }

    public void setMgiHeadModule(String mgiHeadModule) {
        this.mgiHeadModule = mgiHeadModule;
    }

    public String getMgiHeadMaco() {
        return mgiHeadMaco;
    }

    public void setMgiHeadMaco(String mgiHeadMaco) {
        this.mgiHeadMaco = mgiHeadMaco;
    }

    public String getMgiMesRecGiPet() {
        return mgiMesRecGiPet;
    }

    public void setMgiMesRecGiPet(String mgiMesRecGiPet) {
        this.mgiMesRecGiPet = mgiMesRecGiPet;
    }

    public String getMgiMesEnvGiResp() {
        return mgiMesEnvGiResp;
    }

    public void setMgiMesEnvGiResp(String mgiMesEnvGiResp) {
        this.mgiMesEnvGiResp = mgiMesEnvGiResp;
    }

    public String getMgiMesEnvSnsPet() {
        return mgiMesEnvSnsPet;
    }

    public void setMgiMesEnvSnsPet(String mgiMesEnvSnsPet) {
        this.mgiMesEnvSnsPet = mgiMesEnvSnsPet;
    }

    public String getMgiMesRecSnsResp() {
        return mgiMesRecSnsResp;
    }

    public void setMgiMesRecSnsResp(String mgiMesRecSnsResp) {
        this.mgiMesRecSnsResp = mgiMesRecSnsResp;
    }

    public String getMgiObservaciones() {
        return mgiObservaciones;
    }

    public void setMgiObservaciones(String mgiObservaciones) {
        this.mgiObservaciones = mgiObservaciones;
    }

    public String getMgiCodErrorSns() {
        return mgiCodErrorSns;
    }

    public void setMgiCodErrorSns(String mgiCodErrorSns) {
        this.mgiCodErrorSns = mgiCodErrorSns;
    }

    public String getMgiDescErrorSns() {
        return mgiDescErrorSns;
    }

    public void setMgiDescErrorSns(String mgiDescErrorSns) {
        this.mgiDescErrorSns = mgiDescErrorSns;
    }

    public LocalDateTime getMgiGiReceived() {
        return mgiGiReceived;
    }

    public void setMgiGiReceived(LocalDateTime mgiGiReceived) {
        this.mgiGiReceived = mgiGiReceived;
    }

    public LocalDateTime getMgiSnsSend() {
        return mgiSnsSend;
    }

    public void setMgiSnsSend(LocalDateTime mgiSnsSend) {
        this.mgiSnsSend = mgiSnsSend;
    }

    public LocalDateTime getMgiSnsReceived() {
        return mgiSnsReceived;
    }

    public void setMgiSnsReceived(LocalDateTime mgiSnsReceived) {
        this.mgiSnsReceived = mgiSnsReceived;
    }

    public LocalDateTime getMgiCreated() {
        return mgiCreated;
    }

    public void setMgiCreated(LocalDateTime mgiCreated) {
        this.mgiCreated = mgiCreated;
    }

    public String getMgiCreatedBy() {
        return mgiCreatedBy;
    }

    public void setMgiCreatedBy(String mgiCreatedBy) {
        this.mgiCreatedBy = mgiCreatedBy;
    }

    public LocalDateTime getMgiUpdated() {
        return mgiUpdated;
    }

    public void setMgiUpdated(LocalDateTime mgiUpdated) {
        this.mgiUpdated = mgiUpdated;
    }

    public String getMgiUpdatedBy() {
        return mgiUpdatedBy;
    }

    public void setMgiUpdatedBy(String mgiUpdatedBy) {
        this.mgiUpdatedBy = mgiUpdatedBy;
    }

//    public ProtocoloEntity getProtocolo() {
//        return protocolo;
//    }
//
//    public void setProtocolo(ProtocoloEntity protocolo) {
//        this.protocolo = protocolo;
//    }
//
//    public EstadoEntity getEstado() {
//        return estado;
//    }
//
//    public void setEstado(EstadoEntity estado) {
//        this.estado = estado;
//    }
//
//    public TipoOperacionEntity getTipoOperacion() {
//        return tipoOperacion;
//    }
//
//    public void setTipoOperacion(TipoOperacionEntity tipoOperacion) {
//        this.tipoOperacion = tipoOperacion;
//    }
}