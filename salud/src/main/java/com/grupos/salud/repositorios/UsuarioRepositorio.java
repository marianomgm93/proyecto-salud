package com.grupos.salud.repositorios;

import com.grupos.salud.entidades.Usuario;

import com.grupos.salud.enumeraciones.Rol;
import com.grupos.salud.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
    public Usuario buscarPorNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.rol = :rol")
    public Usuario buscarPorRol(@Param("rol") Rol rol);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario buscarPorEmail(@Param("email") String email);

    @Query("SELECT u.id FROM Usuario u WHERE u.email = :email")
    String findUserIdByEmail(@Param("email") String correo);
}
