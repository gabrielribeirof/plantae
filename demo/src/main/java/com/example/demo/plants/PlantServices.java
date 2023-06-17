package com.example.demo.plants;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Karol
 */
@Component
public interface PlantServices {

    public List<Plant> findAll();

    public ResponseEntity<Plant> newPlant(@RequestBody Plant plant);

    public ResponseEntity<Plant> findById(@PathVariable int id);

    public ResponseEntity<Plant> updatePlant(@PathVariable int id, @RequestBody Plant bodyPlant);

    public ResponseEntity<Void> deletePlant(@PathVariable int id);

    public ResponseEntity<Integer> totalOfWeek(@PathVariable int userid);

    public ResponseEntity<Plant> lessWateredOfWeek();

    public ResponseEntity<Plant> mostWateredOfWeek();

    public ResponseEntity<Integer> wateredOfDay(@PathVariable int day, @PathVariable int userid);

    public ResponseEntity<Integer> notWateredOfDay(@PathVariable int day, @PathVariable int userid);
}
