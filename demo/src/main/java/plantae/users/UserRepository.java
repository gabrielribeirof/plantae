package plantae.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Willian
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
}
