/*
 * Projeto da disciplina de Introducao a Tecnologia Java
 * Membros: Bruno Augusto Furquim, Gabriel Ribeiro Ferreira, Karolyne Domiciano Marques, Willian Yoshio Murayama
 */
package com.plantae.plants;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Karol
 */
@Repository
public interface PlantRepository extends CrudRepository<Plant, Integer> {

    /**
     * Achar alguma planta por ID
     *
     * @param id
     * @return
     */
    public Optional<Plant> findById(int id);

}
