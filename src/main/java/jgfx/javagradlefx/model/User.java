package jgfx.javagradlefx.model;

/**
 * Représente un utilisateur dans l'application JavaFX.
 * Contient des informations sur l'identifiant, le nom et les préférences alimentaires de l'utilisateur.
 *
 * Champs principaux :
 * - `id` : Identifiant unique de l'utilisateur.
 * - `name` : Nom de l'utilisateur.
 * - `preference` : Préférences alimentaires de l'utilisateur.
 *
 * Méthodes principales :
 * - `getId()` : Récupère l'identifiant de l'utilisateur.
 * - `setId(Long id)` : Définit l'identifiant de l'utilisateur.
 * - `getName()` : Récupère le nom de l'utilisateur.
 * - `setName(String name)` : Définit le nom de l'utilisateur.
 * - `getPreference()` : Récupère les préférences alimentaires de l'utilisateur.
 * - `updatePreference(Preference preference)` : Met à jour les préférences alimentaires de l'utilisateur.
 */

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