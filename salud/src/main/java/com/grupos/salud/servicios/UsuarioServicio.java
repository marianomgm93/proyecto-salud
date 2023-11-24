package com.grupos.salud.servicios;

import com.grupos.salud.entidades.Usuario;
import com.grupos.salud.enumeraciones.Rol;
import com.grupos.salud.excepciones.MiException;
import com.grupos.salud.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
  
    //REGISTRO USUARIO
    @Transactional
    public void registrarUsuario(MultipartFile archivo, String nombreUsuario, String password, String password2,String email) throws MiException {

        validarRegistro(nombreUsuario, password, password2,email);


        Usuario usuario = new Usuario();

        usuario.setNombreUsuario(nombreUsuario);
        

        usuario.setEmail(email);
        
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));

        usuario.setRol(Rol.USER);

        usuarioRepositorio.save(usuario);
    }

    // DAR DE BAJA USUARIO 
    @Transactional
    public void DarBajaUsuarioPorNombreUsuario(String nombreUsuario) throws MiException{

        validarUsuario(nombreUsuario); // VERIFICA QUE NO ESTE VACIO EL PARAMETRO

        Usuario usuario = usuarioRepositorio.buscarPorNombreUsuario(nombreUsuario);

        if (usuario != null) {
            usuario.setEstado(false);
            usuarioRepositorio.save(usuario);
        } else {
            throw new MiException("Usuario con nombre " + nombreUsuario + " no encontrado");
        }

    }
    // MODIFICACION USUARIO 
    @Transactional
    public void modificarUsuario(String nombreUsuario, String nuevoNombre, String nuevaPassword,Rol rol,String email) throws MiException {

        validarModificacion(nombreUsuario,nuevoNombre,nuevaPassword, rol,email);// VERIFICA QUE NO ESTE VACIO EL PARAMETRO

        Usuario usuario = usuarioRepositorio.buscarPorNombreUsuario(nombreUsuario);

        if (usuario != null) {
            usuario.setNombreUsuario(nuevoNombre);
            usuario.setPassword(nuevaPassword);

            usuario.setEmail(email);

            usuario.setRol(rol);
            usuarioRepositorio.save(usuario);
        } else {
            throw new MiException("Usuario con nombre " + nombreUsuario + " no encontrado");
        }

    }
    
    //GUARDAR PERMISOS DE USUARIO
    
    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepositorio.buscarPorNombreUsuario(nombreUsuario);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getNombreUsuario(),usuario.getPassword(),permisos);
            
        } else {
            return null;
        }
     
    }
  

    
    //VALIDACIONES
    private void validarRegistro(String nombre, String password, String password2,String email) throws MiException {


        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }

        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }


    }
    
    private void validarModificacion(String nombre, String password, String password2,Rol rol,String email) throws MiException {


        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }

        if (email.isEmpty() || email == null ) {
            throw new MiException("El email no puede estar vacío,o ser nulo");
        }

        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
         if (rol == null) {

            throw new MiException("el rol no puede ser nulo o estar vacío");

        }

    }

    private void validarUsuario(String nombreUsuario) throws MiException {
         if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new MiException("El nombre a buscar no puede ser nulo o estar vacío");
        }
    
    }
    
}
    


