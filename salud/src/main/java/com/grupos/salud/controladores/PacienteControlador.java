package com.grupos.salud.controladores;

import com.grupos.salud.entidades.Paciente;
import com.grupos.salud.entidades.Profesional;
import com.grupos.salud.entidades.Usuario;
import com.grupos.salud.repositorios.UsuarioRepositorio;
import com.grupos.salud.servicios.PacienteServicio;
import com.grupos.salud.servicios.ProfesionalServicio;
import com.grupos.salud.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/paciente")
@Controller
public class PacienteControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private PacienteServicio pacienteServicio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

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

    @GetMapping("/perfil/{id}")
    public String perfilPaciente(@PathVariable String id, ModelMap model) {
        Paciente paciente = pacienteServicio.getOne(id);
        Usuario usuario = paciente.getUsuario(); // Obtén el Usuario asociado
        model.addAttribute("paciente", paciente);
        model.addAttribute("usuario", usuario);
        return "perfil_paciente.html";
    }

    @PostMapping("/baja/{id}")
    public String bajaPaciente(@PathVariable String id, @RequestParam String email, @RequestParam String password) {

        if (usuarioServicio.autenticar(email, password)) {
            pacienteServicio.darDeBaja(email);
            String idUser = usuarioRepositorio.findUserIdByEmail(email);
            usuarioServicio.cambiarEstado(idUser);

            return "redirect:/logout";
        } else {
            return "perfil_paciente.html";
        }

    }
    
    
    
    

}
