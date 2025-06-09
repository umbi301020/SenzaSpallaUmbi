public class QuantityScaledRecipeDecorator extends RecipeDecorator {
    private final double scaleFactor;
    
    public QuantityScaledRecipeDecorator(Recipe recipe, double scaleFactor) {
        super(recipe);
        this.scaleFactor = scaleFactor;
    }
    
    @Override
    public String getName() {
        return recipe.getName() + " (x" + scaleFactor + ")";
    }
    
    @Override
    public int getEstimatedTime() {
        return (int) (recipe.getEstimatedTime() * scaleFactor);
    }
    
    @Override
    public int getPortions() {
        return (int) (recipe.getPortions() * scaleFactor);
    }
}