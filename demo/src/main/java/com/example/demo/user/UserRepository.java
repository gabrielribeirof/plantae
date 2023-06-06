
package com.example.demo.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Willian
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByName(String name);
}
