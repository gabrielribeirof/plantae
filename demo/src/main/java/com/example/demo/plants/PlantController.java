package com.example.demo.plants;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Karol
 */
@Controller
public class PlantController {

    @GetMapping("/plantas")
    public String plantas() {
        return "plantas";
    }

    @GetMapping("/cadastro-plantas")
    public String cadastroPlantas(Model model) {
        model.addAttribute("plantas", new Plant());
        return "/cadastro-plantas";
    }

    @PostMapping("/cadastro-plantas")
    public String cadastroPlantasPost(@ModelAttribute Plant plantas, Model model) {
        model.addAttribute("plantas", plantas);
        return "sucesso-cadastro-plantas";
    }
}
