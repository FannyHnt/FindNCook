package jgfx.javagradlefx.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente les préférences alimentaires d'un utilisateur dans l'application JavaFX.
 * Contient des informations sur le régime alimentaire et les intolérances.
 *
 * Champs principaux :
 * - `id` : Identifiant unique des préférences.
 * - `diet` : Régime alimentaire de l'utilisateur (ex. végétarien, végan).
 * - `intolerances` : Liste des intolérances alimentaires de l'utilisateur.
 *
 * Méthodes principales :
 * - `getDiet()` : Récupère le régime alimentaire.
 * - `setDiet(String diet)` : Définit le régime alimentaire.
 * - `getIntolerances()` : Récupère la liste des intolérances.
 * - `setIntolerances(List<String> intolerances)` : Définit la liste des intolérances.
 * - `addIntolerance(String intolerance)` : Ajoute une intolérance à la liste.
 */

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