/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo;

//import model.Plantas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

    @PostMapping("/cadastro")
    public String cadastroUsuario(@ModelAttribute Usuario usuario, Model model) {
        userRepository.save(usuario);
        model.addAttribute("usuario", usuario);
        return "boasvindas";
    }

    @RequestMapping("/id-usuario/{id}")
    public ModelAndView detalhesUsuario(@PathVariable("id") int id) {
        Usuario usuario = userRepository.findById(id).get();
        ModelAndView mv = new ModelAndView("id-usuario");
        mv.addObject("usuario", usuario);
        return mv;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/plantas")
    public String plantas() {
        return "plantas";
    }

    @GetMapping("/cadastro-plantas")
    public String cadastroPlantas(Model model) {
        model.addAttribute("plantas", new Plantas());
        return "/cadastro-plantas";
    }

    @PostMapping("/cadastro-plantas")
    public String cadastroPlantasPost(@ModelAttribute Plantas plantas, Model model) {
        model.addAttribute("plantas", plantas);
        return "sucesso-cadastro-plantas";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Usuario> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
}
