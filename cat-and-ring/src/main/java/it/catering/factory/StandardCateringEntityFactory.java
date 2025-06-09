public class StandardCateringEntityFactory implements CateringEntityFactory {
    
    @Override
    public Menu createMenu(String menuId, String title) {
        return new Menu(menuId, title);
    }
    
    @Override
    public Recipe createRecipe(String recipeId, String name, String owner) {
        return new Recipe(recipeId, name, owner);
    }
    
    @Override
    public Task createTask(String taskId, Recipe recipe, int quantity) {
        return new Task(taskId, recipe, quantity);
    }
    
    @Override
    public Event createEvent(String eventId, String title, String location, 
                           java.time.LocalDateTime date, String clientName) {
        return new Event(eventId, title, location, date, clientName);
    }
}