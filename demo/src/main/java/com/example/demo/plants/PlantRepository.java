package com.example.demo.plants;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Karol
 */
@Repository
public interface PlantRepository extends CrudRepository<Plant, Integer> {
    
    Optional<Plant> findById(Long id);
    
}
