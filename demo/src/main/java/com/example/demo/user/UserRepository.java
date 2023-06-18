package com.example.demo.user;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Willian
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    public Optional<User> findById(int id);
}
