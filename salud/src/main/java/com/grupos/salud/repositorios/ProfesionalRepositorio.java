package com.grupos.salud.repositorios;

import com.grupos.salud.entidades.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfesionalRepositorio extends JpaRepository<Profesional, String> {

    @Query("SELECT p FROM Profesional p WHERE p.especialidad = :especialidad")
    List<Profesional> findByEspecialidad(String especialidad);

    @Query("SELECT p FROM Profesional p WHERE p.reputacion >= :reputacion")
    List<Profesional> findByReputacionGreaterThanEqual(Integer reputacion);

    @Query("SELECT p FROM Profesional p WHERE p.reputacion <= :reputacion")
    List<Profesional> findByReputacionLessThanEqual(Integer reputacion);

    @Query("SELECT p FROM Profesional p WHERE p.precioConsulta >= :precioConsulta")
    List<Profesional> findByPrecioConsultaGreaterThanEqual(Integer precioConsulta);

    @Query("SELECT p FROM Profesional p WHERE p.precioConsulta <= :precioConsulta")
    List<Profesional> findByPrecioConsultaLessThanEqual(Integer precioConsulta);

    @Query("SELECT p FROM Profesional p WHERE p.id = :id")
    Optional<Profesional> findById(String id);

    @Query("DELETE FROM Profesional p WHERE p.id = :id")
    void deleteById(String id);
}
