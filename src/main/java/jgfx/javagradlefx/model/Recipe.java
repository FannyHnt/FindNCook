package jgfx.javagradlefx.model;

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
