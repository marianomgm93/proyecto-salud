package com.grupos.salud.controladores;

import com.grupos.salud.entidades.Ficha;
import com.grupos.salud.entidades.HistoriaClinica;
import com.grupos.salud.entidades.Paciente;
import com.grupos.salud.repositorios.FichaRepositorio;
import com.grupos.salud.servicios.FichaServicio;
import com.grupos.salud.servicios.HistoriaClinicaServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/historia_clinica")
public class HistoriaClinicaControlador {
    
    @Autowired
    private HistoriaClinicaServicio historiaClinicaServicio;
    
    @Autowired
    private FichaRepositorio fichaRepositorio;
    
    
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
    
    /// NO ES EL MISMO FORM DE  HISTORIA CLINICA QUE EL DE ARRIBA
    @GetMapping("/mostrarHistoriaClinica/{id}")
    public String mostrarHistoria(@PathVariable String id,Model modelo){
        List<Ficha> listaFichas = fichaRepositorio.findAll();
        List<Ficha> listaFichasAMostrar = new ArrayList();
        for (Ficha ficha : listaFichas) {
            if(ficha.getPaciente().getId().equals(id)){
                listaFichasAMostrar.add(ficha);
            }
        }
        modelo.addAttribute("fichas", listaFichasAMostrar);
        return "historia_clinica_list";
    }
    
}
