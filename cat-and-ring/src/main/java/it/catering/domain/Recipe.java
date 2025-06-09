// Pattern GRASP: Information Expert - Recipe conosce i propri dati
public class Recipe {
    private String recipeId;
    private String name;
    private String author;
    private String owner;
    private String description;
    private List<Ingredient> ingredients;
    private List<String> preparationSteps;
    private List<String> lastMinuteSteps;
    private int estimatedTime; // in minuti
    private int portions;
    private RecipeStatus status;
    private Set<String> tags;
    
    public Recipe(String recipeId, String name, String owner) {
        this.recipeId = recipeId;
        this.name = name;
        this.owner = owner;
        this.ingredients = new ArrayList<>();
        this.preparationSteps = new ArrayList<>();
        this.lastMinuteSteps = new ArrayList<>();
        this.status = RecipeStatus.DRAFT;
        this.tags = new HashSet<>();
    }
    
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }
    
    public void addPreparationStep(String step) {
        preparationSteps.add(step);
    }
    
    public void addLastMinuteStep(String step) {
        lastMinuteSteps.add(step);
    }
    
    public void addTag(String tag) {
        tags.add(tag);
    }
    
    // Getters e setters
    public String getRecipeId() { return recipeId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getOwner() { return owner; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Ingredient> getIngredients() { return new ArrayList<>(ingredients); }
    public List<String> getPreparationSteps() { return new ArrayList<>(preparationSteps); }
    public List<String> getLastMinuteSteps() { return new ArrayList<>(lastMinuteSteps); }
    public int getEstimatedTime() { return estimatedTime; }
    public void setEstimatedTime(int estimatedTime) { this.estimatedTime = estimatedTime; }
    public int getPortions() { return portions; }
    public void setPortions(int portions) { this.portions = portions; }
    public RecipeStatus getStatus() { return status; }
    public void setStatus(RecipeStatus status) { this.status = status; }
    public Set<String> getTags() { return new HashSet<>(tags); }
}

enum RecipeStatus {
    DRAFT, PUBLISHED, IN_USE
}
