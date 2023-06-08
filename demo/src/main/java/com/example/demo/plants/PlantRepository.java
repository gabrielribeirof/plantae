package com.example.demo.plants;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Karol
 */
@Repository
public interface PlantRepository extends CrudRepository<Plant, Integer> {

}
