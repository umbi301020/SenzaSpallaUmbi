// Pattern GRASP: Information Expert - MenuCharacteristics conosce le caratteristiche del menu
public class MenuCharacteristics {
    private boolean requiresChefDuringService;
    private boolean hasHotDishes;
    private boolean requiresKitchen;
    private boolean buffetSuitable;
    private boolean fingerFood;
    
    // Getters e setters
    public boolean isRequiresChefDuringService() { return requiresChefDuringService; }
    public void setRequiresChefDuringService(boolean requiresChefDuringService) { 
        this.requiresChefDuringService = requiresChefDuringService; 
    }
    public boolean isHasHotDishes() { return hasHotDishes; }
    public void setHasHotDishes(boolean hasHotDishes) { this.hasHotDishes = hasHotDishes; }
    public boolean isRequiresKitchen() { return requiresKitchen; }
    public void setRequiresKitchen(boolean requiresKitchen) { this.requiresKitchen = requiresKitchen; }
    public boolean isBuffetSuitable() { return buffetSuitable; }
    public void setBuffetSuitable(boolean buffetSuitable) { this.buffetSuitable = buffetSuitable; }
    public boolean isFingerFood() { return fingerFood; }
    public void setFingerFood(boolean fingerFood) { this.fingerFood = fingerFood; }
}