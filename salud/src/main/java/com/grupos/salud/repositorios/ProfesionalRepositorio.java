package com.grupos.salud.repositorios;
/*
    SE DEBEN ARREGLAR LAS CONSULTAS QUERY CON MAYOR Y MENOR
*/

import com.grupos.salud.entidades.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ProfesionalRepositorio extends JpaRepository<Profesional, String> {
    
    @Query("SELECT p FROM Profesional p WHERE p.especialidad = :especialidad")
    public List<Profesional> findByEspecialidad(@Param("especialidad") String especialidad);
    /*
    
    @Query("SELECT p FROM Profesional p WHERE p.reputacion => :reputacion")
    public List<Profesional> findByReputacionGreaterThanEqual(@Param("reputacion") Double reputacion);
    
    @Query("SELECT p FROM Profesional p WHERE p.reputacion =< :reputacion")
    public List<Profesional> findByReputacionLessThanEqual(@Param("reputacion") Double reputacion);
    
    @Query("SELECT p FROM Profesional p WHERE p.valorConsulta => :valorConsulta")
    public List<Profesional> findByValorConsultaGreaterThanEqual(@Param("valorConsulta") Double valorConsulta);
    
    @Query("SELECT p FROM Profesional p WHERE p.valorConsulta =< :valorConsulta")
    public List<Profesional> findByValorConsultaLessThanEqual(@Param("valorConsulta") Double valorConsulta);
*/

}

