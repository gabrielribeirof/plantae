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
     * @param model Adicionar o objeto para o view
     * @return Uma pagina com o objeto atribuido
     */
    @GetMapping("/")
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
        mv.addObject("day", -1);
        model.addAttribute("plant", new Plant());
        return mv;
    }

    /**
     * Post de cadastro de plantas
     *
     * @param plant Objeto que eh criado no view
     * @return Redireciona para a pagina de plantas
     */
    @PostMapping("/cadastro-plantas")
    @Override
    public String plantas(@ModelAttribute Plant plant) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        if (user != null) {
            plant.setUser(user);
            plantRepository.save(plant);
            return "redirect:/plants/";
        }
        // Handle user not found scenario
        return "error";
    }

    /**
     * Caminho para modificacao de plantas
     *
     * @param model Modelo que eh adicionado no view
     * @param id ID eh passado por parametro
     * @return Retorna a pagina de modificacao
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
     * @param plant Planta para ser modificado
     * @param id Id da planta
     * @return Redireciona para a pagina planta
     */
    @PostMapping("/modify/{id}")
    public String modifiyPlantsPost(@ModelAttribute Plant plant, @PathVariable int id) {
        Plant modifiedPlant = plantRepository.findById(id).get();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modifiedPlant.setCategory(plant.getCategory());
        modifiedPlant.setEspecie(plant.getEspecie());
        modifiedPlant.setDaysToWater(plant.getDaysToWater());
        modifiedPlant.setSun(plant.getSun());
        modifiedPlant.setWater(plant.getWater());
        if (user != null) {
            modifiedPlant.setUser(user);
        }

        plantRepository.save(modifiedPlant);
        return "redirect:/plants/";
    }

    /**
     * Caminho para plantas de um dia da semana
     *
     * @param model Modelo de planta no view
     * @param day Dia da semana
     * @return Uma pagina com as plantas do dia que eh regado
     */
    @GetMapping("/{day}")
    public ModelAndView getPlantOfDay(Model model, @PathVariable int day) {
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
        modelAndView.addObject("day", day);
//      Adicionamos atributos de cadastro de plantas para o modelo para que em POST seja possivel recuperar os atributos de cadastro de planta
        model.addAttribute("plant", new Plant());
        return modelAndView;
    }

    /**
     * Post para marcar a planta como regada
     *
     * @param id Id da planta
     * @param day Dia que a planta eh regado
     * @return Retorna a pagina do dia que a planta eh regado
     */
    @PostMapping("water/{id}/{day}")
    public String waterPlant(@PathVariable int id, @PathVariable int day) {
        Plant plant = plantRepository.findById(id).get();

        boolean[] newDaysWatered = plant.getDaysWatered();
        newDaysWatered[day] = true;
        plant.setDaysWatered(newDaysWatered);

        plantRepository.save(plant);
        return "redirect:/plants/" + day;
    }

    /**
     * Post para marcar a planta como nao regada
     *
     * @param id Id da planta
     * @param day Dia da semana eh regado em inteiro
     * @return Retorna a pagina do dia que a planta eh regado
     */
    @PostMapping("not-watered/{id}/{day}")
    public String notWateredPlant(@PathVariable int id, @PathVariable int day) {
        Plant plant = plantRepository.findById(id).get();
        boolean[] newDaysWatered = plant.getDaysWatered();
        newDaysWatered[day] = false;
        plant.setDaysWatered(newDaysWatered);
        plantRepository.save(plant);
        return "redirect:/plants/" + day;
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
        return "redirect:/plants/";
    }

    /**
     * Post para deletar a planta
     *
     * @param id Id da planta para ser excluido
     * @return Redireciona para a pagina principal
     */
    @DeleteMapping("/{id}")
    @Override
    public String deletePlant(@PathVariable int id) {
        plantRepository.deleteById(id);
        return "redirect:/plants/";
    }

    // REPORTS *****************************************************************
    /**
     * Quantidade de plantas regadas na semana
     *
     * @param userid Id do usuario
     * @return Quantidade de plantas regadas na semana
     */
    public int totalWateredOfWeek(int userid) {
        int total = 0;
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();

        for (Plant plant : plants) {
            if (plant.getUser().getId() != userid) {
                continue;
            }
            boolean[] daysWatered = plant.getDaysWatered();
            boolean plantWatered = false;
            for (boolean watered : daysWatered) {
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
     * Quantidade de plantas nao regadas na semana
     *
     * @param userid Id do usuario
     * @return Quantidade de plantas nao regadas
     */
    public int totalNotWateredOfWeek(int userid) {
        int total = plantsByUser(userid).size() - totalWateredOfWeek(userid);
        return total;
    }

    /**
     * A planta menos regada na semana
     *
     * @param userid Id do usuario
     * @return A planta menos regada na semana
     */
    public Plant lessWateredOfWeek(int userid) {
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();
        HashMap<Integer, Plant> countWateredPlants = new HashMap<>();

        for (Plant plant : plants) {
            if (plant.getUser().getId() != userid) {
                continue;
            }
            int total = 0;
            boolean[] daysWatered = plant.getDaysWatered();
            for (boolean watered : daysWatered) {
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
     * A planta mais regada na semana
     *
     * @param userid Id do usuario
     * @return A planta mais regada na semana
     */
    public Plant mostWateredOfWeek(int userid) {
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();
        HashMap<Integer, Plant> countWateredPlants = new HashMap<>();

        for (Plant plant : plants) {
            if (plant.getUser().getId() != userid) {
                continue;
            }
            int total = 0;
            boolean[] dayswatered = plant.getDaysWatered();
            for (boolean watered : dayswatered) {
                if (watered == true) {
                    total++;
                }
            }
            countWateredPlants.put(total, plant);
        }
        List<Integer> keys = new ArrayList<>(countWateredPlants.keySet());
        Collections.sort(keys);

        return countWateredPlants.get(keys.get(keys.size() - 1));
    }

    /**
     * A quantidade de plantas regadas no dia
     *
     * @param day Dia em inteiro
     * @param userid Id do usuario
     * @return A quantidade de plantas regadas no dia
     */
    public int wateredOfDay(int day, int userid) {
        int total = 0;
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();

        for (Plant plant : plants) {
            if (plant.getUser().getId() != userid) {
                continue;
            }
            if (plant.getDaysWatered()[day] == true) {
                total++;
            }
        }
        return total;
    }

    /**
     * Quantidade de plantas nao regadas no dia
     *
     * @param day Dia da semana em inteiro
     * @param userid Id do usuario
     * @return Quantidade de plantas nao regadas no dia
     */
    public int notWateredOfDay(int day, int userid) {
        int total = 0;
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();
        for (Plant plant : plants) {
            if (plant.getUser().getId() != userid) {
                continue;
            }
            if (plant.getDaysWatered()[day] == false) {
                total++;
            }
        }

        return total;
    }

    /**
     * A quantidade de plantas de um usuario
     *
     * @param userid Id do usuario
     * @return Quantidade de plantas em inteiro
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
     * Retorna todas as plantas de um usuario
     *
     * @param userid ID do usuario
     * @return Um arraylist com as plantas do usuario
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
     * @param day Dia da semana em inteiro
     * @return Retorna a pagina com os dias que foram regados
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
                dayOfWeek = "domingo";
                break;
        }
        ModelAndView mv = new ModelAndView("relatorio");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int totalNotWatered = totalNotWateredOfWeek(user.getId());
        int totalWatered = totalWateredOfWeek(user.getId());
        int wateredOfDay = wateredOfDay(day, user.getId());
        int notWateredOfDay = notWateredOfDay(day, user.getId());
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
        mv.addObject("wateredOfDay", wateredOfDay);
        mv.addObject("notWateredOfDay", notWateredOfDay);
        mv.addObject("lessWateredOfWeek", lessWateredOfWeek);
        mv.addObject("mostWateredOfWeek", mostWateredOfWeek);
        return mv;
    }
}
