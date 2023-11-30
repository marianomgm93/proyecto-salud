package com.grupos.salud.controladores;

import com.grupos.salud.entidades.Usuario;
import com.grupos.salud.servicios.PacienteServicio;
import com.grupos.salud.servicios.ProfesionalServicio;
import com.grupos.salud.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    

}
