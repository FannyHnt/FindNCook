package jgfx.javagradlefx.model;


import jgfx.javagradlefx.controller.SpoonacularService;

import java.util.List;

public class RecetteInfo {
    private Long id;
    private int portion;
    private double tempsPreparation;
    private List<Ingredient> ingredientList;
    private List<String> etapes;
    private List<String> ustensiles;
    private List<String> regimesAlimentaires;

    public Long getId() {
        return id;
    }

    public RecetteInfo(Long id, int portion, double tempsPreparation, List<Ingredient> ingredientList, List<String> etapes, List<String> ustensiles, List<String> regimesAlimentaires) {
        this.id = id;
        this.portion = portion;
        this.tempsPreparation = tempsPreparation;
        this.ingredientList = ingredientList;
        this.etapes = etapes;
        this.ustensiles = ustensiles;
        this.regimesAlimentaires = regimesAlimentaires;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPortion(int portion) {
        this.portion = portion;
    }

    public void setTempsPreparation(double tempsPreparation) {
        this.tempsPreparation = tempsPreparation;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public void setEtapes(List<String> etapes) {
        this.etapes = etapes;
    }

    public void setUstensiles(List<String> ustensiles) {
        this.ustensiles = ustensiles;
    }

    public void setRegimesAlimentaires(List<String> regimesAlimentaires) {
        this.regimesAlimentaires = regimesAlimentaires;
    }

    public int getPortion() {
        return portion;
    }

    public double getTempsPreparation() {
        return tempsPreparation;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public List<String> getEtapes() {
        return etapes;
    }

    public List<String> getUstensiles() {
        return ustensiles;
    }

    public List<String> getRegimesAlimentaires() {
        return regimesAlimentaires;
    }
}

