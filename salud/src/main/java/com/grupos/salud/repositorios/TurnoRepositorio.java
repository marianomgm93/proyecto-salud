package com.grupos.salud.repositorios;

import com.grupos.salud.entidades.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TurnoRepositorio extends JpaRepository<Turno, String>{
    
    @Query("SELECT e FROM Turno e WHERE e.estado = :estado")
    public Turno buscarPorEstado(@Param("estado")String estado);
    
}
