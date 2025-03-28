package jgfx.javagradlefx.model;

public class User {
    private Long id;
    private String name;
    private Preference preference;

    //constructeur
    public User(Long id, String name){
        this.id = id;
        this.name = name;
        this.preference = new Preference(id);
    }

    //getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Preference getPreference() {
        return preference;
    }
    //Methode pour mettre à jour les préférences de l'utilisateur
    public void updatePreference(Preference preference) {
        this.preference = preference;
    }

}