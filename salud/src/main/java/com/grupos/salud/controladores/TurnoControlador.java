package com.grupos.salud.controladores;

import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.servicios.TurnoServicio;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/turnos")
public class TurnoControlador {

    @Autowired
    private TurnoServicio turnoServicio;
    
    @GetMapping("/registrar")
    public String registrar(){ 
        return "turno_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam Date fechaYhora, @RequestParam String estado, ModelMap modelo){
        try {
                turnoServicio.registrar(fechaYhora, estado);    
                modelo.put("exito", "¡El turno fue registrado con exito!");
            } catch (MiException ex) {
                modelo.put("error", ex.getMessage());
        }
        
        return "turno_list.html";
    }
    
   /* @GetMapping("/lista")
    public String lista(ModelMap modelo){
        List<turno> turnos = turnoServicio.listarTurnos();
        modelo.addAttribute("turno", turnos);
    }*/
    
    @GetMapping("/actualizar/{id}")
    public String acutalizar(@PathVariable String id, ModelMap modelo){
        modelo.put("turno", turnoServicio.getOne(id));
        return "turno_actualizar.html";  
    }
    
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable String id, Date fechaYhora, String estado, ModelMap modelo){
        String path="turno_actualizar.html";;
        try {
            turnoServicio.actualizar(id, fechaYhora, estado);
            path = "turno_list.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return path;
    }
   
    @PostMapping("/baja")
    public String baja(@PathVariable String id, ModelMap modelo) throws MiException{
        //modelo.put("turno", turnoServicio.getOne(id));
        
        turnoServicio.darDeBaja(id);
        
        return "turno_list.html"; 
    }
}
