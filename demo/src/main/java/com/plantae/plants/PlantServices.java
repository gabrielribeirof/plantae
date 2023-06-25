package com.plantae.plants;

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

}
