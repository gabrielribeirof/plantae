package com.example.demo.user;

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
 * @author Karol
 */
@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;
    
    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new User());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastroUsuario(@ModelAttribute User usuario, Model model) {
        userRepository.save(usuario);
        model.addAttribute("usuario", usuario);
        return "boasvindas";
    }

    @RequestMapping("/id-usuario/{id}")
    public ModelAndView detalhesUsuario(@PathVariable("id") int id) {
        User usuario = userRepository.findById(id).get();
        ModelAndView mv = new ModelAndView("id-usuario");
        mv.addObject("usuario", usuario);
        return mv;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
   
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
}
