package com.grupos.salud.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupos.salud.entidades.Usuario;
import com.grupos.salud.repositorios.UsuarioRepositorio;

@Controller
@RequestMapping("/recuperar-contrasena")
public class RecuperarPassword {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping
    public String mostrarFormularioRecuperarPassword() {
        return "recuperarPassword";
    }

    @PostMapping
    public String recuperarPassword(@RequestParam String email) {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        if (usuario != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(usuario.getEmail());
            message.setSubject("Recuperar contraseña");
            message.setText("Su contraseña es: " + usuario.getPassword());
            mailSender.send(message);
        } else {
            System.out.println("No se encontró el usuario");
        }
        return "recuperarPassword";
    }
}
