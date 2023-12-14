package com.grupos.salud.controladores;

import com.grupos.salud.entidades.Paciente;
import com.grupos.salud.entidades.Profesional;
import com.grupos.salud.entidades.Turno;
import com.grupos.salud.entidades.Usuario;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.repositorios.PacienteRepositorio;
import com.grupos.salud.repositorios.TurnoRepositorio;
import com.grupos.salud.servicios.PacienteServicio;
import com.grupos.salud.servicios.ProfesionalServicio;
import com.grupos.salud.servicios.TurnoServicio;
import com.grupos.salud.servicios.UsuarioServicio;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
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
    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private PacienteServicio pacienteServicio;
    @Autowired
    private TurnoRepositorio turnoRepositorio;
    @Autowired
    private PacienteRepositorio pacienteRepositorio;

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

    @GetMapping("/agendar/{id}")
    public String mostrarCalendario(ModelMap model, @PathVariable String id) {
        Profesional profesional = profesionalServicio.getOne(id);
        model.addAttribute("profesionalId", profesional.getId());
        model.addAttribute("turnos", profesional.getTurnos());
        return "calendario.html";
    }

    @GetMapping("/eventos/{id}")
    @ResponseBody
    public List<FullCalendarEventDTO> obtenerEventosProfesional(@PathVariable String id) {
        Profesional profesional = profesionalServicio.getOne(id);
        List<Turno> turnos = profesional.getTurnos();
        List<FullCalendarEventDTO> eventos = new ArrayList<>();

        for (Turno turno : turnos) {
            if (turno.getEstado().equals("Disponible")) {
                FullCalendarEventDTO eventoDTO = new FullCalendarEventDTO();
                eventoDTO.setTitle("Turno Disponible");
                eventoDTO.setId(turno.getId());
                eventoDTO.setStart(turno.getFechaYHora());
                eventoDTO.setAllDay(false);

                eventos.add(eventoDTO);
            }
        }

        return eventos;
    }

    @GetMapping("/confirmar-turno/{id}")
    public String confirmarTurno(@PathVariable String id, ModelMap model, Authentication authentication) {

        try {
            if (authentication != null && authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String username = userDetails.getUsername();
                Usuario usuario = usuarioServicio.buscarPorEmail(username);
                Paciente paciente = pacienteServicio.buscarPorEmail(usuario.getEmail());
                Turno turno = turnoServicio.getOne(id);
                Profesional profesional = turno.getProfesional();

                model.addAttribute("paciente", paciente);
                model.addAttribute("profesional", profesional);
                model.addAttribute("usuario", usuario);
                model.addAttribute("turno", turno);
            }
        } catch (Exception e) {
        }
        return "confirmar_turno.html";
    }

    @PostMapping("/confirmacion/{id}")
    public String confirmacion(@PathVariable String id, Authentication authentication, ModelMap modelo) {

        try {
            if (authentication != null && authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String username = userDetails.getUsername();
                Usuario usuario = usuarioServicio.buscarPorEmail(username);
                Paciente paciente = pacienteServicio.buscarPorEmail(usuario.getEmail());
                Turno turno = turnoServicio.getOne(id);
                turno.setPaciente(paciente);
                turno.setEstado("Reservado");
                List<Turno> turnos = paciente.getTurnos();
                turnos.add(turno);
                paciente.setTurnos(turnos);
                pacienteRepositorio.save(paciente);
                turnoRepositorio.save(turno);
                return "index.html";
            } else {
                return "confirmar_turno.html";
            }

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "confirmar_turno.html";
        }

    }
}
