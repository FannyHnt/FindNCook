package jgfx.javagradlefx.model;

import java.util.ArrayList;
import java.util.List;

public class Preference {
    private Long id;
    private String diet;
    private List<String> intolerances;

    //constructeur
    public Preference(Long id){
        this.id = id;
        diet = "";
        intolerances = new ArrayList<String>();
    }

    //getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public List<String> getIntolerances() {
        return intolerances;
    }

    public void setIntolerances(List<String> intolerances) {
        this.intolerances = intolerances;
    }

    //méthode pour ajouter une intolérance alimentaire
    public void addIntolerance(String intolerance){
        this.intolerances.add(intolerance);
    }
}