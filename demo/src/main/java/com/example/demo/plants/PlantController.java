package com.example.demo.plants;

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
public class PlantController {

    @Autowired
    PlantRepository plantRepository;

    @GetMapping
    public List<Plant> findAll() {
        return (List<Plant>) plantRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Plant> newPlant(@RequestBody Plant plant) {
        Plant newPlant = plantRepository.save(plant);
        return new ResponseEntity<>(newPlant, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plant> findById(@PathVariable int id) {
        Optional<Plant> plantOptional = plantRepository.findById(id);
        return plantOptional.map(produto -> new ResponseEntity<>(produto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
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
    public ResponseEntity<Void> deletePlant(@PathVariable int id) {
        Optional<Plant> plant = plantRepository.findById(id);
        if (plant.isPresent()) {
            plantRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
