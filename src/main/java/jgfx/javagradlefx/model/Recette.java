package jgfx.javagradlefx.model;

public class Recette {
    private Long id;
    private String nom;
    private String urlImage;

    //constructeur
    public Recette(Long id, String nom, String urlImage){
        this.id = id;
        this.nom = nom;
        this.urlImage = urlImage;
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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return id+":{id=" + id + ", nom='" + nom + "', urlImage='" + urlImage + "'}";
    }
}
