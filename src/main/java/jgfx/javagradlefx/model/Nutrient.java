package jgfx.javagradlefx.model;


/**
 * Représente un nutriment dans l'application JavaFX.
 * Contient des informations sur l'identifiant, le nom, la quantité, l'unité et le pourcentage des besoins journaliers.
 *
 * Champs principaux :
 * - `id` : Identifiant unique du nutriment.
 * - `name` : Nom du nutriment.
 * - `quantity` : Quantité du nutriment.
 * - `unit` : Unité de mesure du nutriment (ex. mg, g).
 * - `percentOfDailyNeeds` : Pourcentage des besoins journaliers couverts par ce nutriment.
 *
 * Méthodes principales :
 * - `getId()` : Récupère l'identifiant du nutriment.
 * - `getName()` : Récupère le nom du nutriment.
 * - `getQuantity()` : Récupère la quantité du nutriment.
 * - `getUnit()` : Récupère l'unité de mesure du nutriment.
 * - `getPercentOfDailyNeeds()` : Récupère le pourcentage des besoins journaliers.
 * - `toString()` : Retourne une représentation textuelle du nutriment.
 */
public class Nutrient {
    private long id;
    private String name;
    private double quantity;
    private String unit;
    private double percentOfDailyNeeds;

    public Nutrient(long id, String name, double quantity, String unit, double percentOfDailyNeeds) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.percentOfDailyNeeds = percentOfDailyNeeds;
    }

    @Override
    public String toString() {
        return "Nutrient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", percentOfDailyNeeds=" + percentOfDailyNeeds +
                '}';
    }

    public long getId() {return id;}

    public String getName() {return name;}

    public String getUnit() {
        return unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPercentOfDailyNeeds() {
        return percentOfDailyNeeds;
    }
}
