

import com.example.demo.plants.Plant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Karol
 */
@Repository
public interface PlantRepository extends CrudRepository<Plant, Integer> {

    Plant findByName(long id);
}
