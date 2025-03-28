package jgfx.javagradlefx.model;

public class Ingredient {
    private long id;
    private String name;
    private String unit;
    private int quantity;

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unite='" + unit + '\'' +
                ", quantite=" + quantity +
                '}';
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getUnit() {
        return unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public Ingredient(long id, String name, String unit, int quantity) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
    }

}
