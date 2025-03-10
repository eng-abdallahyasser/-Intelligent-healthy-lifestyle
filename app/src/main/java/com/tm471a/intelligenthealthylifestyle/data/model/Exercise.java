package com.tm471a.intelligenthealthylifestyle.data.model;

import com.google.firebase.firestore.PropertyName;
import java.util.List;

public class Exercise {
    private String name;
    private String description;
    private List<String> primaryMuscles;
    private List<String> equipment;
    private int sets;
    private int reps;

    public Exercise() {}

    // Getters and setters with @PropertyName annotations
    @PropertyName("name")
    public String getName() { return name; }

    @PropertyName("primary_muscles")
    public List<String> getPrimaryMuscles() { return primaryMuscles; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrimaryMuscles(List<String> primaryMuscles) {
        this.primaryMuscles = primaryMuscles;
    }

    public List<String> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<String> equipment) {
        this.equipment = equipment;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}