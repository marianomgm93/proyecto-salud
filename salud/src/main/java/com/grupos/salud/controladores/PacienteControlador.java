package com.grupos.salud.controladores;

import com.grupos.salud.entidades.Profesional;
import com.grupos.salud.servicios.PacienteServicio;
import com.grupos.salud.servicios.ProfesionalServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/paciente")
@Controller
public class PacienteControlador {

    @Autowired
    private PacienteServicio pacienteServicio;
    @Autowired
    private ProfesionalServicio profesionalServicio;

    @GetMapping("/solicitarTurno")
    public String solicitarTurno() {
        return "solicitarTurno.html";
    }

    @GetMapping("/cancelarTurno")
    public String cancelarTurno() {
        return "cancelarTurno.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        modelo.addAttribute("profesionales", profesionales);
        return "profesional_list.html";
    }

}
