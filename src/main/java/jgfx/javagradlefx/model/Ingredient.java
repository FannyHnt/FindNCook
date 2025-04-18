package jgfx.javagradlefx.model;

/**
 * Représente un ingrédient dans l'application JavaFX.
 * Contient des informations sur l'identifiant, le nom, l'unité et la quantité de l'ingrédient.
 *
 * Champs principaux :
 * - `id` : Identifiant unique de l'ingrédient.
 * - `name` : Nom de l'ingrédient.
 * - `unit` : Unité de mesure de l'ingrédient (ex. grammes, litres).
 * - `quantity` : Quantité de l'ingrédient.
 *
 * Méthodes principales :
 * - `getName()` : Récupère le nom de l'ingrédient.
 * - `getId()` : Récupère l'identifiant de l'ingrédient.
 * - `getUnit()` : Récupère l'unité de mesure de l'ingrédient.
 * - `getQuantity()` : Récupère la quantité de l'ingrédient.
 * - `toString()` : Retourne une représentation textuelle de l'ingrédient.
 */

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
