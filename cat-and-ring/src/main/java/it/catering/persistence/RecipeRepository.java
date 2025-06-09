package it.catering.persistence;

import it.catering.domain.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// Pattern Singleton - Un solo repository per le ricette
public class RecipeRepository {
    private static RecipeRepository instance;
    private final Map<String, Recipe> recipes;
    
    private RecipeRepository() {
        this.recipes = new ConcurrentHashMap<>();
    }
    
    public static RecipeRepository getInstance() {
        if (instance == null) {
            synchronized (RecipeRepository.class) {
                if (instance == null) {
                    instance = new RecipeRepository();
                }
            }
        }
        return instance;
    }
    
    public void save(Recipe recipe) {
        recipes.put(recipe.getRecipeId(), recipe);
    }
    
    public Recipe findById(String recipeId) {
        return recipes.get(recipeId);
    }
    
    public List<Recipe> findAll() {
        return new ArrayList<>(recipes.values());
    }
    
    public List<Recipe> findByOwner(String owner) {
        return recipes.values().stream()
                .filter(recipe -> recipe.getOwner().equals(owner))
                .toList();
    }
    
    public List<Recipe> findByTag(String tag) {
        return recipes.values().stream()
                .filter(recipe -> recipe.getTags().contains(tag))
                .toList();
    }
    
    public List<Recipe> findByStatus(RecipeStatus status) {
        return recipes.values().stream()
                .filter(recipe -> recipe.getStatus() == status)
                .toList();
    }
}