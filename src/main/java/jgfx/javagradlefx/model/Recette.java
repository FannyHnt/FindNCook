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

    @Override
    public String toString() {
        return id+":{id=" + id + ", nom='" + nom + "', urlImage='" + urlImage + "'}";
    }
}
