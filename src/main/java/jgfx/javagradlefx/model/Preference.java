package jgfx.javagradlefx.model;

import java.util.ArrayList;
import java.util.List;

public class Preference {
    private Long id;
    private String regimeAlimentaire;
    private List<String> intolerancesAlimentaires;

    //constructeur
    public Preference(Long id){
        this.id = id;
        regimeAlimentaire = "";
        intolerancesAlimentaires = new ArrayList<String>();
    }

    //getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegimeAlimentaire() {
        return regimeAlimentaire;
    }

    public void setRegimeAlimentaire(String regimeAlimentaire) {
        this.regimeAlimentaire = regimeAlimentaire;
    }

    public List<String> getIntolerancesAlimentaires() {
        return intolerancesAlimentaires;
    }

    public void setIntolerancesAlimentaires(List<String> intolerancesAlimentaires) {
        this.intolerancesAlimentaires = intolerancesAlimentaires;
    }

    //méthode pour ajouter une intolérance alimentaire
    public void ajouterIntoleranceAlimentaire(String intoleranceAlimentaire){
        this.intolerancesAlimentaires.add(intoleranceAlimentaire);
    }

    //méthode pour supprimer une intolérance alimentaire
    public void supprimerIntoleranceAlimentaire(String intoleranceAlimentaire){
        this.intolerancesAlimentaires.remove(intoleranceAlimentaire);
    }
}