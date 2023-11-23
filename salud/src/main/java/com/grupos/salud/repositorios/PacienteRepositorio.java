package com.grupos.salud.repositorios;

import com.grupos.salud.entidades.Paciente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente,String>  {
    
    @Query("SELECT p FROM Paciente p WHERE p.obraSocial = :obraSocial")
    public List<Paciente> BuscarPorObraSocial(@Param("obraSocial")String obraSocial);

}
