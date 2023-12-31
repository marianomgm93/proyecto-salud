package com.grupos.salud.servicios;

import com.grupos.salud.entidades.HistoriaClinica;
import com.grupos.salud.entidades.Imagen;

import com.grupos.salud.entidades.Paciente;
import com.grupos.salud.entidades.Usuario;
import com.grupos.salud.enumeraciones.Rol;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.repositorios.PacienteRepositorio;
import com.grupos.salud.repositorios.UsuarioRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PacienteServicio {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private UsuarioRepositorio usuariorepositorio;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    private UsuarioServicio usuarioServicio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    private HistoriaClinicaServicio historiaClinicaServicio;

    // MODIFICAR MULTIPART ARCHIVO, ESTE VIENE DE USUARIO(Propiedad de usuario) // 
    @Transactional
    public void registrar(String datosContacto, String obraSocial, Usuario usuario) throws MiException {

        validar(datosContacto, obraSocial);
        Paciente paciente = new Paciente();
        paciente.setDatosContacto(datosContacto);
        paciente.setUsuario(usuario);
        paciente.setObraSocial(obraSocial);
        paciente.setEstado(true);
        /*
        Imagen imagen = imagenServicio.guardar(archivo);
        paciente.setImagen(imagen);*/

        HistoriaClinica historiaClinica = historiaClinicaServicio.crearHistoriaClinica(paciente);
        paciente.setHistoriaClinica(historiaClinica);
        pacienteRepositorio.save(paciente);
    }

//    @Transactional(readOnly = true)
//    public List<Paciente> listarPacientes() {
//        List<Paciente> pacientes = pacienteRepositorio.findAll();
//        return pacientes;
//    }

    @Transactional(readOnly = true)
    public List<Paciente> listarPacientes() {
        List<Paciente> pacientes = pacienteRepositorio.listar();
        return pacientes;
    }
    public Paciente getOne(String id) {
        Paciente paciente = pacienteRepositorio.getOne(id);
        return paciente;
    }

    // MODIFICAR MULTIPART ARCHIVO, ESTE VIENE DE USUARIO //actualizar(MultipartFile archivo, String id, String nuevosDatosContacto, String nuevaObraSocial) throws MiException
    @Transactional
    public void actualizar(String id, String nuevosDatosContacto, String nuevaObraSocial) throws MiException {

        validar(nuevosDatosContacto, nuevaObraSocial);
        Optional<Paciente> respuesta = pacienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Paciente paciente = respuesta.get();
            paciente.setDatosContacto(nuevosDatosContacto);
            paciente.setObraSocial(nuevaObraSocial);
            /*
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
    }

    @Transactional
    public void darDeBaja(String email) {
        Paciente paciente = pacienteRepositorio.findByUsuarioEmail(email);
        if (paciente != null) {
            paciente.setEstado(false);
            pacienteRepositorio.save(paciente);
    
        }
    }

    public List<Paciente> buscarPorObraSocial(String obraSocial) {
        List<Paciente> pacientes = pacienteRepositorio.BuscarPorObraSocial(obraSocial);
        return pacientes;
    }

    private void validar(String datosContacto, String obraSocial) throws MiException {
        if (datosContacto.isEmpty() || datosContacto == null) {
            throw new MiException("Los datos de contacto no pueden ser nulos o estar vacios.");
        }
        if (obraSocial.isEmpty() || obraSocial == null) {
            throw new MiException("La obra social no puede ser nula o estar vacía.");
        }
    }

    //DEBE SER TESTEADO
    public Paciente buscarPorEmail(String email) throws MiException{
        Optional<Paciente> respuesta = pacienteRepositorio.buscarPorEmail(email);
        if(respuesta.isPresent()){
            Paciente paciente=respuesta.get();
            return paciente;
         }else{
            throw new MiException("El email es invalido o se encuentra vacio");
        }
    }

}
