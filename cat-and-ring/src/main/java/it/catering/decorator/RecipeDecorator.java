package it.catering.decorator;

import it.catering.domain.Recipe;
import it.catering.domain.Ingredient;
import java.util.List;

// Pattern Decorator - Decoratore per ricette
public abstract class RecipeDecorator extends Recipe {
    protected Recipe recipe;
    
    public RecipeDecorator(Recipe recipe) {
        super(recipe.getRecipeId(), recipe.getName(), recipe.getOwner());
        this.recipe = recipe;
    }
    
    @Override
    public String getName() {
        return recipe.getName();
    }
    
    @Override
    public List<Ingredient> getIngredients() {
        return recipe.getIngredients();
    }
}