/*
 * Projeto da disciplina de Introducao a Tecnologia Java
 * Membros: Bruno Augusto Furquim, Gabriel Ribeiro Ferreira, Karolyne Domiciano Marques, Willian Yoshio Murayama
 */
package com.plantae.controller;

import com.plantae.plants.Plant;
import com.plantae.plants.PlantRepository;
import com.plantae.user.User;
import com.plantae.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Willian
 */
@Controller
public class Controlador {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlantRepository plantRepository;

    /**
     *
     * @return
     */
    @GetMapping("/")
    public String home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (username.equals("anonymousUser")) {
            return "login";
        } else {
            return "redirect:/cadastro-plantas";
        }
    }

    /**
     *
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new User());
        return "cadastro";
    }

    /**
     *
     * @param usuario
     * @param model
     * @return
     */
    @PostMapping("/cadastro")
    public String cadastroUsuario(@ModelAttribute User usuario, Model model) {
        BCryptPasswordEncoder passwordencoder = new BCryptPasswordEncoder();
        usuario.setSenha(passwordencoder.encode(usuario.getPassword()));
        userRepository.save(usuario);
        return "login";
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/cadastro-plantas")
    public ModelAndView plantas(Model model) {
        ModelAndView mv = new ModelAndView("plantas");
        Iterable<Plant> todasPlantas = plantRepository.findAll();
        mv.addObject("todas_plantas", todasPlantas);
        model.addAttribute("plant", new Plant());
        return mv;
    }

    /**
     *
     * @param plant
     * @param model
     * @return
     */
    @PostMapping("/cadastro-plantas")
    public ModelAndView plantas(@ModelAttribute Plant plant, Model model) {
        model.addAttribute("plant", plant);
        plantRepository.save(plant);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView mv = new ModelAndView("plantas");
        Iterable<Plant> todasPlantas = plantRepository.findAll();
        mv.addObject("todas_plantas", todasPlantas);
        return mv;
    }
}
