package jgfx.javagradlefx.model;

public class Ingredient {
    private long id;
    private String name;
    private String unite;
    private int quantite;

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unite='" + unite + '\'' +
                ", quantite=" + quantite +
                '}';
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getUnite() {
        return unite;
    }

    public int getQuantite() {
        return quantite;
    }

    public Ingredient(long id, String name, String unite, int quantite) {
        this.id = id;
        this.name = name;
        this.unite = unite;
        this.quantite = quantite;
    }

}
