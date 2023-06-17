package com.example.demo.plants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Karol
 */
@RestController
@RequestMapping("/plants")
public class PlantController implements PlantServices {

    @Autowired
    PlantRepository plantRepository;

    @GetMapping
    @Override
    public List<Plant> findAll() {
        return (List<Plant>) plantRepository.findAll();
    }

    @PostMapping
    @Override
    public ResponseEntity<Plant> newPlant(@RequestBody Plant plant) {
        Plant newPlant = plantRepository.save(plant);
        return new ResponseEntity<>(newPlant, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Plant> findById(@PathVariable int id) {
        Optional<Plant> plantOptional = plantRepository.findById(id);
        return plantOptional.map(produto -> new ResponseEntity<>(produto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Plant> updatePlant(@PathVariable int id, @RequestBody Plant bodyPlant) {
        Optional<Plant> plantOptional = plantRepository.findById(id);
        if (plantOptional.isPresent()) {
            Plant newPlant = plantOptional.get();
            newPlant.setCategory(bodyPlant.getCategory());
            newPlant.setDaysToWater(bodyPlant.getDaysToWater());
            newPlant.setEspecie(bodyPlant.getEspecie());
            newPlant.setSun(bodyPlant.getSun());
            newPlant.setWater(bodyPlant.getWater());

            Plant updatedPlant = plantRepository.save(newPlant);
            return new ResponseEntity<>(updatedPlant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deletePlant(@PathVariable int id) {
        Optional<Plant> plant = plantRepository.findById(id);
        if (plant.isPresent()) {
            plantRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // REPORTS *****************************************************************
    @GetMapping("/{userid}/reports/week/total-watered")
    @Override
    public ResponseEntity<Integer> totalOfWeek(@PathVariable int userid) {
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

        return new ResponseEntity(total, HttpStatus.OK);
    }

    @GetMapping("/{userid}/reports/week/less-watered")
    @Override
    public ResponseEntity<Plant> lessWateredOfWeek() {
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
        
        return new ResponseEntity(countWateredPlants.get(keys.get(0)), HttpStatus.OK);
    }

    @GetMapping("/{userid}/reports/week/most-watered")
    @Override
    public ResponseEntity<Plant> mostWateredOfWeek() {
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
        
        return new ResponseEntity(countWateredPlants.get(keys.get(keys.size())), HttpStatus.OK);
    }

    @GetMapping("/{userid}/reports/{day}/total-watered")
    @Override
    public ResponseEntity<Integer> wateredOfDay(@PathVariable int day, @PathVariable int userid) {
        int total = 0;
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();

        for (Plant plant : plants) {
            if (plant.getDaysToWater()[day] == true) {
                total++;
            }
        }

        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    @GetMapping("/{userid}/reports/{day}/total-not-watered")
    @Override
    public ResponseEntity<Integer> notWateredOfDay(@PathVariable int day, @PathVariable int userid) {
        int total = 0;
        Iterable<Plant> plants = (List<Plant>) plantRepository.findAll();

        for (Plant plant : plants) {
            if (plant.getDaysToWater()[day] == false) {
                total++;
            }
        }

        return new ResponseEntity<>(total, HttpStatus.OK);
    }
}
