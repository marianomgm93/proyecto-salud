package com.grupos.salud.servicios;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grupos.salud.entidades.Turno;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.repositorios.TurnoRepositorio;

@Service
public class TurnoServicio {

    @Autowired
    TurnoRepositorio turnoRepositorio;

    @Transactional
    public void registrar(Date fechaYHora, String estado) throws MiException {
        validar(fechaYHora);
        Turno turno = new Turno();
        turno.setFechaYHora(fechaYHora);
        turno.setEstado(estado);
        turnoRepositorio.save(turno);
    }

    @Transactional(readOnly = true)
    public Turno getOne(String id) {
    Optional<Turno> turno = turnoRepositorio.findById(id);
    return turno.orElse(null);
    }

    @Transactional
    public void actualizar(String id, Date nuevaFechaYHora, String nuevoEstado) throws MiException {
        validar(nuevaFechaYHora);
        Optional<Turno> respuesta = turnoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Turno turno = respuesta.get();
            turno.setFechaYHora(nuevaFechaYHora);
            turno.setEstado(nuevoEstado);
            turnoRepositorio.save(turno);
        }
    }

    @Transactional
    public void darDeBaja(String id) throws MiException {
        Optional<Turno> respuesta = turnoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Turno turno = respuesta.get();
            turno.setEstado("Cancelado");
            turnoRepositorio.save(turno);
        } else {
            throw new MiException("El turno con id: "+ id + "no existe.");
        }

    }

    private void validar(Date fecha) throws MiException {
        if (fecha == null) {
            throw new MiException("La fecha no puede ser nula.");
        }
    }
}
