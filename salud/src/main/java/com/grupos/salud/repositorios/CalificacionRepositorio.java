/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupos.salud.repositorios;

import com.grupos.salud.entidades.Calificacion;
import com.grupos.salud.entidades.Profesional;
import com.grupos.salud.entidades.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author mvale
 */
public interface CalificacionRepositorio extends JpaRepository<Calificacion, String>{
    
    @Query("SELECT c FROM Calificacion c WHERE c.profesional = :profesional")
    public List<Calificacion> findByProfesional(@Param("profesional") Profesional profesional);
    
    @Query("SELECT c FROM Calificacion c WHERE c.profesional = :profesional AND c.usuario = :usuario")
    public Optional<Calificacion> findByProfesionalAndUsuario(@Param("profesional") Profesional profesional, @Param("usuario") Usuario usuario);
    
}
