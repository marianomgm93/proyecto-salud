package com.grupos.salud.repositorios;

import com.grupos.salud.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario,String>{
}
