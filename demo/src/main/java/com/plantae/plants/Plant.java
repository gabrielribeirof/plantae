/*
 * Projeto da disciplina de Introducao a Tecnologia Java
 * Membros: Bruno Augusto Furquim, Gabriel Ribeiro Ferreira, Karolyne Domiciano Marques, Willian Yoshio Murayama
 */
package com.plantae.plants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.plantae.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 *
 * @author Karol
 */
@Entity
public class Plant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    private String especie;

    @NotEmpty
    private String category;

    @NotEmpty
    private int water; // storage with 1, 2 or 3

    @NotEmpty
    private int sun; // storage with 1, 2 or 3

    @NotEmpty
    private boolean[] daysToWater = new boolean[7];

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @JsonIgnoreProperties("plant")
    @NotEmpty
    private User user;

    /**
     *
     */
    public Plant() {
    }

    /**
     *
     * @param id
     * @param especie
     * @param category
     * @param water
     * @param sun
     * @param daysToWater
     */
    public Plant(int id, String especie, String category, int water, int sun, boolean[] daysToWater) {
        this.id = id;
        this.especie = especie;
        this.category = category;
        this.water = water;
        this.sun = sun;
        this.daysToWater = daysToWater;
    }
    
    
    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getEspecie() {
        return especie;
    }

    /**
     *
     * @param especie
     */
    public void setEspecie(String especie) {
        this.especie = especie;
    }

    /**
     *
     * @return
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @return
     */
    public int getWater() {
        return water;
    }

    /**
     *
     * @param water
     */
    public void setWater(int water) {
        this.water = water;
    }

    /**
     *
     * @return
     */
    public int getSun() {
        return sun;
    }

    /**
     *
     * @param sun
     */
    public void setSun(int sun) {
        this.sun = sun;
    }

    /**
     *
     * @return
     */
    public boolean[] getDaysToWater() {
        return daysToWater;
    }

    /**
     *
     * @param daysToWater
     */
    public void setDaysToWater(boolean[] daysToWater) {
        this.daysToWater = daysToWater;
    }

    /**
     *
     * @return
     */
    public User getUser() {
        return user;
    }
    
    public void setUser(User user){
        this.user = user;
    }
}
