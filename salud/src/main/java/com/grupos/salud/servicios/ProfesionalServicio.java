package com.grupos.salud.servicios;

import com.grupos.salud.entidades.Profesional;
import com.grupos.salud.entidades.Reputacion;
import com.grupos.salud.entidades.Turno;
import com.grupos.salud.entidades.Usuario;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.repositorios.ProfesionalRepositorio;
import com.grupos.salud.repositorios.TurnoRepositorio;

import com.grupos.salud.repositorios.UsuarioRepositorio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
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
        profesional.setUsuario(usuario);
        profesional.setEstado(false);
        profesionalRepositorio.save(profesional);
        Reputacion reputacion = reputacionServicio.crearReputacion(profesional.getId(), 0, 0);
        profesional.setReputacion(reputacion);
        profesionalRepositorio.save(profesional);
    }

    @Transactional(readOnly = true)
    public List<Profesional> listarProfesionales() {
        List<Profesional> profesionales = profesionalRepositorio.findAll();
        return profesionales;
    }

    @Transactional(readOnly = true)
    public List<Profesional> listarProfesional(String palabraClave) {
        if (palabraClave != null) {
            return profesionalRepositorio.findAll(palabraClave);
        }
        return profesionalRepositorio.findAll();
    }

    public Profesional getOne(String id) {
        Profesional profesional = profesionalRepositorio.getOne(id);
        return profesional;
    }

    @Transactional
    public void actualizar(String id, String especialidad, Reputacion reputacion, Double valorConsulta) throws MiException {

        validar(especialidad, valorConsulta);
        Optional<Profesional> respuesta = profesionalRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Profesional profesional = respuesta.get();
            profesional.setEspecialidad(especialidad);
            profesional.setReputacion(reputacion);
            profesional.setValorConsulta(valorConsulta);
            profesional.setEstado(true);
            profesionalRepositorio.save(profesional);
        }
    }

    @Transactional
    public void actualizarReputacion(String idProfesional, int calificacion) throws MiException {
        Profesional profesional = getOne(idProfesional);
        Reputacion reputacion = reputacionServicio.actualizarReputacion(idProfesional, calificacion);
        if (reputacion != null) {
            actualizar(idProfesional, profesional.getEspecialidad(), reputacion, profesional.getValorConsulta());
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
    public void crearTurnos(Profesional profesional, Integer horaInicio, Integer horaFin, Date fechaDeseada) throws ParseException {
        List<Turno> turnos = new ArrayList<>();

        TimeZone timeZone = TimeZone.getTimeZone("UTC");

        Calendar cal = Calendar.getInstance(timeZone);
        cal.setTime(fechaDeseada);

        cal.set(Calendar.HOUR_OF_DAY, horaInicio);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        Calendar horaFinCal = Calendar.getInstance(timeZone);
        horaFinCal.setTime(fechaDeseada);
        horaFinCal.set(Calendar.HOUR_OF_DAY, horaFin);
        horaFinCal.set(Calendar.MINUTE, 0);

        // Ajustar la lógica para incluir el último intervalo
        while (cal.before(horaFinCal) || cal.equals(horaFinCal)) {
            Calendar proximoTurnoCal = (Calendar) cal.clone();
            proximoTurnoCal.add(Calendar.MINUTE, 20);

            // Ajustar la lógica para manejar el último turno
            if (proximoTurnoCal.before(horaFinCal) || proximoTurnoCal.equals(horaFinCal)) {
                Turno turno = new Turno();

                turno.setFechaYHora(cal.getTime());

                turno.setEstado("Disponible");
                turno.setProfesional(profesional);
                turnoRepositorio.save(turno);
                turnos.add(turno);
            }

            cal.add(Calendar.MINUTE, 20);
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
