// Pattern GRASP: Information Expert - Task conosce i propri dati
public class Task {
    private String taskId;
    private Recipe recipe;
    private Cook assignedCook;
    private String shiftId;
    private int estimatedTime;
    private int quantity;
    private TaskStatus status;
    
    public Task(String taskId, Recipe recipe, int quantity) {
        this.taskId = taskId;
        this.recipe = recipe;
        this.quantity = quantity;
        this.estimatedTime = recipe.getEstimatedTime();
        this.status = TaskStatus.PENDING;
    }
    
    public void assignTo(Cook cook, String shiftId) {
        this.assignedCook = cook;
        this.shiftId = shiftId;
        this.status = TaskStatus.ASSIGNED;
    }
    
    public void complete() {
        this.status = TaskStatus.COMPLETED;
    }
    
    public String getTaskId() { return taskId; }
    public Recipe getRecipe() { return recipe; }
    public Cook getAssignedCook() { return assignedCook; }
    public String getShiftId() { return shiftId; }
    public int getEstimatedTime() { return estimatedTime; }
    public void setEstimatedTime(int estimatedTime) { this.estimatedTime = estimatedTime; }
    public int getQuantity() { return quantity; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
}

enum TaskStatus {
    PENDING, ASSIGNED, IN_PROGRESS, COMPLETED, CANCELLED
}
