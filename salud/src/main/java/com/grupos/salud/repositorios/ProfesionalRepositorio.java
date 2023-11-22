package com.grupos.salud.repositorios;


import com.grupos.salud.entidades.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/*
@Repository
public interface ProfesionalRepositorio extends JpaRepository<Profesional, Long> {

    List<Profesional> findByEspecialidad(String especialidad);

    List<Profesional> findByReputacionGreaterThanEqual(Integer reputacion);

    List<Profesional> findByReputacionLessThanEqual(Integer reputacion);

    List<Profesional> findByPrecioConsultaGreaterThanEqual(Integer precioConsulta);

    List<Profesional> findByPrecioConsultaLessThanEqual(Integer precioConsulta);

    Optional<Profesional> findById(Long id);

    void deleteById(Long id);
}*/