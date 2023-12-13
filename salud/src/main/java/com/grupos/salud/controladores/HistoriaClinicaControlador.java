package com.grupos.salud.controladores;

import com.grupos.salud.entidades.HistoriaClinica;
import com.grupos.salud.entidades.Paciente;
import com.grupos.salud.servicios.HistoriaClinicaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/historia_clinica")
public class HistoriaClinicaControlador {
    
    @Autowired
    private HistoriaClinicaServicio historiaClinicaServicio;
    
    
    @GetMapping("/registrar")
    public String registrar(){
        return "historia_clinica_form.html";
    }
    
    @PostMapping("/registro")
    public HistoriaClinica crearHistoriaClinica(@RequestBody Paciente paciente) {
        return historiaClinicaServicio.crearHistoriaClinica(paciente);
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<HistoriaClinica> historiasClinicas = historiaClinicaServicio.listarHistoriasClinicas();
        modelo.addAttribute("historiasClinicas",historiasClinicas);
        return "historias_clinicas_list.html";
    }
    
    
}