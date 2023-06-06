
package com.example.demo;

//import model.Plantas;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Willian
 */
@Controller
public class ControllerApplication {

    @GetMapping("")
    public String index() {
        return "index";
    }
}
