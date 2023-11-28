package com.grupos.salud.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupos.salud.entidades.Ficha;

@Repository
public interface FichaRepositorio extends JpaRepository<Ficha, String> {

}
