package it.catering.observer;

import it.catering.domain.Task;
import java.util.List;

public class ChefNotificationObserver implements TaskObserver {
    private String chefName;
    
    public ChefNotificationObserver(String chefName) {
        this.chefName = chefName;
    }
    
    @Override
    public void onTaskAssigned(Task task) {
        System.out.println("🔔 [" + chefName + "] Notifica: Task '" + 
            task.getRecipe().getName() + "' assegnato a " + 
            task.getAssignedCook().getName());
    }
    
    @Override
    public void onTaskCompleted(Task task) {
        System.out.println("✅ [" + chefName + "] Task completato: '" + 
            task.getRecipe().getName() + "' da " + 
            task.getAssignedCook().getName());
    }
    
    @Override
    public void onTaskListUpdated(List<Task> tasks) {
        long pendingTasks = tasks.stream()
            .filter(task -> task.getStatus() == it.catering.domain.TaskStatus.PENDING)
            .count();
        
        if (pendingTasks > 0) {
            System.out.println("⚠️  [" + chefName + "] Attenzione: " + 
                pendingTasks + " task in attesa di assegnazione");
        }
    }
}