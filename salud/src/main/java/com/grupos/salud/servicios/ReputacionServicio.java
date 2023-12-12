/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupos.salud.servicios;

import com.grupos.salud.entidades.Reputacion;
import com.grupos.salud.repositorios.ReputacionRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mvale
 */

@Service
public class ReputacionServicio {
    
    @Autowired
    private ReputacionRepositorio reputacionRepositorio;
    
    
    @Transactional
    public Reputacion crearReputacion(String idProfesional, int cantValoraciones,double calificacion){
        Reputacion reputacion = new Reputacion();
        reputacion.setCalificacion(calificacion);
        reputacion.setIdProfesional(idProfesional);
        reputacion.setCantValoraciones(0);
        reputacionRepositorio.save(reputacion);
        return reputacion;
    }
    
    @Transactional
    public void actualizarReputacion(String idProfesional, int calificacion){
       
        
        Optional<Reputacion> respuesta = reputacionRepositorio.findByIdProfesional(idProfesional);
        if(respuesta.isPresent()){
            Reputacion reputacion = respuesta.get();
            reputacion.setCantValoraciones(reputacion.getCantValoraciones() + 1);
            reputacion.setCalificacion(reputacion.getCalificacion() + calificacion / reputacion.getCantValoraciones());
            
            reputacionRepositorio.save(reputacion);
        }
     
        
    }
    
    public Reputacion encontrarPorIdProfesional(String idProfesional){
        
        Optional<Reputacion> respuesta = reputacionRepositorio.findByIdProfesional(idProfesional);
        Reputacion reputacion = respuesta.get();
        return reputacion;
    }
    
}
