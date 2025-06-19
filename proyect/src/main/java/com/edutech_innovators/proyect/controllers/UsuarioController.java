package com.edutech_innovators.proyect.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.edutech_innovators.proyect.entities.Usuario;
import com.edutech_innovators.proyect.repository.UsuarioRepository;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuariorepository;
// http://localhost:8080/swagger-ui/index.html#/
// http://localhost:8080/usuarios
    @GetMapping("/usuarios")
    public String verUsuarios(Model model){
        List<Usuario> userlist= (List<Usuario>) usuariorepository.findAll();
        model.addAttribute("userlist", userlist);
        return "usuarios";

    }


}