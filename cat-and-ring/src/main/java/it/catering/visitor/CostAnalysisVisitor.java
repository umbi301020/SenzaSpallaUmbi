public class CostAnalysisVisitor implements MenuVisitor {
    private double totalCost = 0.0;
    private int itemCount = 0;
    
    @Override
    public void visit(Menu menu) {
        for (MenuComponent section : menu.getSections()) {
            if (section instanceof MenuSection menuSection) {
                visit(menuSection);
            }
        }
    }
    
    @Override
    public void visit(MenuSection section) {
        for (MenuComponent item : section.getItems()) {
            if (item instanceof MenuItem menuItem) {
                visit(menuItem);
            }
        }
    }
    
    @Override
    public void visit(MenuItem item) {
        itemCount++;
        // Simula calcolo costo basato sugli ingredienti
        totalCost += item.getRecipe().getIngredients().size() * 5.0; // €5 per ingrediente
    }
    
    public void printAnalysis() {
        System.out.println("=== ANALISI COSTI ===");
        System.out.println("Costo totale stimato: €" + String.format("%.2f", totalCost));
        System.out.println("Numero di piatti: " + itemCount);
        System.out.println("Costo medio per piatto: €" + String.format("%.2f", totalCost / itemCount));
    }
    
    public double getTotalCost() { return totalCost; }
    public double getAverageCostPerItem() { return itemCount > 0 ? totalCost / itemCount : 0; }
}
