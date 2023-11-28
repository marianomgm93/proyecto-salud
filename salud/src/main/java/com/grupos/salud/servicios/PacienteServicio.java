package com.grupos.salud.servicios;


<<<<<<< HEAD
=======
import com.grupos.salud.entidades.HistoriaClinica;
>>>>>>> ee9df1e2379d23b44759d6d8e2fbdf3c40763942
import com.grupos.salud.entidades.Imagen;

import com.grupos.salud.entidades.Paciente;
import com.grupos.salud.entidades.Usuario;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.repositorios.PacienteRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PacienteServicio {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    

    @Autowired
    private ImagenServicio imagenServicio;
<<<<<<< HEAD
    
=======
>>>>>>> ee9df1e2379d23b44759d6d8e2fbdf3c40763942
    
    
    @Autowired
    private HistoriaClinicaServicio historiaClinicaServicio;
    

    // MODIFICAR MULTIPART ARCHIVO, ESTE VIENE DE USUARIO(Propiedad de usuario) // 
    @Transactional
<<<<<<< HEAD
    public void registrar(MultipartFile archivo, String datosContacto, String obraSocial) throws MiException{
=======
    public void registrar(String datosContacto, String obraSocial,Usuario usuario) throws MiException{
>>>>>>> ee9df1e2379d23b44759d6d8e2fbdf3c40763942

        validar(datosContacto, obraSocial);
        Paciente paciente = new Paciente();
        paciente.setDatosContacto(datosContacto);
        paciente.setUsuario(usuario);
        paciente.setObraSocial(obraSocial);
        paciente.setEstado(true);
<<<<<<< HEAD
        Imagen imagen = imagenServicio.guardar(archivo);
        paciente.setImagen(imagen);
=======
        /*
        Imagen imagen = imagenServicio.guardar(archivo);
        paciente.setImagen(imagen);*/
        
        HistoriaClinica historiaClinica = historiaClinicaServicio.crearHistoriaClinica(paciente);
        paciente.setHistoriaClinica(historiaClinica);
>>>>>>> ee9df1e2379d23b44759d6d8e2fbdf3c40763942
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
    
    
    // MODIFICAR MULTIPART ARCHIVO, ESTE VIENE DE USUARIO //actualizar(MultipartFile archivo, String id, String nuevosDatosContacto, String nuevaObraSocial) throws MiException
    @Transactional
<<<<<<< HEAD
    public void actualizar(MultipartFile archivo, String id, String nuevosDatosContacto, String nuevaObraSocial) throws MiException{
=======
    public void actualizar(String id, String nuevosDatosContacto, String nuevaObraSocial) throws MiException{
>>>>>>> ee9df1e2379d23b44759d6d8e2fbdf3c40763942

        validar(nuevosDatosContacto, nuevaObraSocial);
        Optional<Paciente> respuesta = pacienteRepositorio.findById(id);
        if(respuesta.isPresent()){
            Paciente paciente = respuesta.get();
            paciente.setDatosContacto(nuevosDatosContacto);
            paciente.setObraSocial(nuevaObraSocial);
<<<<<<< HEAD
            
=======
            /*
>>>>>>> ee9df1e2379d23b44759d6d8e2fbdf3c40763942
            String idImagen = null;
            
            if (paciente.getImagen() != null) {
                idImagen = paciente.getImagen().getId();
            }
            
            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            
            paciente.setImagen(imagen);

            pacienteRepositorio.save(paciente);
        }
            */
    }
<<<<<<< HEAD
    
=======
    }
        
>>>>>>> ee9df1e2379d23b44759d6d8e2fbdf3c40763942
    public void darDeBaja(String id){
         Optional<Paciente> respuesta = pacienteRepositorio.findById(id);
         if(respuesta.isPresent()){
             Paciente paciente = respuesta.get();
             paciente.setEstado(false);
             pacienteRepositorio.save(paciente);
         }
    }

    public List<Paciente> buscarPorObraSocial(String obraSocial){
        List<Paciente> pacientes = pacienteRepositorio.BuscarPorObraSocial(obraSocial);
        return pacientes;
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
