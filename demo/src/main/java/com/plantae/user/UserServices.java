package com.plantae.user;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Unesp
 */
public interface UserServices {
    
    /**
     *
     * @return
     */
    public String home();
    
    /**
     *
     * @return
     */
    public String login();
    
    /**
     * @param model
     * @return
     */
    public String cadastro(Model model);

    /**
     *
     * @param user
     * @return
     */
    public String newUser(@RequestBody User user);

    /**
     *
     * @param id
     * @param bodyUser
     * @return
     */
    public String updateUser(@PathVariable int id, @RequestBody User bodyUser);

    /**
     *
     * @param id
     * @return
     */
    public String deleteUser(@PathVariable int id);
}
