/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupos.salud.servicios;

import com.grupos.salud.entidades.Calificacion;
import com.grupos.salud.entidades.Profesional;
import com.grupos.salud.entidades.Reputacion;
import com.grupos.salud.entidades.Usuario;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.repositorios.CalificacionRepositorio;
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

    @Autowired
    private CalificacionRepositorio califRepositorio;
    
    @Transactional
    public Reputacion crearReputacion(Profesional profesional, int cantValoraciones,double calificacion){
        Reputacion reputacion = new Reputacion();
        reputacion.setCalificacion(calificacion);
        reputacion.setProfesional(profesional);
        reputacion.setCantValoraciones(cantValoraciones);
        reputacionRepositorio.save(reputacion);
        return reputacion;
    }
    
    @Transactional
    public Reputacion actualizarReputacion(Profesional profesional, int calificacion, Usuario usuario) throws MiException{
          
        Optional<Reputacion> respuesta = reputacionRepositorio.findByProfesional(profesional);
        Optional<Calificacion> respuestaCalif = califRepositorio.findByProfesionalAndUsuario(profesional, usuario);
        if(respuesta.isPresent()){
            if(respuestaCalif.isPresent()){
                guardarCalificacion(profesional, usuario, calificacion);
                Reputacion reputacion = respuesta.get();
                reputacion.setCantValoraciones(reputacion.getCantValoraciones() + 1);
                reputacion.setCalificacion((reputacion.getCalificacion() * (reputacion.getCantValoraciones() - 1) + calificacion) / reputacion.getCantValoraciones());
                reputacionRepositorio.save(reputacion);
                return reputacion;

            }else{
                editarCalificacion(profesional, usuario, calificacion);
                Reputacion reputacion = respuesta.get();
                reputacion.setCalificacion((reputacion.getCalificacion() * (reputacion.getCantValoraciones()) + calificacion) / reputacion.getCantValoraciones());
                reputacionRepositorio.save(reputacion);
                return reputacion;
            }
            
        }else{
            return null;
        }
        
    }
    
    public Reputacion encontrarPorIdProfesional(Profesional profesional){
        
        Optional<Reputacion> respuesta = reputacionRepositorio.findByProfesional(profesional);
        Reputacion reputacion = respuesta.get();
        return reputacion;
    }
    
    public void guardarCalificacion(Profesional profesional, Usuario usuario, int calificacion){
        Calificacion calif = new Calificacion();
        calif.setCalificacion(calificacion);
        calif.setProfesional(profesional);
        calif.setUsuario(usuario);
        califRepositorio.save(calif);
    }
    
    public void editarCalificacion(Profesional profesional, Usuario usuario, int calificacion){
        Optional<Calificacion> resp = califRepositorio.findByProfesionalAndUsuario(profesional, usuario);
        if(resp.isPresent()){
            Calificacion calif= resp.get();
            calif.setCalificacion(calificacion);
            califRepositorio.save(calif);
        }
    }
    
}
