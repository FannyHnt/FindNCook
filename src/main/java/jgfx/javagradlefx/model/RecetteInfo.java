package jgfx.javagradlefx.model;


import java.util.List;

public class RecetteInfo {
    private Long id;
    private int portion;
    private double tempsPreparation;
    private List<Ingredient> ingredientList;
    private List<String> etapes;
    //private List<String> ustensiles;
    private List<String> regimesAlimentaires;
    private List<Nutrient> nutrients;

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "RecetteInfo{" +
                "id=" + id +
                ", portion=" + portion +
                ", tempsPreparation=" + tempsPreparation +
                ", ingredientList=" + ingredientList +
                ", etapes=" + etapes +
                ", regimesAlimentaires=" + regimesAlimentaires +
                ", nutrients=" + nutrients +
                '}';
    }

    public RecetteInfo(Long id, int portion,
                       double tempsPreparation,
                       List<Ingredient> ingredientList,
                       List<String> etapes,
                       List<String> regimesAlimentaires,
                       List<Nutrient> nutrients) {
        this.id = id;
        this.portion = portion;
        this.tempsPreparation = tempsPreparation;
        this.ingredientList = ingredientList;
        this.etapes = etapes;
        this.regimesAlimentaires = regimesAlimentaires;
        this.nutrients = nutrients;
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

    public List<String> getRegimesAlimentaires() {
        return regimesAlimentaires;
    }
}

