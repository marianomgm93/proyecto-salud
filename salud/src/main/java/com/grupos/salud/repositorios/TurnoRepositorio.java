package com.grupos.salud.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupos.salud.entidades.Turno;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, String> {

    @Query("SELECT t FROM Turno t WHERE t.profesional.id = :id ORDER BY t.fechaYHora ASC")
    public List<Turno> ordenarListaTurnos(@Param("id") String id);
}
