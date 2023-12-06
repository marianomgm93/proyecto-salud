package com.grupos.salud.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Turno implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaYHora;

    private String estado;
    
    @ManyToOne
    private Profesional profesional;
    

    public Turno() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFechaYHora(Date fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public Date getFechaYHora() {
        return fechaYHora;
    }

    public String getEstado() {
        return estado;
    }

}
