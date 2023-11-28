package com.grupos.salud.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Paciente {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String datosContacto;
    private String obraSocial;
    
    @OneToOne
    private Imagen imagen;
<<<<<<< HEAD
=======
    
    private Boolean estado;

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

>>>>>>> f10fc7925474af93a61140d6f6b5c88583917b9a

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

<<<<<<< HEAD
    public Imagen getImagen() {
=======

    public Imagen getImagen() {

>>>>>>> f10fc7925474af93a61140d6f6b5c88583917b9a
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }
<<<<<<< HEAD
}
=======

    

}
>>>>>>> f10fc7925474af93a61140d6f6b5c88583917b9a
