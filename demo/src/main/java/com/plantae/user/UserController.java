/*
 * Projeto da disciplina de Introducao a Tecnologia Java
 * Membros: Bruno Augusto Furquim, Gabriel Ribeiro Ferreira, Karolyne Domiciano Marques, Willian Yoshio Murayama
 */
package com.plantae.user;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Willian
 */
@Controller
@RequestMapping("/")
public class UserController implements UserServices {

    @Autowired
    UserRepository userRepository;

    /**
     * Caminho para a pagina principal
     *
     * @return Redireciona para o cadastro
     */
    @GetMapping("")
    @Override
    public String home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (username.equals("anonymousUser")) {
            return "login";
        } else {
            return "redirect:/plants/";
        }
    }

    /**
     * Caminho para login
     *
     * @return Redireciona para o login
     */
    @GetMapping("login")
    @Override
    public String login() {
        return "login";
    }

    /**
     * Post para cadastro
     *
     * @param user
     * @return Redireciona para o login
     */
    @PostMapping("/cadastro")
    @Override
    public String newUser(@ModelAttribute User user) {
        BCryptPasswordEncoder passwordencoder = new BCryptPasswordEncoder();
        user.setSenha(passwordencoder.encode(user.getPassword()));
        userRepository.save(user);
        return "login";
    }

    /**
     * Caminho para cadastro
     *
     * @param model
     * @return Redireciona para o cadastro
     */
    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new User());
        return "cadastro";
    }

    /**
     *
     * @param id Id do usuario
     * @param bodyUser
     * @return
     */
    @PutMapping("/{id}")
    @Override
    public String updateUser(@PathVariable int id, @RequestBody User bodyUser) {
        Optional<User> userOptional = userRepository.findById(id);
        User newUser = userOptional.get();
        newUser.setNome(bodyUser.getNome());
        newUser.setSenha(bodyUser.getSenha());
        newUser.setUsername(bodyUser.getUsername());
        userRepository.deleteById(id);
        userRepository.save(newUser);
        return "redirect:/";
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @Override
    public String deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.setAuthenticated(false);
        return "redirect:/login";
    }
}
