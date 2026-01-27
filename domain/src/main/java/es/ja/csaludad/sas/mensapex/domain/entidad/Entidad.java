package es.ja.csaludad.sas.mensapex.domain.entidad;

import es.ja.csalud.sas.framework.domain.Domain;

import java.time.LocalDateTime;

public class Entidad extends Domain<Long> {

    private final Long id;
    private final String nombre;
    private final String descripcion;
    private final LocalDateTime fechaCreacion;

    private Entidad(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.descripcion = builder.descripcion;
        this.fechaCreacion = builder.fechaCreacion;
    }

    @Override
    public Long getDomainId() {
        return id;
    }

    @Override
    public void setDomainId(Long aLong) {
        throw new UnsupportedOperationException("Entidad is immutable");
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }


    public static class Builder {
        private Long id;
        private String nombre;
        private String descripcion;
        private LocalDateTime fechaCreacion;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder withDescripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder withFechaCreacion(LocalDateTime fechaCreacion) {
            this.fechaCreacion = fechaCreacion;
            return this;
        }

        public Entidad build() {
            return new Entidad(this);
        }
    }
}
