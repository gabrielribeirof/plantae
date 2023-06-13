/*
 * Projeto da disciplina de Introducao a Tecnologia Java
 * Membros: Bruno Augusto Furquim, Gabriel Ribeiro Ferreira, Karolyne Domiciano Marques, Willian Yoshio Murayama
 */
package com.plantae.controller;

import com.plantae.user.User;
import com.plantae.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Willian
 */
@Controller
public class Controlador {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new User());
        return "cadastro";
    }

//    @PostMapping("/cadastro")
//    public String cadastroUsuario(@ModelAttribute User user, Model model) {
//        model.addAttribute("usuario", user);
//        return "cadastro";
//    }
    @PostMapping("/cadastro")
    public String cadastroUsuario(@ModelAttribute User usuario, Model model) {
//        Usuario user = new Usuario();
//        user.setNome(usuario.getNome());
//        user.setSenha(usuario.getSenha());
        BCryptPasswordEncoder passwordencoder = new BCryptPasswordEncoder();

        usuario.setSenha(passwordencoder.encode(usuario.getPassword()));
        userRepository.save(usuario);
        model.addAttribute("usuario", usuario);
        return "boasvindas";
    }

    @GetMapping("/plantas")
//    @PreAuthorize("isAuthenticated()")
    public String plantas() {
        return "plantas";
    }
}
