/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupos.salud.controladores;

import com.grupos.salud.entidades.Ficha;
import com.grupos.salud.entidades.Paciente;
import com.grupos.salud.entidades.Profesional;
import com.grupos.salud.entidades.Usuario;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.servicios.FichaServicio;
import com.grupos.salud.servicios.PacienteServicio;
import com.grupos.salud.servicios.ProfesionalServicio;
import com.grupos.salud.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/ficha")
public class FichaControlador {

    @Autowired
    private FichaServicio fichaservicio;
    @Autowired
    private PacienteServicio pacienteservicio;
    @Autowired
    private ProfesionalServicio profesionalservicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registrar")
    public String crearFicha(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "ficha_form.html";
        } else {
            return "redirect:/";
        }

    }

    @PostMapping("/registro")
    public String creacionFicha(@RequestParam String emailPaciente,
            @RequestParam String diagnostico,
            @RequestParam Boolean estado,
            Authentication authentication,
            ModelMap modelo) {

        try {
            if (authentication != null && authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String username = userDetails.getUsername();
                Usuario usuario = usuarioServicio.buscarPorEmail(username);

                Profesional profesional = profesionalservicio.buscarPorEmail(usuario.getEmail());
                Paciente paciente = pacienteservicio.buscarPorEmail(emailPaciente);
                fichaservicio.registrar(paciente, profesional, diagnostico, estado);
                modelo.put("exito", "¡La ficha fue registrada con éxito!");

            }
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "ficha_form.html";
        }

        return "index.html";
    }

    @GetMapping("/actualizar/{id}")
    public String acutalizar(@PathVariable String id, ModelMap modelo) {
        modelo.put("ficha", fichaservicio.getOne(id));
        return "ficha_actualizar.html";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable String id, @RequestParam Paciente paciente, @RequestParam Profesional profesional, @RequestParam String diagnostico, @RequestParam Boolean estado, ModelMap modelo) {
        String path = "ficha_actualizar.html";
        try {
            fichaservicio.actualizar(id, paciente, profesional, diagnostico, estado);
            path = "ficha_list.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return path;
    }

    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) throws MiException {
        fichaservicio.eliminar(id);
        return "ficha_list.html";
    }

    // MODIFICAR FICHASERVICIO
    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Ficha> fichas = fichaservicio.listarFichas();
        modelo.addAttribute("ficha", fichas);
        return "ficha_list.html";
    }
}
