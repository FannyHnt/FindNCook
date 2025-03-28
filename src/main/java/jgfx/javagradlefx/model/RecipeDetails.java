package jgfx.javagradlefx.model;


import java.util.List;

public class RecipeDetails {
    private Long id;
    private String name;
    private String image;
    private int servings;
    private double cookingTime;
    private List<Ingredient> ingredientList;
    private List<String> steps;
    private List<String> diets;
    private List<Nutrient> nutrients;

    public String getId() {
        return id.toString();
    }

    @Override
    public String toString() {
        return "RecetteInfo{" +
                "id=" + id +
                ", portion=" + servings +
                ", tempsPreparation=" + cookingTime +
                ", ingredientList=" + ingredientList +
                ", etapes=" + steps +
                ", regimesAlimentaires=" + diets +
                ", nutrients=" + nutrients +
                '}';
    }

    public RecipeDetails(Long id, String name, String image, List<Nutrient> nutrients, List<String> diets, List<String> steps, List<Ingredient> ingredientList, double cookingTime, int servings) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.nutrients = nutrients;
        this.diets = diets;
        this.steps = steps;
        this.ingredientList = ingredientList;
        this.cookingTime = cookingTime;
        this.servings = servings;

    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getServings() {
        return servings;
    }

    public double getCookingTime() {
        return cookingTime;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public List<String> getSteps() {
        return steps;
    }

    public List<String> getDiets() {
        return diets;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public List<Nutrient> getNutrients() {
        return nutrients;
    }
}

