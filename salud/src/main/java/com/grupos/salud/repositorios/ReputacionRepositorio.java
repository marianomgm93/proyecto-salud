/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupos.salud.repositorios;

import com.grupos.salud.entidades.Profesional;
import com.grupos.salud.entidades.Reputacion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mvale
 */
@Repository
public interface ReputacionRepositorio extends JpaRepository<Reputacion, String>{
    
      @Query("SELECT r FROM Reputacion r WHERE r.profesional = :profesional")
    public Optional<Reputacion> findByProfesional(@Param("profesional") Profesional profesional);
    
}
