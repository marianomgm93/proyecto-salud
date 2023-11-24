package com.grupos.salud.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupos.salud.entidades.Turno;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, String>{

}
