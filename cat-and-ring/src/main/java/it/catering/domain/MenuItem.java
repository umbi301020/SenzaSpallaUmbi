// Pattern Composite - Voce del menu (foglia)
public class MenuItem extends MenuComponent {
    private String itemName;
    private String description;
    private Recipe recipe;
    
    public MenuItem(String itemName, Recipe recipe) {
        this.itemName = itemName;
        this.recipe = recipe;
    }
    
    public MenuItem(String itemName, String description, Recipe recipe) {
        this.itemName = itemName;
        this.description = description;
        this.recipe = recipe;
    }
    
    @Override
    public String getName() {
        return itemName;
    }
    
    @Override
    public String getDescription() {
        return description != null ? description : "";
    }
    
    @Override
    public void display() {
        System.out.println("  • " + itemName);
        if (description != null && !description.isEmpty()) {
            System.out.println("    " + description);
        }
    }
    
    public Recipe getRecipe() { return recipe; }
    public void setDescription(String description) { this.description = description; }
}

enum MenuStatus {
    DRAFT, PUBLISHED, IN_USE
}
