package jgfx.javagradlefx.model;

public class Utilisateur {
    private Long id;
    private String nom;
    private Preference preference;
    private Favoris favoris;

    //constructeur
    public Utilisateur(Long id, String nom){
        this.id = id;
        this.nom = nom;
        this.preference = new Preference(id);
    }

    //getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Preference getPreference() {
        return preference;
    }

    //Methode pour mettre à jour les préférences de l'utilisateur
    public void mettreAJourPreference(Preference preference) {
        this.preference = preference;
    }

}