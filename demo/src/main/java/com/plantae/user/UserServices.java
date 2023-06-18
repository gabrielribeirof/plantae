package com.example.demo.user;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Unesp
 */
public interface UserServices {

    public List<User> findAll();

    public ResponseEntity<User> newUser(@RequestBody User user);

    public ResponseEntity<User> findById(@PathVariable int id);

    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User bodyUser);

    public ResponseEntity<Void> deleteUser(@PathVariable int id);
}
