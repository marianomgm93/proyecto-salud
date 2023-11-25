package com.grupos.salud.servicios;

import com.grupos.salud.entidades.Profesional;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.repositorios.ProfesionalRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfesionalServicio {

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;
    
    @Transactional
    public void registrar(String especialidad, Double reputacion, Double valorConsulta) throws MiException{

        validar (especialidad, reputacion, valorConsulta);
        Profesional profesional = new Profesional();
        profesional.setEspecialidad(especialidad);
        profesional.setReputacion(reputacion);
        profesional.setValorConsulta(valorConsulta);
        
        profesionalRepositorio.save(profesional);
    }
    
    @Transactional(readOnly=true)
    public List<Profesional> listarProfesionales(){
        List<Profesional> profesionales = profesionalRepositorio.findAll();
        return profesionales;
    }
            
    
    public Profesional getOne(String id){
        Profesional profesional = profesionalRepositorio.getOne(id);
        return profesional;
    } 
    
    @Transactional
    public void actualizar(String id, String especialidad, Double reputacion, Double valorConsulta) throws MiException{

        validar (especialidad, reputacion, valorConsulta);
        Optional<Profesional> respuesta = profesionalRepositorio.findById(id);
        if(respuesta.isPresent()){
            Profesional profesional = respuesta.get();
            profesional.setEspecialidad(especialidad);
            profesional.setReputacion(reputacion);
            profesional.setValorConsulta(valorConsulta);
            profesional.setEstado(true);
                     
            
            profesionalRepositorio.save(profesional);
        }
    }
    
    public void darDeBaja(String id){
         Optional<Profesional> respuesta = profesionalRepositorio.findById(id);
         if(respuesta.isPresent()){
             Profesional profesional = respuesta.get();
             profesional.setEstado(false);
             profesionalRepositorio.save(profesional);
         }
    }

    public List<Profesional> buscarPorEspecialidad(String especialidad){
        List<Profesional> profesionales = profesionalRepositorio.findByEspecialidad(especialidad);
        return profesionales;
    }
    
    private void validar(String especialidad, Double reputacion, Double valorConsulta) throws MiException{
        if(especialidad.isEmpty()){
            throw new MiException("El dato de especialidad no pueden ser nulo o estar vacío.");
        }
        if(reputacion == null){
            throw new MiException("La reputación no puede estar vacía.");
        }
        if(valorConsulta == null){
            throw new MiException("El valor de la consulta no puede estar vacía.");
        }
    }
}
