package jgfx.javagradlefx.model;

/**
 * Représente une recette dans l'application JavaFX.
 * Contient des informations sur l'identifiant, le nom et l'URL de l'image de la recette.
 *
 * Champs principaux :
 * - `id` : Identifiant unique de la recette.
 * - `name` : Nom de la recette.
 * - `urlImage` : URL de l'image associée à la recette.
 *
 * Méthodes principales :
 * - `getId()` : Récupère l'identifiant de la recette.
 * - `setId(Long id)` : Définit l'identifiant de la recette.
 * - `getName()` : Récupère le nom de la recette.
 * - `getUrlImage()` : Récupère l'URL de l'image de la recette.
 * - `toString()` : Retourne une représentation textuelle de la recette.
 */

public class Recipe {
    private Long id;
    private String name;
    private String urlImage;

    //constructeur
    public Recipe(Long id, String name, String urlImage){
        this.id = id;
        this.name = name;
        this.urlImage = urlImage;
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

    public String getUrlImage() {
        return urlImage;
    }

    @Override
    public String toString() {
        return id+":{id=" + id + ", nom='" + name + "', urlImage='" + urlImage + "'}";
    }
}
