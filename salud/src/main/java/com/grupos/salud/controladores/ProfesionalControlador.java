package com.grupos.salud.controladores;

import com.grupos.salud.entidades.Paciente;
import com.grupos.salud.entidades.Profesional;
import com.grupos.salud.entidades.Turno;
import com.grupos.salud.entidades.Usuario;
import com.grupos.salud.enumeraciones.Rol;
import com.grupos.salud.servicios.FichaServicio;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.servicios.PacienteServicio;
import com.grupos.salud.servicios.ProfesionalServicio;
import com.grupos.salud.servicios.ReputacionServicio;
import com.grupos.salud.servicios.TurnoServicio;
import com.grupos.salud.servicios.UsuarioServicio;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/profesional")
public class ProfesionalControlador {

    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private PacienteServicio pacienteServicio;

    @Autowired
    private FichaServicio fichaServicio;

    private ReputacionServicio reputacionServicio;
    @Autowired
    private TurnoServicio turnoServicio;


    @GetMapping("/registrar")
    public String mostrarFormularioPostulacion() {

        return "registro_profesional";
    }

    @PostMapping("/registro")
    public String procesarFormularioPostulacion(@RequestParam Double valorConsulta, @RequestParam String especialidad,
            Authentication authentication, @RequestParam(required = false) String nombreUsuario, @RequestParam(required = false) String email,
            @RequestParam(required = false) String password, String password2, @RequestParam(required = false) String obraSocial,
            @RequestParam(required = false) String datosContacto, MultipartFile archivo, ModelMap modelo) {

        try {
            if (authentication != null && authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String username = userDetails.getUsername();
                Usuario usuario = usuarioServicio.buscarPorEmail(username);
                profesionalServicio.registrar(especialidad, valorConsulta, usuario);
            } else {
                Usuario usuarioRegistrado = usuarioServicio.buscarPorEmail(email);
                if (usuarioRegistrado != null) {
                    profesionalServicio.registrar(especialidad, valorConsulta, usuarioRegistrado);
                } else {
                    pacienteServicio.registrar(datosContacto, obraSocial, usuarioServicio.registrarUsuario(archivo, nombreUsuario, password, password2, email));
                    Usuario usuarioNuevo = usuarioServicio.buscarPorEmail(email);
                    profesionalServicio.registrar(especialidad, valorConsulta, usuarioNuevo);
                }
            }

            return "index.html";
        } catch (Exception e) {
            return "registro_profesional.html";
        }
    }

    @GetMapping("/detalle/{id}")
    public String obtenerProfesional(@PathVariable String id, ModelMap model) {
        Profesional profesional = profesionalServicio.getOne(id);
        Usuario usuario = profesional.getUsuario();
        model.addAttribute("profesional", profesional);
        model.addAttribute("usuario", usuario);
        return "detalleProfesional.html";
    }

    @PostMapping("/calificacion/{id}")
    public String guardarCalificacion(@RequestParam("reputacion") int reputacion, @PathVariable String id) throws MiException {
        profesionalServicio.actualizarReputacion(id, reputacion);
        return "redirect:/profesional/detalle/" + id;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_PROFESIONAL')")
    @GetMapping("/turnos1")
    public String crearListaTurnos(HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("PROFESIONAL")) {
            return "formulario_horarios.html";
        } else {
            return "index.html";
        }

    }

    @PostMapping("/turnos")
    public String listaTurnos(@RequestParam Integer horaInicio, @RequestParam Integer horaFin,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDeseada,
            Authentication authentication) {
        try {
            if (authentication != null && authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String username = userDetails.getUsername();
                Usuario usuario = usuarioServicio.buscarPorEmail(username);
                Profesional profesional = profesionalServicio.buscarPorEmail(username);
                profesionalServicio.crearTurnos(profesional, horaInicio, horaFin, fechaDeseada);
            }
            return "formulario_horarios.html";
        } catch (Exception e) {
            return "index.html";
        }
    }

    @GetMapping("/mostrarPacientes")
     public String mostrarPaciente(Authentication authentication,ModelMap modelo) {
         
         
         try{
             if (authentication != null && authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String username = userDetails.getUsername();
                Usuario usuario = usuarioServicio.buscarPorEmail(username);
                Profesional profesional = profesionalServicio.buscarPorEmail(username);
                
                
                List<Paciente> pacientes = fichaServicio.listarPacientesPorFichaConProfesional(profesional.getId());
               modelo.addAttribute("pacientes", pacientes);
                return "mostrarPacientes.html";
            } 
             
         } catch (Exception e) {
            return "inicio.html";
        }
      
        
         return "mostrarPacientes.html";
       
     }


    @GetMapping("/misturnos")
    public String listaTurnos(Authentication authentication, ModelMap model) throws MiException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Profesional profesional = profesionalServicio.buscarPorEmail(username);
        List<Turno> turnosOrdenados = turnoServicio.ordenarTurnos(profesional.getId());
        model.addAttribute("turnos", turnosOrdenados);
        return "turnos_profesional_list.html";

    }

    @GetMapping("/cancelar_turno/{id}")
    public String cancelarTurno(@PathVariable String id) {
        try {
            turnoServicio.darDeBaja(id);
            return "redirect:/profesional/misturnos";
        } catch (Exception e) {
            return "redirect:/profesional/misturnos";
        }
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL','ROLE_ADMIN')")
    @GetMapping("/modificar-perfil")
    public String modificarPerfil(Authentication authentication, ModelMap modelo) throws MiException{
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Usuario usuario = usuarioServicio.buscarPorEmail(username);
        Profesional profesional = profesionalServicio.buscarPorEmail(username);
        Paciente paciente = pacienteServicio.buscarPorEmail(username);
        
        modelo.put("profesional", profesional);
        modelo.put("usuario", usuario);
        modelo.put("paciente", paciente);
        
        return "profesional_editarPerfil.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL','ROLE_ADMIN')")
    @PostMapping("/modificar-perfil/{id}")
    public String modificarPerfil(@PathVariable String id, @RequestParam Double valorConsulta, @RequestParam String especialidad,
            Authentication authentication, @RequestParam(required = false) String nombreUsuario, @RequestParam(required = false) String email,
            @RequestParam(required = false) String password, @RequestParam(required = false) String datosContacto, 
            @RequestParam(required = false) MultipartFile archivo, ModelMap modelo) throws MiException{
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Paciente paciente = pacienteServicio.buscarPorEmail(username);
        
        Profesional profesional = profesionalServicio.getOne(id);
        String idUsuario = profesional.getUsuario().getId();
        String antiguoNombreUsuario = profesional.getUsuario().getNombreUsuario();
        
        
        usuarioServicio.modificarUsuario(archivo, idUsuario, antiguoNombreUsuario, nombreUsuario, password, Rol.PROFESIONAL, email);
        profesionalServicio.actualizar(id, especialidad, profesional.getReputacion(),valorConsulta);
        pacienteServicio.actualizar(paciente.getId(), datosContacto, paciente.getObraSocial());
        
        return "redirect:/logout";
    }

}

