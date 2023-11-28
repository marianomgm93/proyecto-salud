package com.grupos.salud.controladores;

import com.grupos.salud.servicios.PacienteServicio;
import com.grupos.salud.servicios.ProfesionalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/turnos")
public class TurnoControlador {

    @Autowired
    private PacienteServicio pacienteServicio;
    @Autowired
    private ProfesionalServicio profesionalServicio;
    
    @GetMapping("/registrar")
    public String registrar(){
        
        return "turno_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(){
        
        return "index.html";
    }
    
    @GetMapping("/disponibles")
    public String disponibles(){
        
        return "turnos_disponibles.html";
    }
    
}
