package jgfx.javagradlefx.model;

import java.util.List;

/**
 * Représente les détails d'une recette dans l'application JavaFX.
 * Contient des informations complètes sur une recette, y compris les ingrédients, les étapes, les régimes alimentaires, etc.
 *
 * Champs principaux :
 * - `id` : Identifiant unique de la recette.
 * - `name` : Nom de la recette.
 * - `image` : URL de l'image associée à la recette.
 * - `servings` : Nombre de portions de la recette.
 * - `cookingTime` : Temps de préparation de la recette.
 * - `ingredientList` : Liste des ingrédients nécessaires pour la recette.
 * - `steps` : Étapes de préparation de la recette.
 * - `diets` : Régimes alimentaires compatibles avec la recette.
 * - `nutrients` : Liste des nutriments associés à la recette.
 *
 * Méthodes principales :
 * - `getId()` : Récupère l'identifiant de la recette.
 * - `getName()` : Récupère le nom de la recette.
 * - `getImage()` : Récupère l'URL de l'image de la recette.
 * - `getServings()` : Récupère le nombre de portions.
 * - `getCookingTime()` : Récupère le temps de préparation.
 * - `getIngredientList()` : Récupère la liste des ingrédients.
 * - `getSteps()` : Récupère les étapes de préparation.
 * - `getDiets()` : Récupère les régimes alimentaires compatibles.
 * - `getNutrients()` : Récupère la liste des nutriments.
 * - `toString()` : Retourne une représentation textuelle des détails de la recette.
 */


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

