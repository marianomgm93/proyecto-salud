package com.grupos.salud.controladores;

import com.grupos.salud.entidades.Paciente;
import com.grupos.salud.entidades.Profesional;
import com.grupos.salud.entidades.Usuario;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.repositorios.UsuarioRepositorio;
import com.grupos.salud.servicios.PacienteServicio;
import com.grupos.salud.servicios.ProfesionalServicio;
import com.grupos.salud.servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

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

    @RequestMapping("/listado")
    public String listado(Model modelo, @Param("palabraClave") String palabraClave) {
        List<Profesional> profesionales = profesionalServicio.listarProfesional(palabraClave);
        modelo.addAttribute("profesionales", profesionales);
        modelo.addAttribute("palabraClave", palabraClave);
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

    @GetMapping("/turnos")
    public String listaTurnos(Authentication authentication, ModelMap model) throws MiException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Paciente paciente = pacienteServicio.buscarPorEmail(username);
        model.addAttribute("turnos", paciente.getTurnos());
        return "turno_list.html";
    }

    @PostMapping("buscarPorEspecialidad")
    public String buscarProfesionales2(@RequestParam String especialidad, Model model) {
        try {
            if (especialidad == null || especialidad.isEmpty()) {

                return "redirect:/";
            } else {
                List<Profesional> listaProfesionales = profesionalServicio.buscarPorEspecialidad(especialidad);
                List<Profesional> listaProfesionalesEnAlta = new ArrayList();
                for (Profesional pro : listaProfesionales) {
                    if (pro.getEstado()) {
                        listaProfesionalesEnAlta.add(pro);
                    }
                }
                model.addAttribute("profesionales", listaProfesionalesEnAlta);
                return "profesional_list.html";
            }
        } catch (Exception e) {
            return "index.html";
        }
    }

}
