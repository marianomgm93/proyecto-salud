package com.grupos.salud.entidades;

import javax.persistence.Entity;

@Entity
public class Profesional extends Usuario{
    
    private String especialidad;
    private Double reputacion;
    private Double valorConsulta;

    public Profesional() {
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Double getReputacion() {
        return reputacion;
    }

    public void setReputacion(Double reputacion) {
        this.reputacion = reputacion;
    }

    public Double getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(Double valorConsulta) {
        this.valorConsulta = valorConsulta;
    }
    
    

}
