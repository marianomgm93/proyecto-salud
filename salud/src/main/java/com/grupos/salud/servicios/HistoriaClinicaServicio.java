package com.grupos.salud.servicios;

import com.grupos.salud.entidades.Ficha;
import com.grupos.salud.entidades.HistoriaClinica;
import com.grupos.salud.entidades.Paciente;
import com.grupos.salud.repositorios.HistoriaClinicaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoriaClinicaServicio {

    @Autowired
    private HistoriaClinicaRepositorio historiaClinicaRepositorio;

    public HistoriaClinica crearHistoriaClinica(Paciente paciente) {

        HistoriaClinica historiaClinica = new HistoriaClinica();
        Date fecha = new Date();
        historiaClinica.setFecha(fecha);
        List<Ficha> fichas = new ArrayList();
        historiaClinica.setFichas(fichas);
        historiaClinica.setPaciente(paciente);
        historiaClinicaRepositorio.save(historiaClinica);
        return historiaClinica;
    }

    public void agregarUnaFicha(String id, Ficha ficha) {

        Optional<HistoriaClinica> respuesta = historiaClinicaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            HistoriaClinica historiaClinica = respuesta.get();

            List<Ficha> fichas = historiaClinica.getFichas();

            fichas.add(ficha);

            historiaClinica.setFichas(fichas);

            historiaClinicaRepositorio.save(historiaClinica);
        }

    }

}
