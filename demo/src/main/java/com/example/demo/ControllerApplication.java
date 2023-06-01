/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Willian
 */
@Controller
public class ControllerApplication {

    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/cadastro")
    public String cadastroUsuario(@ModelAttribute Usuario usuario, Model model) {
//        Usuario user = new Usuario();
//        user.setNome(usuario.getNome());
//        user.setSenha(usuario.getSenha());
        userRepository.save(usuario);
        model.addAttribute("usuario", usuario);
        return "boasvindas";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Usuario> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
}
