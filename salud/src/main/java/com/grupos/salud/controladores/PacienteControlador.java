package com.grupos.salud.controladores;

import com.grupos.salud.servicios.PacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;



@Controller
public class PacienteControlador {

    @Autowired
    PacienteServicio pacienteservicio;
    
    
    
    
}
