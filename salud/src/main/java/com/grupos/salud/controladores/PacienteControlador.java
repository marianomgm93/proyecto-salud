package com.grupos.salud.controladores;

import com.grupos.salud.servicios.PacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/paciente")
@Controller
public class PacienteControlador {

    @Autowired
    PacienteServicio pacienteservicio;
    
    
    @GetMapping("/solicitarTurno")
    public String solicitarTurno(){
        return "solicitarTurno.html";
    }
    
    
    @GetMapping("/cancelarTurno")
    public String cancelarTurno(){
        return "cancelarTurno.html";
    }
    
    
}
