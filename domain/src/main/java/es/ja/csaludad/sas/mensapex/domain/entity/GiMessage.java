package es.ja.csaludad.sas.mensapex.domain.entity;

import es.ja.csalud.sas.framework.domain.Domain;

import java.time.LocalDateTime;

public class GiMessage extends Domain<Long> {

    private final Long id;
    private final String mgiUid;
    private String internalAhRegcess;
    private String internalPacienteSns;
    private final String mgiPacienteNuhsa;
    private final String mgiHeadVersion;
    private final String mgiHeadModule;
    private final String mgiHeadMaco;
    private final String mgiMesRecGiPet;
    private final String mgiMesEnvGiResp;
    private final String mgiMesEnvSnsPet;
    private final String mgiMesRecSnsResp;
    private final String mgiObservaciones;
    private final String mgiCodErrorSns;
    private final String mgiDescErrorSns;
    private final LocalDateTime mgiGiReceived;
    private final LocalDateTime mgiSnsSend;
    private final LocalDateTime mgiSnsReceived;
    private final LocalDateTime mgiCreated;
    private final String mgiCreatedBy;
    private final LocalDateTime mgiUpdated;
    private final String mgiUpdatedBy;
    //TODO: Falta añadir las relaciones
    private GiMessage(Builder builder) {
        this.id = builder.id;
        this.mgiUid = builder.mgiUid;
        this.internalAhRegcess = builder.internalAhRegcess;
        this.internalPacienteSns = builder.internalPacienteSns;
        this.mgiPacienteNuhsa = builder.mgiPacienteNuhsa;
        this.mgiHeadVersion = builder.mgiHeadVersion;
        this.mgiHeadModule = builder.mgiHeadModule;
        this.mgiHeadMaco = builder.mgiHeadMaco;
        this.mgiMesRecGiPet = builder.mgiMesRecGiPet;
        this.mgiMesEnvGiResp = builder.mgiMesEnvGiResp;
        this.mgiMesEnvSnsPet = builder.mgiMesEnvSnsPet;
        this.mgiMesRecSnsResp = builder.mgiMesRecSnsResp;
        this.mgiObservaciones = builder.mgiObservaciones;
        this.mgiCodErrorSns = builder.mgiCodErrorSns;
        this.mgiDescErrorSns = builder.mgiDescErrorSns;
        this.mgiGiReceived = builder.mgiGiReceived;
        this.mgiSnsSend = builder.mgiSnsSend;
        this.mgiSnsReceived = builder.mgiSnsReceived;
        this.mgiCreated = builder.mgiCreated;
        this.mgiCreatedBy = builder.mgiCreatedBy;
        this.mgiUpdated = builder.mgiUpdated;
        this.mgiUpdatedBy = builder.mgiUpdatedBy;
    }

    @Override
    public Long getDomainId() {
        return id;
    }

    @Override
    public void setDomainId(Long id) {
        throw new UnsupportedOperationException("GIMessage is immutable");
    }

    public Long getId() {
        return id;
    }

    public String getMgiUid() {
        return mgiUid;
    }

    public String getInternalAhRegcess() {
        return internalAhRegcess;
    }

    public String getInternalPacienteSns() {
        return internalPacienteSns;
    }

    public String getMgiPacienteNuhsa() {
        return mgiPacienteNuhsa;
    }

    public String getMgiHeadVersion() {
        return mgiHeadVersion;
    }

    public String getMgiHeadModule() {
        return mgiHeadModule;
    }

    public String getMgiHeadMaco() {
        return mgiHeadMaco;
    }

    public String getMgiMesRecGiPet() {
        return mgiMesRecGiPet;
    }

    public String getMgiMesEnvGiResp() {
        return mgiMesEnvGiResp;
    }

    public String getMgiMesEnvSnsPet() {
        return mgiMesEnvSnsPet;
    }

    public String getMgiMesRecSnsResp() {
        return mgiMesRecSnsResp;
    }

    public String getMgiObservaciones() {
        return mgiObservaciones;
    }

    public String getMgiCodErrorSns() {
        return mgiCodErrorSns;
    }

    public String getMgiDescErrorSns() {
        return mgiDescErrorSns;
    }

    public LocalDateTime getMgiGiReceived() {
        return mgiGiReceived;
    }

    public LocalDateTime getMgiSnsSend() {
        return mgiSnsSend;
    }

    public LocalDateTime getMgiSnsReceived() {
        return mgiSnsReceived;
    }

    public LocalDateTime getMgiCreated() {
        return mgiCreated;
    }

    public String getMgiCreatedBy() {
        return mgiCreatedBy;
    }

    public LocalDateTime getMgiUpdated() {
        return mgiUpdated;
    }

    public String getMgiUpdatedBy() {
        return mgiUpdatedBy;
    }

    public static class Builder {

        private Long id;
        private String mgiUid;
        private String internalAhRegcess;
        private String internalPacienteSns;
        private String mgiPacienteNuhsa;
        private String mgiHeadVersion;
        private String mgiHeadModule;
        private String mgiHeadMaco;
        private String mgiMesRecGiPet;
        private String mgiMesEnvGiResp;
        private String mgiMesEnvSnsPet;
        private String mgiMesRecSnsResp;
        private String mgiObservaciones;
        private String mgiCodErrorSns;
        private String mgiDescErrorSns;
        private LocalDateTime mgiGiReceived;
        private LocalDateTime mgiSnsSend;
        private LocalDateTime mgiSnsReceived;
        private LocalDateTime mgiCreated;
        private String mgiCreatedBy;
        private LocalDateTime mgiUpdated;
        private String mgiUpdatedBy;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withMgiUid(String mgiUid) {
            this.mgiUid = mgiUid;
            return this;
        }

        public Builder withInternalAhRegcess(String internalAhRegcess) {
            this.internalAhRegcess = internalAhRegcess;
            return this;
        }

        public Builder withInternalPacienteSns(String internalPacienteSns) {
            this.internalPacienteSns = internalPacienteSns;
            return this;
        }

        public Builder withMgiPacienteNuhsa(String mgiPacienteNuhsa) {
            this.mgiPacienteNuhsa = mgiPacienteNuhsa;
            return this;
        }

        public Builder withMgiHeadVersion(String mgiHeadVersion) {
            this.mgiHeadVersion = mgiHeadVersion;
            return this;
        }

        public Builder withMgiHeadModule(String mgiHeadModule) {
            this.mgiHeadModule = mgiHeadModule;
            return this;
        }

        public Builder withMgiHeadMaco(String mgiHeadMaco) {
            this.mgiHeadMaco = mgiHeadMaco;
            return this;
        }

        public Builder withMgiMesRecGiPet(String mgiMesRecGiPet) {
            this.mgiMesRecGiPet = mgiMesRecGiPet;
            return this;
        }

        public Builder withMgiMesEnvGiResp(String mgiMesEnvGiResp) {
            this.mgiMesEnvGiResp = mgiMesEnvGiResp;
            return this;
        }

        public Builder withMgiMesEnvSnsPet(String mgiMesEnvSnsPet) {
            this.mgiMesEnvSnsPet = mgiMesEnvSnsPet;
            return this;
        }

        public Builder withMgiMesRecSnsResp(String mgiMesRecSnsResp) {
            this.mgiMesRecSnsResp = mgiMesRecSnsResp;
            return this;
        }

        public Builder withMgiObservaciones(String mgiObservaciones) {
            this.mgiObservaciones = mgiObservaciones;
            return this;
        }

        public Builder withMgiCodErrorSns(String mgiCodErrorSns) {
            this.mgiCodErrorSns = mgiCodErrorSns;
            return this;
        }

        public Builder withMgiDescErrorSns(String mgiDescErrorSns) {
            this.mgiDescErrorSns = mgiDescErrorSns;
            return this;
        }

        public Builder withMgiGiReceived(LocalDateTime mgiGiReceived) {
            this.mgiGiReceived = mgiGiReceived;
            return this;
        }

        public Builder withMgiSnsSend(LocalDateTime mgiSnsSend) {
            this.mgiSnsSend = mgiSnsSend;
            return this;
        }

        public Builder withMgiSnsReceived(LocalDateTime mgiSnsReceived) {
            this.mgiSnsReceived = mgiSnsReceived;
            return this;
        }

        public Builder withMgiCreated(LocalDateTime mgiCreated) {
            this.mgiCreated = mgiCreated;
            return this;
        }

        public Builder withMgiCreatedBy(String mgiCreatedBy) {
            this.mgiCreatedBy = mgiCreatedBy;
            return this;
        }

        public Builder withMgiUpdated(LocalDateTime mgiUpdated) {
            this.mgiUpdated = mgiUpdated;
            return this;
        }

        public Builder withMgiUpdatedBy(String mgiUpdatedBy) {
            this.mgiUpdatedBy = mgiUpdatedBy;
            return this;
        }

        public GiMessage build() {
            return new GiMessage(this);
        }
    }
}