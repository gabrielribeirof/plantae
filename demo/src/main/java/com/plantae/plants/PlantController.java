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
        Iterable<Plant> todasPlantas = plantRepository.findAll();
        mv.addObject("todas_plantas", todasPlantas);
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
            return "redirect:/cadastro-plantas";
        }
        // Handle user not found scenario
        return "error";
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
        return "redirect:/cadastro-plantas";
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("plants/{id}")
    @Override
    public String deletePlant(@PathVariable int id) {
        plantRepository.deleteById(id);
        return "redirect:/cadastro-plantas";
    }

    // REPORTS *****************************************************************
    /**
     *
     * @param userid
     * @return
     */
    public int totalWateredOfWeek(int userid) {
        int total = 0;
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();

        for (Plant plant : plants) {
            if (plant.getUser().getId() != userid) {
                continue;
            }
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

        return total;
    }
    
    /**
     *
     * @param userid
     * @return
     */
    public int totalNotWateredOfWeek(int userid) {
        int total = 0;
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();

        for (Plant plant : plants) {
            if (plant.getUser().getId() != userid) {
                continue;
            }
            boolean[] daysToWater = plant.getDaysToWater();
            boolean plantNotWatered = false;
            for (boolean watered : daysToWater) {
                if (watered == false) {
                    total++;
                    plantNotWatered = true;
                    break;
                }
            }
            if (plantNotWatered) {
                break;
            }
        }

        return total;
    }

    /**
     *
     * @param userid
     * @return
     */
    public Plant lessWateredOfWeek(int userid) {
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();
        HashMap<Integer, Plant> countWateredPlants = new HashMap<>();

        for (Plant plant : plants) {
            if (plant.getUser().getId() != userid) {
                continue;
            }
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

        return countWateredPlants.get(keys.get(0));
    }

    /**
     *
     * @param userid
     * @return
     */
    public Plant mostWateredOfWeek(int userid) {
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();
        HashMap<Integer, Plant> countWateredPlants = new HashMap<>();

        for (Plant plant : plants) {
            if (plant.getUser().getId() != userid) {
                continue;
            }
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

        return countWateredPlants.get(keys.get(keys.size()));
    }

    /**
     *
     * @param day
     * @param userid
     * @return
     */
    public int wateredOfDay(int day, int userid) {
        int total = 0;
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();

        for (Plant plant : plants) {
            if (plant.getUser().getId() != userid) {
                continue;
            }
            if (plant.getDaysToWater()[day] == true) {
                total++;
            }
        }

        return total;
    }

    /**
     *
     * @param day
     * @param userid
     * @return
     */
    public int notWateredOfDay(int day, int userid) {
        int total = 0;
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();

        for (Plant plant : plants) {
            if (plant.getUser().getId() != userid) {
                continue;
            }
            if (plant.getDaysToWater()[day] == false) {
                total++;
            }
        }

        return total;
    }
    
      /**
     *
     * @param userid
     * @return
     */
    public int totalPlantsByUser(int userid) {
        int total = 0;
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();

        for (Plant plant : plants) {
            if (plant.getUser().getId() != userid) {
                continue;
            }
            total++;
        }

        return total;
    }
    
          /**
     *
     * @param userid
     * @return
     */
    public ArrayList<Plant> plantsByUser(int userid) {
        ArrayList<Plant> plantsOfUser = new ArrayList<>();
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();

        for (Plant plant : plants) {
            if (plant.getUser().getId() != userid) {
                continue;
            }
            plantsOfUser.add(plant);
        }

        return plantsOfUser;
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

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        int totalNotWatered = totalNotWateredOfWeek(user.getId());
        int totalWatered = totalWateredOfWeek(user.getId());
        int wateredPlantWeek = wateredOfDay(day, user.getId());
        int notWateredPlantWeek = notWateredOfDay(day, user.getId());
        int totalPlants = totalPlantsByUser(user.getId());
         ArrayList<Plant> plantsWeek = plantsByUser(user.getId());
         Plant lessWateredOfWeek = lessWateredOfWeek(user.getId());
         Plant mostWateredOfWeek = mostWateredOfWeek(user.getId());

        mv.addObject("plantsWeek", plantsWeek);
        mv.addObject("totalPlants", totalPlants);
        mv.addObject("user", user.getNome());
        mv.addObject("dayOfWeek", dayOfWeek);
        mv.addObject("totalNotWatered", totalNotWatered);
        mv.addObject("totalWatered", totalWatered);
        mv.addObject("wateredPlantWeek", wateredPlantWeek);
        mv.addObject("notWateredPlantWeek", notWateredPlantWeek);
        mv.addObject("lessWateredOfWeek", lessWateredOfWeek);
        mv.addObject("mostWateredOfWeek", mostWateredOfWeek);

        return mv;
    }
}
