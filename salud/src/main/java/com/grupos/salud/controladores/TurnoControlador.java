package com.grupos.salud.controladores;

import com.grupos.salud.entidades.Turno;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.repositorios.TurnoRepositorio;
import com.grupos.salud.servicios.TurnoServicio;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/turnos")
public class TurnoControlador {

    @Autowired
    private TurnoServicio turnoServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "turno_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam Date fechaYhora, @RequestParam String estado, ModelMap modelo) {
        try {
            turnoServicio.registrar(fechaYhora, estado);
            modelo.put("exito", "¡El turno fue registrado con exito!");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }

        return "turno_list.html";
    }

//   @GetMapping("/lista")
//    public String lista(ModelMap modelo){
//        List<turno> turnos = turnoServicio.listarTurnos();
//        modelo.addAttribute("turno", turnos);
//        return "turno_list.html"; 
//    }
    @GetMapping("/actualizar/{id}")
    public String acutalizar(@PathVariable String id, ModelMap modelo) {
        modelo.put("turno", turnoServicio.getOne(id));
        return "turno_actualizar.html";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable String id, Date fechaYhora, String estado, ModelMap modelo) {
        String path = "turno_actualizar.html";
        try {
            turnoServicio.actualizar(id, fechaYhora, estado);
            path = "turno_list.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return path;
    }

    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) throws MiException {
        turnoServicio.darDeBaja(id);
        return "turno_list.html";
    }

    @Autowired
    private TurnoRepositorio turnoRepositorio; // Asume que tienes un repositorio para los turnos

    @GetMapping("/calendario")
    public String mostrarCalendario(Model model) {
        List<Turno> turnos = turnoRepositorio.findAll();
        model.addAttribute("turnos", turnos);
        return "calendario";
    }

@GetMapping("/eventos")
@ResponseBody
public List<FullCalendarEventDTO> obtenerEventos() {
    List<Turno> turnos = turnoRepositorio.findAll();
    List<FullCalendarEventDTO> eventos = new ArrayList<>();

    for (Turno turno : turnos) {
        FullCalendarEventDTO eventoDTO = new FullCalendarEventDTO();
        eventoDTO.setTitle("Turno Programado");
        eventoDTO.setStart(turno.getFechaYHora());
        eventoDTO.setAllDay(false);

        eventos.add(eventoDTO);
    }

    return eventos;
}



}
