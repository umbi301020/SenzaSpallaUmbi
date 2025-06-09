package it.catering.factory;

import it.catering.domain.*;

// Pattern Abstract Factory - Factory per creare entità del dominio
public interface CateringEntityFactory {
    Menu createMenu(String menuId, String title);
    Recipe createRecipe(String recipeId, String name, String owner);
    Task createTask(String taskId, Recipe recipe, int quantity);
    Event createEvent(String eventId, String title, String location, 
                     java.time.LocalDateTime date, String clientName);
}
