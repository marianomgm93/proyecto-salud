package com.grupos.salud.controladores;

<<<<<<< HEAD
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
=======
import com.grupos.salud.entidades.Usuario;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.servicios.PacienteServicio;
import com.grupos.salud.servicios.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
>>>>>>> ee9df1e2379d23b44759d6d8e2fbdf3c40763942
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
<<<<<<< HEAD

    @GetMapping("/")
    public String index(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos!");
        }

        return "index.html";
    }

=======
    
    @Autowired
    private PacienteServicio pacienteServicio;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PostMapping("/login")
    public ResponseEntity<String> autenticar(@RequestParam String email, @RequestParam String password) {
        
        if (usuarioServicio.autenticar(email, password)) {
            return ResponseEntity.ok("Inicio de sesión exitoso");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No se pudo iniciar sesión");
        }
    }

>>>>>>> ee9df1e2379d23b44759d6d8e2fbdf3c40763942
    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registro")
<<<<<<< HEAD
    public String registro(@RequestParam String nombreUsuario,@RequestParam String email, @RequestParam String password, String password2, MultipartFile archivo,ModelMap modelo) {
        

        try {
            usuarioServicio.registrarUsuario(archivo, nombreUsuario, password, password2, email);
=======
    public String registro(@RequestParam String nombreUsuario,@RequestParam String email, @RequestParam String password, String password2,@RequestParam String obraSocial,@RequestParam String datosContacto, MultipartFile archivo,ModelMap modelo) {

        try {
            pacienteServicio.registrar( datosContacto, obraSocial,usuarioServicio.registrarUsuario(archivo, nombreUsuario, password, password2, email));
>>>>>>> ee9df1e2379d23b44759d6d8e2fbdf3c40763942
            modelo.put("exito", "Usuario registrado correctamente");
            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombreUsuario);
            modelo.put("email", email);
            return "registro.html";
        }
    }
<<<<<<< HEAD
=======
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/dashboard";
        }

        return "index.html";
    }
>>>>>>> ee9df1e2379d23b44759d6d8e2fbdf3c40763942

}
