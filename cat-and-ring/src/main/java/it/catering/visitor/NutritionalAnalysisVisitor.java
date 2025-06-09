public class NutritionalAnalysisVisitor implements MenuVisitor {
    private int totalCalories = 0;
    private int totalPreparationTime = 0;
    private boolean hasVegetarianOptions = false;
    
    @Override
    public void visit(Menu menu) {
        System.out.println("Analizzando menu: " + menu.getTitle());
        for (MenuComponent section : menu.getSections()) {
            if (section instanceof MenuSection menuSection) {
                visit(menuSection);
            }
        }
    }
    
    @Override
    public void visit(MenuSection section) {
        System.out.println("Analizzando sezione: " + section.getName());
        for (MenuComponent item : section.getItems()) {
            if (item instanceof MenuItem menuItem) {
                visit(menuItem);
            }
        }
    }
    
    @Override
    public void visit(MenuItem item) {
        Recipe recipe = item.getRecipe();
        totalPreparationTime += recipe.getEstimatedTime();
        
        if (recipe.getTags().contains("vegetariano")) {
            hasVegetarianOptions = true;
        }
        
        // Simula calcolo calorie
        totalCalories += recipe.getIngredients().size() * 100;
    }
    
    public void printAnalysis() {
        System.out.println("=== ANALISI NUTRIZIONALE ===");
        System.out.println("Calorie totali stimate: " + totalCalories);
        System.out.println("Tempo di preparazione totale: " + totalPreparationTime + " minuti");
        System.out.println("Opzioni vegetariane: " + (hasVegetarianOptions ? "Sì" : "No"));
    }
    
    // Getters per i risultati
    public int getTotalCalories() { return totalCalories; }
    public int getTotalPreparationTime() { return totalPreparationTime; }
    public boolean hasVegetarianOptions() { return hasVegetarianOptions; }
}