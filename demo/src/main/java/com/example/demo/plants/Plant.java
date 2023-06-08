
package com.example.demo.plants;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Willian
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
    private ArrayList<String> daysToWater;
    
    public Plant(){}

    public Plant(int id, String especie, String category, int water, int sun, ArrayList<String> daysToWater) {
        this.id = id;
        this.especie = especie;
        this.category = category;
        this.water = water;
        this.sun = sun;
        this.daysToWater = daysToWater;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getSun() {
        return sun;
    }

    public void setSun(int sun) {
        this.sun = sun;
    }

    public ArrayList<String> getDaysToWater() {
        return daysToWater;
    }

    public void setDaysToWater(ArrayList<String> daysToWater) {
        this.daysToWater = daysToWater;
    }
}
