package it.catering;

import it.catering.controller.*;
import it.catering.domain.*;
import it.catering.factory.*;
import it.catering.visitor.*;
import it.catering.observer.*;
import it.catering.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

public class CateringApplication {
    public static void main(String[] args) {
        System.out.println("=== CAT & RING - Sistema di Gestione Catering ===\n");
        
        // Inizializzazione del sistema
        CateringEntityFactory factory = new StandardCateringEntityFactory();
        MenuController menuController = new MenuController();
        KitchenTaskController taskController = new KitchenTaskController();
        
        // Aggiungi observer per monitorare i task
        TaskObserver observer = new TaskObserver() {
            @Override
            public void onTaskAssigned(Task task) {
                System.out.println("✓ Task assegnato: " + task.getRecipe().getName() + 
                                 " a " + task.getAssignedCook().getName());
            }
            
            @Override
            public void onTaskCompleted(Task task) {
                System.out.println("✓ Task completato: " + task.getRecipe().getName());
            }
            
            @Override
            public void onTaskListUpdated(List<Task> tasks) {
                System.out.println("📋 Lista task aggiornata. Totale: " + tasks.size());
            }
        };
        taskController.addObserver(observer);
        
        // Demo delle funzionalità
        demoMenuManagement(menuController, factory);
        demoKitchenTaskManagement(taskController, menuController);
        
        System.out.println("\n=== Demo completata ===");
    }
    
    private static void demoMenuManagement(MenuController menuController, 
                                         CateringEntityFactory factory) {
        System.out.println("🍽️  DEMO: Gestione Menu\n");
        
        // 1. Crea alcune ricette di esempio
        setupSampleRecipes(factory);
        
        // 2. Crea un nuovo menu
        String menuId = menuController.createNewMenu("EVENT_001", "Menu Matrimonio Elegante");
        System.out.println("Menu creato con ID: " + menuId);
        
        // 3. Definisci le sezioni del menu
        List<String> sections = Arrays.asList("Antipasti", "Primi Piatti", "Secondi Piatti", "Dessert");
        menuController.defineMenuSections(menuId, sections);
        System.out.println("Sezioni del menu definite");
        
        // 4. Aggiungi ricette alle sezioni
        menuController.addRecipeToSection(menuId, "RECIPE_001", "Antipasti");
        menuController.addRecipeToSection(menuId, "RECIPE_002", "Primi Piatti");
        menuController.addRecipeToSection(menuId, "RECIPE_003", "Secondi Piatti");
        menuController.addRecipeToSection(menuId, "RECIPE_004", "Dessert");
        System.out.println("Ricette aggiunte alle sezioni");
        
        // 5. Inserisci titolo e informazioni aggiuntive
        menuController.insertMenuTitle(menuId, "Menu Matrimonio Premium");
        menuController.annotateAdditionalInformation(menuId, 
            "Menu elegante con piatti caldi. Richiede cuoco durante il servizio.");
        
        // 6. Conferma la creazione del menu
        String confirmation = menuController.confirmMenuCreation(menuId);
        System.out.println(confirmation);
        
        // 7. Visualizza il menu con il Visitor Pattern
        Menu createdMenu = menuController.selectExistingMenu(menuId);
        if (createdMenu != null) {
            createdMenu.display();
            
            // Analisi nutrizionale
            NutritionalAnalysisVisitor nutritionVisitor = new NutritionalAnalysisVisitor();
            nutritionVisitor.visit(createdMenu);
            nutritionVisitor.printAnalysis();
            
            // Analisi costi
            CostAnalysisVisitor costVisitor = new CostAnalysisVisitor();
            costVisitor.visit(createdMenu);
            costVisitor.printAnalysis();
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
    
    private static void demoKitchenTaskManagement(KitchenTaskController taskController,
                                                MenuController menuController) {
        System.out.println("👨‍🍳 DEMO: Gestione Compiti Cucina\n");
        
        // 1. Accedi alla gestione task per un evento
        Menu menu = taskController.accessTaskManagement("EVENT_001");
        System.out.println("Accesso alla gestione task per evento");
        
        // 2. Visualizza dettagli del menu e ricette
        List<Recipe> recipes = taskController.viewMenuDetails("MENU_001");
        System.out.println("Ricette nel menu: " + recipes.size());
        
        // 3. Aggiungi task extra
        String extraTaskId = taskController.addExtraTask("Preparazione decorazioni tavola");
        System.out.println("Task extra aggiunto: " + extraTaskId);
        
        // 4. Visualizza turni e disponibilità
        List<Shift> shifts = taskController.viewShiftsAndAvailability();
        System.out.println("Turni disponibili: " + shifts.size());
        for (Shift shift : shifts) {
            System.out.println("  - " + shift.getDate() + " " + shift.getTimeSlot());
        }
        
        // 5. Ordina i task per priorità
        taskController.sortTasks("priority");
        System.out.println("Task ordinati per priorità");
        
        // 6. Assegna task ai cuochi
        List<Task> tasks = taskController.getTasks();
        if (!tasks.isEmpty()) {
            String assignment1 = taskController.assignTaskToCook(
                tasks.get(0).getTaskId(), "COOK_001", "SHIFT_1", 60, 10);
            System.out.println(assignment1);
            
            if (tasks.size() > 1) {
                String assignment2 = taskController.assignTaskToCook(
                    tasks.get(1).getTaskId(), "COOK_002", "SHIFT_1", 45, 8);
                System.out.println(assignment2);
            }
        }
        
        // 7. Verifica assegnazioni
        Map<String, Object> assignment = taskController.checkAssignment();
        System.out.println("Controllo assegnazioni:");
        System.out.println("  Task totali: " + assignment.get("totalTasks"));
        System.out.println("  Task assegnati: " + 
            ((List<?>) assignment.get("assignedTasks")).size());
        
        List<String> warnings = (List<String>) assignment.get("warnings");
        if (!warnings.isEmpty()) {
            System.out.println("  Avvisi: " + warnings);
        }
        
        // 8. Conferma piano dei task
        String planConfirmation = taskController.confirmTaskPlan();
        System.out.println(planConfirmation);
        
        // 9. Visualizza stato avanzamento
        Map<String, Object> progress = taskController.viewProgressStatus("SHIFT_1");
        System.out.println("Stato avanzamento turno SHIFT_1:");
        System.out.println("  Completati: " + progress.get("completedTasks") + 
                         "/" + progress.get("totalTasks"));
        System.out.println("  Progresso: " + 
            String.format("%.1f", (Double) progress.get("progress")) + "%");
        
        // 10. Simula completamento di un task
        if (!tasks.isEmpty()) {
            taskController.notifyTaskCompleted(tasks.get(0));
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
    
    private static void setupSampleRecipes(CateringEntityFactory factory) {
        RecipeRepository recipeRepo = RecipeRepository.getInstance();
        
        // Ricetta 1: Antipasto
        Recipe recipe1 = factory.createRecipe("RECIPE_001", "Bruschette al Pomodoro", "chef1");
        recipe1.setDescription("Bruschette croccanti con pomodori freschi");
        recipe1.addIngredient(new Ingredient("Pane", "8", "fette"));
        recipe1.addIngredient(new Ingredient("Pomodori", "4", "pezzi"));
        recipe1.addIngredient(new Ingredient("Basilico", "10", "foglie"));
        recipe1.addPreparationStep("Tostare il pane");
        recipe1.addPreparationStep("Tagliare i pomodori a cubetti");
        recipe1.addLastMinuteStep("Assemblare le bruschette");
        recipe1.setEstimatedTime(20);
        recipe1.setPortions(8);
        recipe1.addTag("antipasto");
        recipe1.addTag("vegetariano");
        recipe1.setStatus(RecipeStatus.PUBLISHED);
        recipeRepo.save(recipe1);
        
        // Ricetta 2: Primo Piatto
        Recipe recipe2 = factory.createRecipe("RECIPE_002", "Risotto ai Funghi Porcini", "chef1");
        recipe2.setDescription("Risotto cremoso con funghi porcini freschi");
        recipe2.addIngredient(new Ingredient("Riso Carnaroli", "320", "g"));
        recipe2.addIngredient(new Ingredient("Funghi Porcini", "300", "g"));
        recipe2.addIngredient(new Ingredient("Brodo Vegetale", "1", "litro"));
        recipe2.addPreparationStep("Pulire e tagliare i funghi");
        recipe2.addPreparationStep("Preparare il soffritto");
        recipe2.addLastMinuteStep("Mantecare il risotto");
        recipe2.setEstimatedTime(45);
        recipe2.setPortions(4);
        recipe2.addTag("primo");
        recipe2.addTag("vegetariano");
        recipe2.addTag("caldo");
        recipe2.setStatus(RecipeStatus.PUBLISHED);
        recipeRepo.save(recipe2);
        
        // Ricetta 3: Secondo Piatto
        Recipe recipe3 = factory.createRecipe("RECIPE_003", "Salmone in Crosta", "chef2");
        recipe3.setDescription("Filetto di salmone con crosta di erbe aromatiche");
        recipe3.addIngredient(new Ingredient("Salmone", "800", "g"));
        recipe3.addIngredient(new Ingredient("Pangrattato", "100", "g"));
        recipe3.addIngredient(new Ingredient("Erbe miste", "20", "g"));
        recipe3.addPreparationStep("Preparare la crosta");
        recipe3.addPreparationStep("Preparare i filetti");
        recipe3.addLastMinuteStep("Cuocere in forno");
        recipe3.setEstimatedTime(35);
        recipe3.setPortions(4);
        recipe3.addTag("secondo");
        recipe3.addTag("pesce");
        recipe3.addTag("caldo");
        recipe3.setStatus(RecipeStatus.PUBLISHED);
        recipeRepo.save(recipe3);
        
        // Ricetta 4: Dessert
        Recipe recipe4 = factory.createRecipe("RECIPE_004", "Tiramisù della Casa", "chef2");
        recipe4.setDescription("Tiramisù classico con mascarpone e caffè");
        recipe4.addIngredient(new Ingredient("Mascarpone", "500", "g"));
        recipe4.addIngredient(new Ingredient("Savoiardi", "200", "g"));
        recipe4.addIngredient(new Ingredient("Caffè", "400", "ml"));
        recipe4.addIngredient(new Ingredient("Uova", "4", "pezzi"));
        recipe4.addPreparationStep("Preparare la crema al mascarpone");
        recipe4.addPreparationStep("Bagnare i savoiardi con il caffè");
        recipe4.addPreparationStep("Assemblare gli strati");
        recipe4.addLastMinuteStep("Spolverare con cacao");
        recipe4.setEstimatedTime(30);
        recipe4.setPortions(8);
        recipe4.addTag("dessert");
        recipe4.addTag("freddo");
        recipe4.setStatus(RecipeStatus.PUBLISHED);
        recipeRepo.save(recipe4);
        
        System.out.println("Ricette di esempio create e salvate nel repository");
    }
}