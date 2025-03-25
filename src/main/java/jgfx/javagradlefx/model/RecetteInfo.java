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
    private SpoonacularService spoonacularService;
}

