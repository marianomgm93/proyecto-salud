package com.grupos.salud.repositorios;

import com.grupos.salud.entidades.HistoriaClinica;
import com.grupos.salud.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface HistoriaClinicaRepositorio extends JpaRepository<HistoriaClinica, String> {

    @Query("SELECT h FROM HistoriaClinica h WHERE h.fecha = :fecha")
    List<HistoriaClinica> findByFecha(Date fecha);

    @Query("SELECT h FROM HistoriaClinica h WHERE h.profesional.id = :profesionalId")
    List<HistoriaClinica> findByProfesionalId(String profesionalId);

    @Query("SELECT h FROM HistoriaClinica h WHERE h.paciente = :paciente")
    Optional<HistoriaClinica> findByPaciente(Paciente paciente);

}
