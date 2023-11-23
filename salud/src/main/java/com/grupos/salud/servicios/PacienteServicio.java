package com.grupos.salud.servicios;


import com.grupos.salud.entidades.Paciente;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.repositorios.PacienteRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PacienteServicio {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    
    
    @Transactional
    public void registrar(String datosContacto, String obraSocial) throws MiException{
        validar(datosContacto, obraSocial);
        Paciente paciente = new Paciente();
        paciente.setDatosContacto(datosContacto);
        paciente.setObraSocial(obraSocial);
        pacienteRepositorio.save(paciente);
    }
    
    @Transactional(readOnly=true)
    public List<Paciente> listarPacientes(){
        List<Paciente> pacientes = pacienteRepositorio.findAll();
        return pacientes;
    }
            
    
    public Paciente getOne(String id){
        Paciente paciente = pacienteRepositorio.getOne(id);
        return paciente;
    } 
    
    @Transactional
    public void actualizar(String id, String nuevosDatosContacto, String nuevaObraSocial) throws MiException{
        validar(nuevosDatosContacto, nuevaObraSocial);
        Optional<Paciente> respuesta = pacienteRepositorio.findById(id);
        if(respuesta.isPresent()){
            Paciente paciente = respuesta.get();
            paciente.setDatosContacto(nuevosDatosContacto);
            paciente.setObraSocial(nuevaObraSocial);
            pacienteRepositorio.save(paciente);
        }
    }
    
    private void validar(String datosContacto,String obraSocial) throws MiException{
        if(datosContacto.isEmpty() || datosContacto == null){
            throw new MiException("Los datos de contacto no pueden ser nulos o estar vacios.");
        }
        if(obraSocial.isEmpty() || obraSocial == null){
            throw new MiException("La obra social no puede ser nula o estar vacía.");
        }
    }
}
