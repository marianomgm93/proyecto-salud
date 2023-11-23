package com.grupos.salud.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.grupos.salud.entidades.Imagen;

@Repository
public class interface ImagenRepositorio extends JpaRepository<Imagen, String> {

}