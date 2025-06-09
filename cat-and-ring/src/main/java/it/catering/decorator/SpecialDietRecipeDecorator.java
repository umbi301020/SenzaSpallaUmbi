public class SpecialDietRecipeDecorator extends RecipeDecorator {
    private final String dietType;
    
    public SpecialDietRecipeDecorator(Recipe recipe, String dietType) {
        super(recipe);
        this.dietType = dietType;
    }
    
    @Override
    public String getName() {
        return recipe.getName() + " (" + dietType + ")";
    }
    
    @Override
    public String getDescription() {
        return recipe.getDescription() + " - Adattato per dieta " + dietType;
    }
}
