package com.grupos.salud.controladores;

import com.grupos.salud.entidades.Usuario;
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


@Controller
@RequestMapping("/profesional")
public class ProfesionalControlador {

    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

//    @GetMapping("/registrar")
//    public String registrar(){
//        return "registro_profesional.html";
//    }
//    @PostMapping("/registro")
//    public String registro(@RequestParam String nombreUsuario,@RequestParam String email, @RequestParam String password, String password2
//            , @RequestParam String especialidad, @RequestParam Double valorConsulta, MultipartFile archivo, ModelMap modelo){
//        
//        
//        return "";
//    }
    @GetMapping("/registrar")
    public String mostrarFormularioPostulacion(ModelMap model, Authentication authentication) {
        // Verifica si el usuario está autenticado
        if (authentication.isAuthenticated()) {
            // Obtiene los detalles del usuario autenticado
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // Busca al usuario en la base de datos por su nombre de usuario
            Usuario usuario = usuarioServicio.buscarPorEmail(username);

            // Agrega los datos del usuario al modelo para que se muestren en el formulario
        }

        // Puedes agregar otros datos necesarios para el formulario
        return "registro_profesional";
    }

    @PostMapping("/registro")
    public String procesarFormularioPostulacion(@RequestParam Double valorConsulta, @RequestParam String especialidad, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Usuario usuario = usuarioServicio.buscarPorEmail(username);

        try {
            profesionalServicio.registrar(especialidad, valorConsulta, usuario);
            return "index.html";
        } catch (Exception e) {
            return "registro_profesional.html";
        }
        // Aquí procesas la postulación utilizando los datos recibidos
        // Puedes utilizar el servicio ProfesionalServicio para guardar los datos del profesional

        // Ejemplo de uso del servicio:
//        Profesional profesional = new Profesional();
//        profesional.set(nombreUsuario);
//        profesional.setEmail(email);
//        profesional.setDatosContacto(datosContacto);
//        profesional.setPassword(password);
//        profesional.setObraSocial(obraSocial);
//
//        // Puedes guardar el archivo (foto de perfil) aquí si es necesario
//
//        profesionalServicio.guardarProfesional(profesional);
        // Redirige a una página de confirmación o a donde desees
    }

    // Puedes agregar otros métodos y lógica según sea necesario
}
