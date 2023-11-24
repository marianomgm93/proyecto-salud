package com.grupos.salud.repositorios;

import com.grupos.salud.entidades.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HistoriaClinicaRepositorio extends JpaRepository<HistoriaClinica, String> {

    @Query("SELECT h FROM HistoriaClinica h WHERE h.fecha = :fecha")
    public List<HistoriaClinica> findByFecha(@Param("fecha") Date fecha);


}
