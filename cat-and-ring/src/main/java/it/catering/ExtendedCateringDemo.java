package it.catering;

// Estensione della classe principale per dimostrare tutti i pattern
public class ExtendedCateringDemo {
    
    public static void demonstrateAllPatterns() {
        System.out.println("🎯 DEMO COMPLETA: Tutti i Design Pattern\n");
        
        demoAdapterPattern();
        demoDecoratorPattern();
        demoStatePattern();
        demoAdvancedObserverPattern();
    }
    
    private static void demoAdapterPattern() {
        System.out.println("🔌 DEMO: Adapter Pattern");
        
        // Setup del sistema legacy e adapter
        LegacyPDFGenerator legacyService = new LegacyPDFGenerator();
        PDFServiceAdapter adapter = new PDFServiceAdapter(legacyService);
        MenuPDFService pdfService = new MenuPDFService(adapter);
        
        // Crea menu per PDF
        MenuController menuController = new MenuController();
        String menuId = menuController.createNewMenu("EVENT_ADAPTER", "Menu Speciale");
        menuController.defineMenuSections(menuId, List.of("Antipasti", "Primi"));
        
        Menu menu = menuController.selectExistingMenu(menuId);
        
        // Genera e condividi PDF
        byte[] pdfData = pdfService.generateMenuPDF(menu);
        boolean shared = pdfService.shareMenuPDF(pdfData, 
            new String[]{"cliente@example.com", "chef@catering.com"});
        
        System.out.println("✅ PDF generato: " + pdfData.length + " bytes");
        System.out.println("✅ PDF condiviso: " + shared + "\n");
    }
    
    private static void demoDecoratorPattern() {
        System.out.println("🎨 DEMO: Decorator Pattern");
        
        // Ricetta base
        Recipe baseRecipe = new Recipe("DEC_001", "Pasta al Pomodoro", "chef_demo");
        baseRecipe.setEstimatedTime(25);
        baseRecipe.setPortions(4);
        baseRecipe.addIngredient(new Ingredient("Pasta", "400", "g"));
        baseRecipe.addIngredient(new Ingredient("Pomodori", "500", "g"));
        
        System.out.println("📋 Ricetta base: " + baseRecipe.getName());
        System.out.println("   Tempo: " + baseRecipe.getEstimatedTime() + " min");
        System.out.println("   Porzioni: " + baseRecipe.getPortions());
        
        // Decoratore per scaling
        QuantityScaledRecipeDecorator scaledRecipe = 
            new QuantityScaledRecipeDecorator(baseRecipe, 2.5);
        
        System.out.println("\n🔢 Ricetta scalata (x2.5): " + scaledRecipe.getName());
        System.out.println("   Tempo: " + scaledRecipe.getEstimatedTime() + " min");
        System.out.println("   Porzioni: " + scaledRecipe.getPortions());
        
        // Decoratore per dieta speciale
        SpecialDietRecipeDecorator glutenFreeRecipe = 
            new SpecialDietRecipeDecorator(scaledRecipe, "senza glutine");
        
        System.out.println("\n🌾 Ricetta dieta speciale: " + glutenFreeRecipe.getName());
        System.out.println("   Descrizione: " + glutenFreeRecipe.getDescription() + "\n");
    }
    
    private static void demoStatePattern() {
        System.out.println("🔄 DEMO: State Pattern");
        
        MenuController menuController = new MenuController();
        String menuId = menuController.createNewMenu("EVENT_STATE", "Menu State Demo");
        Menu menu = menuController.selectExistingMenu(menuId);
        
        // Simula gestione stati (semplificata per demo)
        System.out.println("📝 Stato iniziale: " + menu.getStatus());
        
        // Pubblica menu
        menuController.confirmMenuCreation(menuId);
        menu = menuController.selectExistingMenu(menuId);
        System.out.println("📤 Dopo pubblicazione: " + menu.getStatus());
        
        // Usa in evento
        menu.setStatus(MenuStatus.IN_USE);
        System.out.println("🎯 In uso per evento: " + menu.getStatus() + "\n");
    }
    
    private static void demoAdvancedObserverPattern() {
        System.out.println("👥 DEMO: Observer Pattern Avanzato");
        
        KitchenTaskController taskController = new KitchenTaskController();
        
        // Aggiungi diversi observer
        ChefNotificationObserver chefObserver = new ChefNotificationObserver("Chef Marco");
        OrganizerNotificationObserver organizerObserver = 
            new OrganizerNotificationObserver("Organizzatore Anna");
        
        taskController.addObserver(chefObserver);
        taskController.addObserver(organizerObserver);
        
        // Simula attività che generano notifiche
        String task1 = taskController.addExtraTask("Preparazione antipasti");
        String task2 = taskController.addExtraTask("Decorazione tavoli");
        
        taskController.assignTaskToCook(task1, "COOK_001", "SHIFT_MORNING", 45, 20);
        taskController.assignTaskToCook(task2, "COOK_002", "SHIFT_MORNING", 30, 10);
        
        // Completa alcuni task
        List<Task> tasks = taskController.getTasks();
        if (!tasks.isEmpty()) {
            taskController.notifyTaskCompleted(tasks.get(0));
        }
        
        System.out.println("\n");
    }
}