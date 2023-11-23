package com.grupos.salud.servicios;

import com.grupos.salud.entidades.Imagen;
import com.grupos.salud.repositorios.ImagenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    public Imagen guardarImagen(Imagen imagen){
        return imagenRepositorio.save(imagen);
    }

    public Imagen obtenerImagen(String id){
        return imagenRepositorio.findById(id).orElse(null);
    }

    public void eliminarImagen(String id){
        imagenRepositorio.deleteById(id);
    }
}
