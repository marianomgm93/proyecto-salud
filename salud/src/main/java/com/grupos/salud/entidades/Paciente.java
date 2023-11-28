package com.grupos.salud.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Paciente implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String datosContacto;
    private String obraSocial;
    
    @OneToOne
    private Usuario usuario;
    
    @OneToOne
    private Imagen imagen;
    
    private Boolean estado;
    
    @OneToOne
    private HistoriaClinica historiaClinica;

    public Boolean getEstado() {
        return estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
    
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }


    public Paciente() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatosContacto() {
        return datosContacto;
    }

    public void setDatosContacto(String datosContacto) {
        this.datosContacto = datosContacto;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }


    public Imagen getImagen() {

        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    

}
