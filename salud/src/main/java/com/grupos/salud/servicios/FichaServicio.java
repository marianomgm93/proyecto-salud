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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FichaServicio {

    @Autowired
    private FichaRepositorio fichaRepositorio;

    @Transactional
    public void registrar(Paciente paciente, Profesional profesional, String diagnostico, Boolean estado) throws MiException {
        validar(diagnostico, estado);
        Ficha ficha = new Ficha();

        ficha.setPaciente(paciente);
        ficha.setProfesional(profesional);
        ficha.setDiagnostico(diagnostico);
        ficha.setEstado(estado);
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime nuevaFechaHora = localDateTime.minusHours(3);
        ZonedDateTime zonedDateTime = nuevaFechaHora.atZone(ZoneId.systemDefault());
        Date date = Date.from(zonedDateTime.toInstant());
        ficha.setFecha(date);
        fichaRepositorio.save(ficha);
    }

    @Transactional
    public void actualizar(String id, Paciente paciente, Profesional profesional, String diagnostico, Boolean estado) throws MiException {
        validar(diagnostico, estado);
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
    public void eliminar(String id) {
        Optional<Ficha> respuesta = fichaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Ficha ficha = respuesta.get();
            ficha.setEstado(false);
            fichaRepositorio.save(ficha);
        }
    }

    public Ficha getOne(String id) {
        Ficha ficha = fichaRepositorio.getOne(id);
        return ficha;
    }

    public void validar(String diagnostico, Boolean estado) throws MiException {

        if (diagnostico == null || diagnostico.isEmpty()) {
            throw new MiException("Debe ingresar un diagnostico");
        }
        if (estado == null) {
            throw new MiException("Debe ingresar un estado");
        }
    }

    public List<Ficha> listarFichas() {
        List<Ficha> fichas = new ArrayList();
        fichas = fichaRepositorio.findAll();
        return fichas;
    }

    public List<Paciente> listarPacientesPorFichaConProfesional(String id) {
        List<Paciente> listaPacientes = new ArrayList<>();
        Set<String> idPacientesAgregados = new HashSet<>();

        List<Ficha> listaFichas = fichaRepositorio.findAll();
        for (Ficha ficha : listaFichas) {
            if (ficha.getProfesional().getId().equals(id)) {
                Paciente paciente = ficha.getPaciente();
                if (idPacientesAgregados.add(paciente.getId())) {
                    listaPacientes.add(paciente);
                }
            }
        }

        return listaPacientes;
    }

}
