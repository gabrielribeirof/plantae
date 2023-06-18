package com.plantae.user;

import com.plantae.user.User;
import java.util.List;
import org.springframework.http.ResponseEntity;
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
    public List<User> findAll();

    /**
     *
     * @param user
     * @return
     */
    public ResponseEntity<User> newUser(@RequestBody User user);

    /**
     *
     * @param id
     * @return
     */
    public ResponseEntity<User> findById(@PathVariable int id);

    /**
     *
     * @param id
     * @param bodyUser
     * @return
     */
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User bodyUser);

    /**
     *
     * @param id
     * @return
     */
    public ResponseEntity<Void> deleteUser(@PathVariable int id);
}
