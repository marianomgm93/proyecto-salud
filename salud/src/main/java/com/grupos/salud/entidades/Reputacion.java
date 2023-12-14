/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupos.salud.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author mvale
 */

@Entity
public class Reputacion implements Serializable{
    
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
    private String id;
    private Profesional profesional;
    private int cantValoraciones;
    private Double calificacion;
    
    public Reputacion() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCantValoraciones() {
        return cantValoraciones;
    }

    public void setCantValoraciones(int cantValoraciones) {
        this.cantValoraciones = cantValoraciones;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }
    
}
