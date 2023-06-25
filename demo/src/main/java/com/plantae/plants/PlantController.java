/*
 * Projeto da disciplina de Introducao a Tecnologia Java
 * Membros: Bruno Augusto Furquim, Gabriel Ribeiro Ferreira, Karolyne Domiciano Marques, Willian Yoshio Murayama
 */
package com.plantae.plants;

import com.plantae.user.User;
import com.plantae.user.UserRepository;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

@Controller
@RequestMapping("/plants")
public class PlantController implements PlantServices {

    @Autowired
    PlantRepository plantRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Caminho de cadastro de plantas
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
        model.addAttribute("plant", new Plant());
        return mv;
    }

    /**
     * Post de cadastro de plantas
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

    /**
     * Caminho para modificacao de plantas
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/modify/{id}")
    public ModelAndView modifyPlants(Model model, @PathVariable int id) {
        ModelAndView mv = new ModelAndView("modification");
        Plant plant = plantRepository.findById(id).get();
        model.addAttribute("plant", plant);
        model.addAttribute("id", id);
        return mv;
    }

    /**
     * Post de modificacao de plantas
     *
     * @param plant
     * @param id
     * @return
     */
    @PostMapping("/modify/{id}")
    public String modifiyPlantsPost(@ModelAttribute Plant plant, @PathVariable int id) {
        Plant planta = plantRepository.findById(id).get();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        planta.setCategory(plant.getCategory());
        planta.setEspecie(plant.getEspecie());
        planta.setDaysToWater(plant.getDaysToWater());
        planta.setSun(plant.getSun());
        if (user != null) {
            planta.setUser(user);
        }
        planta.setWater(plant.getWater());
        planta.setWatered(plant.isWatered());
        plantRepository.save(planta);
        return "redirect:/plants/cadastro-plantas";
    }

    /**
     * Caminho para plantas de domingo
     *
     * @param model
     * @return
     */
    @GetMapping("/{day}")
    public ModelAndView getSunday(Model model, @PathVariable int day) {
        ModelAndView modelAndView = new ModelAndView("plantas");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Plant> todasPlantas = plantRepository.findAll();
        ArrayList<Plant> plantas = new ArrayList<>();
        for (Plant p : todasPlantas) {
            if (p.getUser().getId().equals(user.getId())) {
                if (p.getDaysToWater()[day]) {
                    plantas.add(p);
                }
            }
        }
        modelAndView.addObject("todas_plantas", plantas);
//      Adicionamos atributos de cadastro de plantas para o modelo para que em POST seja possivel recuperar os atributos de cadastro de planta
        model.addAttribute("plant", new Plant());
        return modelAndView;
    }

    /**
     * Post para marcar a planta como regada
     *
     * @param id
     * @return
     */
    @PostMapping("water/{id}")
    public String waterPlant(@PathVariable int id) {
        Plant plant = plantRepository.findById(id).get();
        plant.setWatered(true);
        plantRepository.save(plant);
        return "redirect:/plants/cadastro-plantas";
    }

    /**
     * Post para marcar a planta como nao regada
     *
     * @param id
     * @return
     */
    @PostMapping("not-watered/{id}")
    public String notWateredPlant(@PathVariable int id) {
        Plant plant = plantRepository.findById(id).get();
        plant.setWatered(false);
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
     * Post para deletar a planta
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
     * Relatorio para as plantas de cada dia da semana
     *
     * @param day
     * @return
     */
    @GetMapping("/reports/{day}")
//    @Override
    public ModelAndView report(@PathVariable int day) {
        String dayOfWeek;
        switch (day) {
            case 0:
                dayOfWeek = "domingo";
                break;
            case 1:
                dayOfWeek = "segunda";
                break;
            case 2:
                dayOfWeek = "terça";
                break;
            case 3:
                dayOfWeek = "quarta";
                break;
            case 4:
                dayOfWeek = "quinta";
                break;
            case 5:
                dayOfWeek = "sexta";
                break;
            case 6:
                dayOfWeek = "sábado";
                break;
            default:
                throw new AssertionError();
        }
        ModelAndView mv = new ModelAndView("relatorio");
        int totalNotWatered = 0;
        int totalWatered = 0;
        int wateredPlantWeek = 0;
        int notWateredPlantWeek = 0;
        int totalPlants = 0;
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();
        ArrayList<Plant> plantsWeek = new ArrayList<>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (Plant plant : plants) {
            if (plant.getUser().getId().equals(user.getId())) {
                totalPlants++;
                if (plant.isWatered()) {
                    totalWatered++;
                } else {
                    totalNotWatered++;
                }
                if (plant.getDaysToWater()[day]) {
                    if (plant.isWatered()) {
                        wateredPlantWeek++;
                        plantsWeek.add(plant);
                    } else {
                        notWateredPlantWeek++;
                    }
                }
            }
        }
        mv.addObject("todas_plantas", plantsWeek);
        mv.addObject("totalPlants", totalPlants);
        mv.addObject("user", user.getNome());
        mv.addObject("dayOfWeek", dayOfWeek);
        mv.addObject("totalNotWatered", totalNotWatered);
        mv.addObject("totalWatered", totalWatered);
        mv.addObject("wateredPlantWeek", wateredPlantWeek);
        mv.addObject("notWateredPlantWeek", notWateredPlantWeek);
        return mv;
    }
}
