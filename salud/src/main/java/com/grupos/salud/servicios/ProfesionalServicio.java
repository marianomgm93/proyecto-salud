package com.grupos.salud.servicios;

import com.grupos.salud.entidades.Profesional;
import com.grupos.salud.entidades.Turno;
import com.grupos.salud.entidades.Usuario;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.repositorios.ProfesionalRepositorio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Transactional
    public void registrar(String especialidad, Double valorConsulta, Usuario usuario) throws MiException {

        validar(especialidad, valorConsulta);
        Profesional profesional = new Profesional();
        profesional.setEspecialidad(especialidad);
        profesional.setValorConsulta(valorConsulta);
        profesional.setUsuario(usuario);
        profesional.setEstado(false);
        profesionalRepositorio.save(profesional);
    }

    @Transactional(readOnly = true)
    public List<Profesional> listarProfesionales() {
        List<Profesional> profesionales = profesionalRepositorio.findAll();
        return profesionales;
    }

    public Profesional getOne(String id) {
        Profesional profesional = profesionalRepositorio.getOne(id);
        return profesional;
    }

    @Transactional
    public void actualizar(String id, String especialidad, Double reputacion, Double valorConsulta) throws MiException {

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
    public void crearTurnos(Profesional profesional, Integer horaInicio, Integer horaFin) throws ParseException {
        List<Turno> turnos = new ArrayList<>();
        Integer minutosATrabajar = (horaFin - horaInicio) * 60;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date horaInicioDate = sdf.parse(String.format("%02d:%02d", horaInicio, 0));
        Date horaFinDate = sdf.parse(String.format("%02d:%02d", horaFin, 0));

        while (horaInicioDate.before(horaFinDate)) {
            Turno turno = new Turno();
            turno.setFechaYHora(horaInicioDate);
            horaInicioDate = new Date(horaInicioDate.getTime() + (20 * 60 * 1000));
            turno.setEstado("Disponible");
            turnos.add(turno);
        }
        profesional.setTurnos(turnos);
        profesionalRepositorio.save(profesional);
    }

    //DEBE SER TESTEADO
    public Profesional buscarPorEmail(String email) throws MiException {
        Optional<Profesional> respuesta = profesionalRepositorio.buscarPorEmail(email);
        if (!respuesta.isPresent()) {
            Profesional profesional = respuesta.get();
            return profesional;
        } else {
            throw new MiException("El email es invalido o se encuentra vacio");
        }

    }
}
