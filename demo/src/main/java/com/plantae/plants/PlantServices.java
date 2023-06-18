package com.plantae.plants;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Karol
 */
@Component
public interface PlantServices {

    /**
     *
     * @return
     */
    public List<Plant> findAll();

    /**
     *
     * @param plant
     * @return
     */
    public ResponseEntity<Plant> newPlant(@RequestBody Plant plant);

    /**
     *
     * @param id
     * @return
     */
    public ResponseEntity<Plant> findById(@PathVariable int id);

    /**
     *
     * @param id
     * @param bodyPlant
     * @return
     */
    public ResponseEntity<Plant> updatePlant(@PathVariable int id, @RequestBody Plant bodyPlant);

    /**
     *
     * @param id
     * @return
     */
//    public ResponseEntity<Void> deletePlant(@PathVariable int id);
//    public ModelAndView deletePlant(@PathVariable int id);
    /**
     *
     * @param userid
     * @return
     */
    public ResponseEntity<Integer> totalOfWeek(@PathVariable int userid);

    /**
     *
     * @return
     */
    public ResponseEntity<Plant> lessWateredOfWeek();

    /**
     *
     * @return
     */
    public ResponseEntity<Plant> mostWateredOfWeek();

    /**
     *
     * @param day
     * @param userid
     * @return
     */
    public ResponseEntity<Integer> wateredOfDay(@PathVariable int day, @PathVariable int userid);

    /**
     *
     * @param day
     * @param userid
     * @return
     */
    public ResponseEntity<Integer> notWateredOfDay(@PathVariable int day, @PathVariable int userid);
}
