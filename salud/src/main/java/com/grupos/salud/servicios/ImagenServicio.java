
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.*/
    

package com.grupos.salud.servicios;

import com.grupos.salud.entidades.Imagen;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.repositorios.ImagenRepositorio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    @Transactional
<<<<<<< HEAD


=======
>>>>>>> 04958f42a7b230f44c4f524815305a0305d7d338
    public Imagen guardar(MultipartFile archivo) throws MiException{

        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();

                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    @Transactional
    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiException{

        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();

                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);

                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }

                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenRepositorio.save(imagen);
            } catch (Exception e){
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
    @Transactional
    public void eliminar(String idImagen) throws MiException{
        try {
            if (idImagen != null) {
                imagenRepositorio.deleteById(idImagen);
            } else {
                throw new MiException("El id de la imagen no puede ser nulo");
            }
        } catch (Exception e) {
            throw new MiException("Error al eliminar la imagen: " + e.getMessage());
        }
    }
<<<<<<< HEAD
    

=======

    
>>>>>>> 04958f42a7b230f44c4f524815305a0305d7d338
    @Transactional
    public Imagen guardarImagen(Imagen imagen){
            return imagenRepositorio.save(imagen);

        }

<<<<<<< HEAD
}
=======
    }
>>>>>>> 04958f42a7b230f44c4f524815305a0305d7d338
