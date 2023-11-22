package com.grupos.salud.repositorios;

import com.grupos.salud.entidades.HistoriaClinica;
import com.grupos.salud.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface HistoriaClinicaRepositorio extends JpaRepository<HistoriaClinica, String> {

    List<HistoriaClinica> findByFecha(Date fecha);

    List<HistoriaClinica> findByProfesionalId(String id);

    Optional<HistoriaClinica> findByPaciente(Paciente paciente);

}
