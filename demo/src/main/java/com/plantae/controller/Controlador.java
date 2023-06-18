/*
 * Projeto da disciplina de Introducao a Tecnologia Java
 * Membros: Bruno Augusto Furquim, Gabriel Ribeiro Ferreira, Karolyne Domiciano Marques, Willian Yoshio Murayama
 */
package com.plantae.controller;

import com.plantae.plants.Plant;
import com.plantae.plants.PlantRepository;
import com.plantae.user.User;
import com.plantae.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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

    @GetMapping("/")
    public String home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (username.equals("anonymousUser")) {
            return "login";
        } else {
            return "index";
        }
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

    @PostMapping("/cadastro")
    public String cadastroUsuario(@ModelAttribute User usuario, Model model) {
        BCryptPasswordEncoder passwordencoder = new BCryptPasswordEncoder();
        usuario.setSenha(passwordencoder.encode(usuario.getPassword()));
        userRepository.save(usuario);
        return "login";
    }

    @GetMapping("/cadastro-plantas")
    public String plantas(Model model) {
        model.addAttribute("plant", new Plant());
        return "plantas";
    }

    @PostMapping("/cadastro-plantas")
    public String plantas(@ModelAttribute Plant plant, Model model, HttpServletRequest request) {
        model.addAttribute("plant", plant);
        boolean[] day = plant.getDaysToWater();
        if (request.getParameter("daysToWater[0]") != null) {
            day[0] = true;
        }
        if (request.getParameter("daysToWater[1]") != null) {
            day[1] = true;
        }
        if (request.getParameter("daysToWater[2]") != null) {
            day[2] = true;
        }
        if (request.getParameter("daysToWater[3]") != null) {
            day[3] = true;
        }
        if (request.getParameter("daysToWater[4]") != null) {
            day[4] = true;
        }
        if (request.getParameter("daysToWater[5]") != null) {
            day[5] = true;
        }
        if (request.getParameter("daysToWater[6]") != null) {
            day[6] = true;
        }
        plant.setDaysToWater(day);
        plantRepository.save(plant);
        return "plantas";
    }
}
