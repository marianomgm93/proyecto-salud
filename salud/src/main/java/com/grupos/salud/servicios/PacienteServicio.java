package com.grupos.salud.servicios;


import com.grupos.salud.entidades.Paciente;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.repositorios.PacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PacienteServicio {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    
        /* private String datosContacto;
           private String obraSocial;
    
           @OneToOne
            private Imagen imagen;
        */
    
    @Transactional
    public void registrar(String datosContacto, String obraSocial) throws MiException{
        validar(datosContacto, obraSocial);
        Paciente paciente = new Paciente();
        paciente.setDatosContacto(datosContacto);
        paciente.setObraSocial(obraSocial);
        pacienteRepositorio.save(paciente);
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
