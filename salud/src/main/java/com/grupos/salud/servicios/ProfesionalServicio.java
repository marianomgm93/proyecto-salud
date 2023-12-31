package com.grupos.salud.servicios;

import com.grupos.salud.entidades.Profesional;
import com.grupos.salud.entidades.Reputacion;
import com.grupos.salud.entidades.Turno;
import com.grupos.salud.entidades.Usuario;
import static com.grupos.salud.enumeraciones.Rol.PROFESIONAL;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.repositorios.ProfesionalRepositorio;
import com.grupos.salud.repositorios.TurnoRepositorio;

import com.grupos.salud.repositorios.UsuarioRepositorio;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
    private ReputacionServicio reputacionServicio;

    @Autowired
    private TurnoRepositorio turnoRepositorio;

    @Autowired
    private UsuarioRepositorio usuariorepositorio;

    @Transactional
    public void registrar(String especialidad, Double valorConsulta, Usuario usuario) throws MiException {

        validar(especialidad, valorConsulta);
        Profesional profesional = new Profesional();
        profesional.setEspecialidad(especialidad);
        profesional.setValorConsulta(valorConsulta);
        Reputacion reputacion = reputacionServicio.crearReputacion(profesional.getId(), 0, 0);
        profesional.setReputacion(reputacion);
        profesional.setUsuario(usuario);
        profesional.usuario.setRol(PROFESIONAL);
        profesional.setEstado(false);
        profesionalRepositorio.save(profesional);
    }

    @Transactional(readOnly = true)
    public List<Profesional> listarProfesionales() {
        List<Profesional> profesionales = profesionalRepositorio.findAll();
        return profesionales;
    }
    
    @Transactional(readOnly = true)
    public List<Profesional> listarProfesional(String palabraClave) {
        if(palabraClave != null){
            return profesionalRepositorio.findAll(palabraClave);
        }
        return profesionalRepositorio.findAll();
    }    
    
    
    public Profesional getOne(String id) {
        Profesional profesional = profesionalRepositorio.getOne(id);
        return profesional;
    }

    @Transactional
    public void actualizar(String id, String especialidad, int calificacion, Double valorConsulta) throws MiException {

        validar(especialidad, valorConsulta);
        Optional<Profesional> respuesta = profesionalRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Profesional profesional = respuesta.get();
            profesional.setEspecialidad(especialidad);
            reputacionServicio.actualizarReputacion(id, calificacion);
            profesional.setReputacion(reputacionServicio.encontrarPorIdProfesional(especialidad));
            profesional.setValorConsulta(valorConsulta);
            profesional.setEstado(true);
            profesionalRepositorio.save(profesional);
        }
    }

    @Transactional
    public void darDeBaja(String id) {
        Optional<Profesional> respuesta = profesionalRepositorio.findById(id);
        if (respuesta.isPresent()) {
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

    public List<Profesional> buscarPorEspecialidad(String especialidad) {
        List<Profesional> profesionales = profesionalRepositorio.findByEspecialidad(especialidad);
        return profesionales;
    }

    private void validar(String especialidad, Double valorConsulta) throws MiException {
        if (especialidad.isEmpty()) {
            throw new MiException("El dato de especialidad no pueden ser nulo o estar vacío.");
        }
        if (valorConsulta == null) {
            throw new MiException("El valor de la consulta no puede estar vacía.");
        }
    }

    @Transactional
    public void crearTurnos(Profesional profesional, Integer horaInicio, Integer horaFin, Date fecha) throws ParseException {
        List<Turno> turnos = new ArrayList<>();

        LocalDateTime horaInicioDate = LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault())
                .withHour(horaInicio)
                .withMinute(0);

        LocalDateTime horaFinDate = LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault())
                .withHour(horaFin)
                .withMinute(0);

        while (horaInicioDate.isBefore(horaFinDate)) {
            Turno turno = new Turno();
            turno.setFechaYHora(Date.from(horaInicioDate.atZone(ZoneId.systemDefault()).toInstant()));
            horaInicioDate = horaInicioDate.plusMinutes(20);

            turno.setEstado("Disponible");
            turno.setProfesional(profesional);
            turnoRepositorio.save(turno);
            turnos.add(turno);
        }

        profesional.setTurnos(turnos);
        profesionalRepositorio.save(profesional);
    }

//DEBE SER TESTEADO
    public Profesional buscarPorEmail(String email) throws MiException {
        Optional<Profesional> respuesta = profesionalRepositorio.buscarPorEmail(email);
        if (respuesta.isPresent()) {
            Profesional profesional = respuesta.get();
            return profesional;
        } else {
            throw new MiException("El email es invalido o se encuentra vacio");
        }

    }

}
