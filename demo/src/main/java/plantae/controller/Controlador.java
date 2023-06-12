/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plantae.controller;

import plantae.users.User;
import plantae.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
////        registry.addViewController("/home").setViewName("index");;
////        registry.addViewController("/").setViewName("index");
////        registry.addViewController("/login").setViewName("login");
////        registry.addViewController("/cadastro").setViewName("cadastro");
//
//    }

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
    @PreAuthorize("isAuthenticated()")
    public String plantas() {
        return "plantas";
    }
}
