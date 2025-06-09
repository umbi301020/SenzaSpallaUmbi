public class OrganizerNotificationObserver implements TaskObserver {
    private String organizerName;
    
    public OrganizerNotificationObserver(String organizerName) {
        this.organizerName = organizerName;
    }
    
    @Override
    public void onTaskAssigned(Task task) {
        System.out.println("📊 [" + organizerName + "] Task assegnato: '" + 
            task.getRecipe().getName() + "' - Tempo stimato: " + 
            task.getEstimatedTime() + " min");
    }
    
    @Override
    public void onTaskCompleted(Task task) {
        System.out.println("✅ [" + organizerName + "] Progresso: Task '" + 
            task.getRecipe().getName() + "' completato");
    }
    
    @Override
    public void onTaskListUpdated(List<Task> tasks) {
        long completedTasks = tasks.stream()
            .filter(task -> task.getStatus() == it.catering.domain.TaskStatus.COMPLETED)
            .count();
        
        double completionPercentage = tasks.isEmpty() ? 0 : 
            (double) completedTasks / tasks.size() * 100;
        
        System.out.println("📈 [" + organizerName + "] Avanzamento generale: " + 
            String.format("%.1f", completionPercentage) + "% (" + 
            completedTasks + "/" + tasks.size() + ")");
    }
}
