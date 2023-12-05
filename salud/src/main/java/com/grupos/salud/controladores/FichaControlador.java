/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.grupos.salud.controladores;

import com.grupos.salud.entidades.Paciente;
import com.grupos.salud.entidades.Profesional;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.servicios.FichaServicio;
import com.grupos.salud.servicios.PacienteServicio;
import com.grupos.salud.servicios.ProfesionalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ficha")
public class FichaControlador {

    @Autowired
    private FichaServicio fichaservicio;
    @Autowired 
    private PacienteServicio pacienteservicio;
    @Autowired 
    private ProfesionalServicio profesionalservicio;
    
    @GetMapping("/registrar")
    public String crearFicha() {
        return "ficha_form.html";
    }
    
    @PostMapping("/registro")
    public String creacionFicha(@RequestParam String emailPaciente,@RequestParam String emailProfesional,@RequestParam String diagnostico,@RequestParam Boolean estado,ModelMap modelo) {
    
        try {
            Profesional profesional=profesionalservicio.buscarPorEmail(emailProfesional);
            
            Paciente paciente=pacienteservicio.buscarPorEmail(emailPaciente);
            
            fichaservicio.registrar(paciente, profesional, diagnostico, estado);
            
              modelo.put("exito", "¡La ficha fue registrada con exito!");
            } catch (MiException ex) {
                modelo.put("error", ex.getMessage());
        }
        
        return "ficha_form.html";
    }

  @GetMapping("/actualizar/{id}")
    public String acutalizar(@PathVariable String id, ModelMap modelo){
        modelo.put("ficha", fichaservicio.getOne(id));
        return "ficha_actualizar.html";  
    }
    
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable String id, @RequestParam Paciente paciente,@RequestParam Profesional profesional,@RequestParam String diagnostico,@RequestParam Boolean estado,ModelMap modelo){
        String path="ficha_actualizar.html";
        try {
            fichaservicio.actualizar(id, paciente,profesional,diagnostico, estado);
            path = "ficha_list.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return path;
    }
   
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) throws MiException{
        fichaservicio.eliminar(id);
        return "ficha_list.html"; 
    }
    
    // MODIFICAR FICHASERVICIO
    //   @GetMapping("/listar")
//    public String listar(ModelMap modelo){
//        List<Ficha> fichas = fichaServicio.listarFichas();
//        modelo.addAttribute("ficha", ficha);
//        return "ficha_list.html"; 
//    }
    

    
    
}

