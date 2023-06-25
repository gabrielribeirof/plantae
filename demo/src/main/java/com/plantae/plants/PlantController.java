/*
 * Projeto da disciplina de Introducao a Tecnologia Java
 * Membros: Bruno Augusto Furquim, Gabriel Ribeiro Ferreira, Karolyne Domiciano Marques, Willian Yoshio Murayama
 */
package com.plantae.plants;

import com.plantae.user.User;
import com.plantae.user.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Karol
 */
@Controller
@RequestMapping("/plants")
public class PlantController implements PlantServices {

    @Autowired
    PlantRepository plantRepository;

    @Autowired
    UserRepository userRepository;

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/cadastro-plantas")
    @Override
    public ModelAndView plantas(Model model) {
        ModelAndView mv = new ModelAndView("plantas");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Plant> todasPlantas = plantRepository.findAll();
        ArrayList<Plant> plantas = new ArrayList<>();
        for (Plant p : todasPlantas) {
            if (p.getUser().getId().equals(user.getId())) {
                plantas.add(p);
            }
        }
        mv.addObject("todas_plantas", plantas);
//      Adicionamos atributos de cadastro de plantas para o modelo para que em POST seja possivel recuperar os atributos de cadastro de planta
        model.addAttribute("plant", new Plant());
        return mv;
    }

    /**
     *
     * @param plant
     * @return
     */
    @PostMapping("/cadastro-plantas")
    @Override
    public String plantas(@ModelAttribute Plant plant) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        if (user != null) {
            plant.setUser(user);
            plantRepository.save(plant);
            return "redirect:/plants/cadastro-plantas";
        }
        // Handle user not found scenario
        return "error";
    }

    @GetMapping("/sunday")
    public ModelAndView getSunday(Model model) {
        ModelAndView modelAndView = new ModelAndView("plantas");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Plant> todasPlantas = plantRepository.findAll();
        ArrayList<Plant> plantas = new ArrayList<>();
        for (Plant p : todasPlantas) {
            if (p.getUser().getId().equals(user.getId())) {
                if (p.getDaysToWater()[0]) {
                    plantas.add(p);
                }
            }
        }
        modelAndView.addObject("todas_plantas", plantas);
//      Adicionamos atributos de cadastro de plantas para o modelo para que em POST seja possivel recuperar os atributos de cadastro de planta
        model.addAttribute("plant", new Plant());
        return modelAndView;
    }

    @GetMapping("/monday")
    public ModelAndView getMonday(Model model) {
        ModelAndView modelAndView = new ModelAndView("plantas");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Plant> todasPlantas = plantRepository.findAll();
        ArrayList<Plant> plantas = new ArrayList<>();
        for (Plant p : todasPlantas) {
            if (p.getUser().getId().equals(user.getId())) {
                if (p.getDaysToWater()[1]) {
                    plantas.add(p);
                }
            }
        }
        modelAndView.addObject("todas_plantas", plantas);
//      Adicionamos atributos de cadastro de plantas para o modelo para que em POST seja possivel recuperar os atributos de cadastro de planta
        model.addAttribute("plant", new Plant());
        return modelAndView;
    }

    @GetMapping("/tuesday")
    public ModelAndView getTuesday(Model model) {
        ModelAndView modelAndView = new ModelAndView("plantas");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Plant> todasPlantas = plantRepository.findAll();
        ArrayList<Plant> plantas = new ArrayList<>();
        for (Plant p : todasPlantas) {
            if (p.getUser().getId().equals(user.getId())) {
                if (p.getDaysToWater()[2]) {
                    plantas.add(p);
                }
            }
        }
        modelAndView.addObject("todas_plantas", plantas);
//      Adicionamos atributos de cadastro de plantas para o modelo para que em POST seja possivel recuperar os atributos de cadastro de planta
        model.addAttribute("plant", new Plant());
        return modelAndView;
    }

    @GetMapping("/wednesday")
    public ModelAndView getWednesday(Model model) {
        ModelAndView modelAndView = new ModelAndView("plantas");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Plant> todasPlantas = plantRepository.findAll();
        ArrayList<Plant> plantas = new ArrayList<>();
        for (Plant p : todasPlantas) {
            if (p.getUser().getId().equals(user.getId())) {
                if (p.getDaysToWater()[3]) {
                    plantas.add(p);
                }
            }
        }
        modelAndView.addObject("todas_plantas", plantas);
//      Adicionamos atributos de cadastro de plantas para o modelo para que em POST seja possivel recuperar os atributos de cadastro de planta
        model.addAttribute("plant", new Plant());
        return modelAndView;
    }

    @GetMapping("/thursday")
    public ModelAndView getThursday(Model model) {
        ModelAndView modelAndView = new ModelAndView("plantas");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Plant> todasPlantas = plantRepository.findAll();
        ArrayList<Plant> plantas = new ArrayList<>();
        for (Plant p : todasPlantas) {
            if (p.getUser().getId().equals(user.getId())) {
                if (p.getDaysToWater()[4]) {
                    plantas.add(p);
                }
            }
        }
        modelAndView.addObject("todas_plantas", plantas);
//      Adicionamos atributos de cadastro de plantas para o modelo para que em POST seja possivel recuperar os atributos de cadastro de planta
        model.addAttribute("plant", new Plant());
        return modelAndView;
    }

    @GetMapping("/friday")
    public ModelAndView getFriday(Model model) {
        ModelAndView modelAndView = new ModelAndView("plantas");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Plant> todasPlantas = plantRepository.findAll();
        ArrayList<Plant> plantas = new ArrayList<>();
        for (Plant p : todasPlantas) {
            if (p.getUser().getId().equals(user.getId())) {
                if (p.getDaysToWater()[5]) {
                    plantas.add(p);
                }
            }
        }
        modelAndView.addObject("todas_plantas", plantas);
//      Adicionamos atributos de cadastro de plantas para o modelo para que em POST seja possivel recuperar os atributos de cadastro de planta
        model.addAttribute("plant", new Plant());
        return modelAndView;
    }

    @GetMapping("/saturday")
    public ModelAndView getSaturday(Model model) {
        ModelAndView modelAndView = new ModelAndView("plantas");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Plant> todasPlantas = plantRepository.findAll();
        ArrayList<Plant> plantas = new ArrayList<>();
        for (Plant p : todasPlantas) {
            if (p.getUser().getId().equals(user.getId())) {
                if (p.getDaysToWater()[6]) {
                    plantas.add(p);
                }
            }
        }
        modelAndView.addObject("todas_plantas", plantas);
//      Adicionamos atributos de cadastro de plantas para o modelo para que em POST seja possivel recuperar os atributos de cadastro de planta
        model.addAttribute("plant", new Plant());
        return modelAndView;
    }

    @PostMapping("water/{id}")
    public String waterPlant(@PathVariable int id) {
        Plant plant = plantRepository.findById(id).get();
        plant.setWatered(true);
        plantRepository.save(plant);
        return "redirect:/plants/cadastro-plantas";
    }

    /**
     *
     * @param id
     * @param bodyPlant
     * @return
     */
    @PutMapping("/{id}")
    @Override
    public String updatePlant(@PathVariable int id, @RequestBody Plant bodyPlant) {
        Optional<Plant> plantOptional = plantRepository.findById(id);
        Plant newPlant = plantOptional.get();
        newPlant.setCategory(bodyPlant.getCategory());
        newPlant.setDaysToWater(bodyPlant.getDaysToWater());
        newPlant.setEspecie(bodyPlant.getEspecie());
        newPlant.setSun(bodyPlant.getSun());
        newPlant.setWater(bodyPlant.getWater());
        plantRepository.save(newPlant);
        return "redirect:/plants/cadastro-plantas";
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @Override
    public String deletePlant(@PathVariable int id) {
        plantRepository.deleteById(id);
        return "redirect:/plants/cadastro-plantas";
    }

    // REPORTS *****************************************************************
    /**
     *
     * @param userid
     * @return
     */
    @GetMapping("/{userid}/reports/week/total-watered")
    @Override
    public ModelAndView totalOfWeek(@PathVariable int userid) {
        ModelAndView mv = new ModelAndView("plantas");
        Iterable<Plant> todasPlantas = plantRepository.findAll();
        mv.addObject("todas_plantas", todasPlantas);
//        model.addAttribute("plant", new Plant());
        int total = 0;
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();

        for (Plant plant : plants) {
            boolean[] daysToWater = plant.getDaysToWater();
            boolean plantWatered = false;
            for (boolean watered : daysToWater) {
                if (watered == true) {
                    total++;
                    plantWatered = true;
                    break;
                }
            }
            if (plantWatered) {
                break;
            }
        }

        mv.addObject("total", total);

        return mv;
    }

    /**
     *
     * @return
     */
    @GetMapping("/{userid}/reports/week/less-watered")
    @Override
    public ModelAndView lessWateredOfWeek() {
        ModelAndView mv = new ModelAndView("plantas");
        Iterable<Plant> todasPlantas = plantRepository.findAll();
        mv.addObject("todas_plantas", todasPlantas);
//        model.addAttribute("plant", new Plant());
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();
        HashMap<Integer, Plant> countWateredPlants = new HashMap<>();

        for (Plant plant : plants) {
            int total = 0;
            boolean[] daysToWater = plant.getDaysToWater();
            for (boolean watered : daysToWater) {
                if (watered == true) {
                    total++;
                }
            }
            countWateredPlants.put(total, plant);
        }

        List<Integer> keys = new ArrayList<>(countWateredPlants.keySet());
        Collections.sort(keys);

        mv.addObject("plant", countWateredPlants.get(keys.get(0)));
        return mv;
    }

    /**
     *
     * @return
     */
    @GetMapping("/{userid}/reports/week/most-watered")
    @Override
    public ModelAndView mostWateredOfWeek() {
        ModelAndView mv = new ModelAndView("plantas");
        Iterable<Plant> todasPlantas = plantRepository.findAll();
        mv.addObject("todas_plantas", todasPlantas);
//        model.addAttribute("plant", new Plant());
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();
        HashMap<Integer, Plant> countWateredPlants = new HashMap<>();

        for (Plant plant : plants) {
            int total = 0;
            boolean[] daysToWater = plant.getDaysToWater();
            for (boolean watered : daysToWater) {
                if (watered == true) {
                    total++;
                }
            }
            countWateredPlants.put(total, plant);
        }

        List<Integer> keys = new ArrayList<>(countWateredPlants.keySet());
        Collections.sort(keys);

        mv.addObject("plant", countWateredPlants.get(keys.get(keys.size())));
        return mv;
    }

    /**
     *
     * @param day
     * @param userid
     * @return
     */
    @GetMapping("/{userid}/reports/{day}/total-watered")
    @Override
    public ModelAndView wateredOfDay(@PathVariable int day, @PathVariable int userid) {
        ModelAndView mv = new ModelAndView("plantas");
        Iterable<Plant> todasPlantas = plantRepository.findAll();
        mv.addObject("todas_plantas", todasPlantas);
//        model.addAttribute("plant", new Plant());
        int total = 0;
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();

        for (Plant plant : plants) {
            if (plant.getDaysToWater()[day] == true) {
                total++;
            }
        }

        mv.addObject("total", total);
        return mv;
    }

    /**
     *
     * @param day
     * @param userid
     * @return
     */
    @GetMapping("/{userid}/reports/{day}/total-not-watered")
    @Override
    public ModelAndView notWateredOfDay(@PathVariable int day, @PathVariable int userid) {
        ModelAndView mv = new ModelAndView("plantas");
        Iterable<Plant> todasPlantas = plantRepository.findAll();
        mv.addObject("todas_plantas", todasPlantas);
//        model.addAttribute("plant", new Plant());
        int total = 0;
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();

        for (Plant plant : plants) {
            if (plant.getDaysToWater()[day] == false) {
                total++;
            }
        }

        mv.addObject("total", total);
        return mv;
    }
}
