package com.grupos.salud.servicios;

import com.grupos.salud.entidades.Profesional;
import com.grupos.salud.entidades.Usuario;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.repositorios.ProfesionalRepositorio;
import com.grupos.salud.repositorios.UsuarioRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfesionalServicio {

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;
    
    @Autowired
    private UsuarioRepositorio usuariorepositorio;
    
    @Transactional
    public void registrar(String especialidad, Double valorConsulta, Usuario usuario) throws MiException{

        validar (especialidad, valorConsulta);
        Profesional profesional = new Profesional();
        profesional.setEspecialidad(especialidad);
        profesional.setValorConsulta(valorConsulta);
        profesional.setUsuario(usuario);
        profesional.setEstado(false);
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

        validar (especialidad, valorConsulta);
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
    
    @Transactional
    public void darDeBaja(String id){
         Optional<Profesional> respuesta = profesionalRepositorio.findById(id);
         if(respuesta.isPresent()){
             Profesional profesional = respuesta.get();
             if (profesional.getEstado() == false) {
                 profesional.setEstado(true);
                 profesionalRepositorio.save(profesional);
             } else {
                 profesional.setEstado(false);
                 profesionalRepositorio.save(profesional);
             }                
             
         }
    }

    public List<Profesional> buscarPorEspecialidad(String especialidad){
        List<Profesional> profesionales = profesionalRepositorio.findByEspecialidad(especialidad);
        return profesionales;
    }
    
    private void validar(String especialidad, Double valorConsulta) throws MiException{
        if(especialidad.isEmpty()){
            throw new MiException("El dato de especialidad no pueden ser nulo o estar vacío.");
        }
        if(valorConsulta == null){
            throw new MiException("El valor de la consulta no puede estar vacía.");
        }
    }
    
    
    
}
