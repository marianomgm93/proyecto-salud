package com.grupos.salud.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupos.salud.entidades.Imagen;

public interface ImagenRepositorio extends JpaRepository<Imagen, String>{
    
}
