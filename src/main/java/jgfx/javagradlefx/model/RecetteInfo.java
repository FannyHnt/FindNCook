package jgfx.javagradlefx.model;


import java.util.List;

public class RecetteInfo {
    private Long id;
    private String nom;
    private String image;
    private int portion;
    private double tempsPreparation;
    private List<Ingredient> ingredientList;
    private List<String> etapes;
    //private List<String> ustensiles;
    private List<String> regimesAlimentaires;
    private List<Nutrient> nutrients;

    public String getId() {
        return id.toString();
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

    public RecetteInfo(Long id, String nom, String image, List<Nutrient> nutrients, List<String> regimesAlimentaires, List<String> etapes, List<Ingredient> ingredientList, double tempsPreparation, int portion) {
        this.id = id;
        this.nom = nom;
        this.image = image;
        this.nutrients = nutrients;
        this.regimesAlimentaires = regimesAlimentaires;
        this.etapes = etapes;
        this.ingredientList = ingredientList;
        this.tempsPreparation = tempsPreparation;
        this.portion = portion;

    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNom() {
        return nom;
    }

    public String getImage() {
        return image;
    }

    public List<Nutrient> getNutrients() {
        return nutrients;
    }
}

