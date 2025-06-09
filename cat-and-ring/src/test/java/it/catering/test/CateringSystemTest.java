package it.catering.test;

import it.catering.controller.*;
import it.catering.domain.*;
import it.catering.observer.*;
import it.catering.adapter.*;
import it.catering.visitor.*;
import it.catering.decorator.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class CateringSystemTest {
    
    private MenuController menuController;
    private KitchenTaskController taskController;
    
    @BeforeEach
    void setUp() {
        menuController = new MenuController();
        taskController = new KitchenTaskController();
    }
    
    @Test
    void testMenuCreationAndManagement() {
        // Test creazione menu
        String menuId = menuController.createNewMenu("EVENT_TEST", "Menu Test");
        assertNotNull(menuId);
        
        // Test aggiunta sezioni
        List<String> sections = List.of("Antipasti", "Primi");
        menuController.defineMenuSections(menuId, sections);
        
        Menu menu = menuController.selectExistingMenu(menuId);
        assertNotNull(menu);
        assertEquals("Menu Test", menu.getTitle());
        assertEquals(2, menu.getSections().size());
    }
    
    @Test
    void testTaskAssignment() {
        // Aggiungi task extra
        String taskId = taskController.addExtraTask("Test Task");
        assertNotNull(taskId);
        
        // Assegna task
        String result = taskController.assignTaskToCook(taskId, "COOK_TEST", "SHIFT_TEST", 30, 1);
        assertTrue(result.contains("successo"));
        
        // Verifica assegnazione
        var assignment = taskController.checkAssignment();
        assertNotNull(assignment);
        assertTrue((Integer) assignment.get("totalTasks") > 0);
    }
    
    @Test
    void testObserverPattern() {
        // Setup observer
        TestTaskObserver observer = new TestTaskObserver();
        taskController.addObserver(observer);
        
        // Aggiungi task
        String taskId = taskController.addExtraTask("Test Observer Task");
        
        // Verifica notifica
        assertTrue(observer.wasNotified());
    }
    
    @Test
    void testVisitorPattern() {
        // Crea menu di test
        String menuId = menuController.createNewMenu("EVENT_VISITOR", "Menu Visitor Test");
        menuController.defineMenuSections(menuId, List.of("Test Section"));
        
        Menu menu = menuController.selectExistingMenu(menuId);
        assertNotNull(menu);
        
        // Test visitor
        NutritionalAnalysisVisitor visitor = new NutritionalAnalysisVisitor();
        visitor.visit(menu);
        
        // Verifica risultati base (anche se menu vuoto)
        assertTrue(visitor.getTotalCalories() >= 0);
        assertTrue(visitor.getTotalPreparationTime() >= 0);
    }
    
    @Test
    void testDecoratorPattern() {
        // Crea ricetta base
        Recipe baseRecipe = new Recipe("TEST_RECIPE", "Test Recipe", "test_owner");
        baseRecipe.setEstimatedTime(30);
        baseRecipe.setPortions(4);
        
        // Applica decorator per scaling
        QuantityScaledRecipeDecorator scaledRecipe = 
            new QuantityScaledRecipeDecorator(baseRecipe, 2.0);
        
        assertEquals(60, scaledRecipe.getEstimatedTime());
        assertEquals(8, scaledRecipe.getPortions());
        assertTrue(scaledRecipe.getName().contains("x2"));
    }
    
    @Test
    void testAdapterPattern() {
        // Setup adapter
        LegacyPDFGenerator legacyService = new LegacyPDFGenerator();
        PDFServiceAdapter adapter = new PDFServiceAdapter(legacyService);
        MenuPDFService pdfService = new MenuPDFService(adapter);
        
        // Crea menu di test
        String menuId = menuController.createNewMenu("EVENT_PDF", "Menu PDF Test");
        Menu menu = menuController.selectExistingMenu(menuId);
        
        // Genera PDF
        byte[] pdfData = pdfService.generateMenuPDF(menu);
        assertNotNull(pdfData);
        assertTrue(pdfData.length > 0);
        
        // Test invio email
        boolean sent = pdfService.shareMenuPDF(pdfData, new String[]{"test@example.com"});
        assertTrue(sent);
    }
    
    // Classe helper per test observer
    private static class TestTaskObserver implements TaskObserver {
        private boolean notified = false;
        
        @Override
        public void onTaskAssigned(Task task) {
            notified = true;
        }
        
        @Override
        public void onTaskCompleted(Task task) {
            notified = true;
        }
        
        @Override
        public void onTaskListUpdated(List<Task> tasks) {
            notified = true;
        }
        
        public boolean wasNotified() {
            return notified;
        }
    }
}