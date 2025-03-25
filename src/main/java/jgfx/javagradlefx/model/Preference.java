package jgfx.javagradlefx.model;

import java.util.List;

public class Preference {
    private Long id;
    private List<String> regimesAlimentaires;
    private List<String> intolerancesAlimentaires;

    //methode pour ajourter un regime alimentaire
    public void ajouterRegimeAlimentaire(String regimeAlimentaire){
        this.regimesAlimentaires.add(regimeAlimentaire);
    }

    //méthode pour supprimer un regime alimentaire
    public void supprimerRegimeAlimentaire(String regimeAlimentaire){
        this.regimesAlimentaires.remove(regimeAlimentaire);
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