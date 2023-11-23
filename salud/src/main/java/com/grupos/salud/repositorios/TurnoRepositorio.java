package com.grupos.salud.repositorios;

import com.grupos.salud.entidades.Turno;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TurnoRepositorio extends JpaRepository<Turno, String>{
    
    @Query("SELECT t FROM Turno t WHERE t.fechaYHora = :fechaYHora")
    public Turno buscarPorFecha(@Param("fechaYHora")Date fechaYHora);
    
}
