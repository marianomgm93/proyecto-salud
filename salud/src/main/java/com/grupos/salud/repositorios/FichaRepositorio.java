package com.grupos.salud.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupos.salud.entidades.Ficha;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface FichaRepositorio extends JpaRepository<Ficha, String> {

  
}
