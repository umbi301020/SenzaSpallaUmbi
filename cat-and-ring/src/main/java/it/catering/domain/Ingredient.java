public class Ingredient {
    private String name;
    private String quantity;
    private String unit;
    
    public Ingredient(String name, String quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }
    
    public String getName() { return name; }
    public String getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    
    @Override
    public String toString() {
        return quantity + " " + unit + " di " + name;
    }