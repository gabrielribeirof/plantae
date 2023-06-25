package com.plantae.plants;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
     * @param model
     * @return
     */
    public ModelAndView plantas(Model model);

    /**
     *
     * @param plant
     * @return
     */
    public String plantas(@ModelAttribute Plant plant);

    /**
     *
     * @param id
     * @param bodyPlant
     * @return
     */
    public String updatePlant(@PathVariable int id, @RequestBody Plant bodyPlant);

    /**
     *
     * @param id
     * @return
     */
    public String deletePlant(@PathVariable int id);
    
    /**
     *
     * @param userid
     * @return
     */
    public ModelAndView totalOfWeek(@PathVariable int userid);

    /**
     *
     * @param userid
     * @return
     */
    public ModelAndView lessWateredOfWeek(@PathVariable int userid);

    /**
     *
     * @param userid
     * @return
     */
    public ModelAndView mostWateredOfWeek(@PathVariable int userid);

    /**
     *
     * @param day
     * @param userid
     * @return
     */
    public ModelAndView wateredOfDay(@PathVariable int day, @PathVariable int userid);

    /**
     *
     * @param day
     * @param userid
     * @return
     */
    public ModelAndView notWateredOfDay(@PathVariable int day, @PathVariable int userid);
}
