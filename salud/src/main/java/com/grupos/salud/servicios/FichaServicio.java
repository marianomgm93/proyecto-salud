package com.grupos.salud.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grupos.salud.entidades.Ficha;
import com.grupos.salud.entidades.Paciente;
import com.grupos.salud.entidades.Profesional;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.repositorios.FichaRepositorio;

@Service
public class FichaServicio {

    @Autowired
    private FichaRepositorio fichaRepositorio;

    @Transactional
    public void registrar(String id, Paciente paciente, Profesional profesional, String diagnostico, Boolean estado) throws MiException{
        validar(id, diagnostico, estado);
        Ficha ficha = new Ficha();
        ficha.setId(id);
        ficha.setPaciente(paciente);
        ficha.setProfesional(profesional);
        ficha.setDiagnostico(diagnostico);
        ficha.setEstado(estado);

        fichaRepositorio.save(ficha);
    }

    @Transactional
    public void actualizar(String id, Paciente paciente, Profesional profesional, String diagnostico, Boolean estado) throws MiException{
        validar(id, diagnostico, estado);
        Optional<Ficha> respuesta = fichaRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Ficha ficha = respuesta.get();
            ficha.setPaciente(paciente);
            ficha.setProfesional(profesional);
            ficha.setDiagnostico(diagnostico);
            ficha.setEstado(estado);

            fichaRepositorio.save(ficha);
        } else {
            throw new MiException("No existe una ficha con ese id");
        }

    }

    @Transactional
    public void eliminar(String id){
        fichaRepositorio.deleteById(id);
    }

    public Ficha getOne(String id){
        Ficha ficha = fichaRepositorio.getOne(id);
        return ficha;
    }

    public void validar(String id, String diagnostico, Boolean estado) throws MiException{
        if(id == null || id.isEmpty()){
            throw new MiException("Debe ingresar un id");
        }
        if(diagnostico == null || diagnostico.isEmpty()){
            throw new MiException("Debe ingresar un diagnostico");
        }
        if(estado == null){
            throw new MiException("Debe ingresar un estado");
        }
    }

}