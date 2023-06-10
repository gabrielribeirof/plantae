
package com.example.demo;

//import model.Plantas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Willian
 */
@Controller
public class ControllerApplication {

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public String index() {
        return "index";
    }
}
