package jgfx.javagradlefx.model;

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

    public long getId() {

        return id;
    }

    public String getName() {

        return name;
    }

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
