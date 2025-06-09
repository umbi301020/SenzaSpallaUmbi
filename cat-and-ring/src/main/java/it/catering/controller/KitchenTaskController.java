// Pattern GRASP: Controller - Gestisce i compiti della cucina
public class KitchenTaskController implements TaskSubject {
    private final List<TaskObserver> observers;
    private final List<Task> tasks;
    private final RecipeRepository recipeRepository;
    private final CateringEntityFactory entityFactory;
    private TaskSortingStrategy sortingStrategy;
    
    public KitchenTaskController() {
        this.observers = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.recipeRepository = RecipeRepository.getInstance();
        this.entityFactory = new StandardCateringEntityFactory();
        this.sortingStrategy = new SortByPriorityStrategy(); // Default strategy
    }
    
    public Menu accessTaskManagement(String eventId) {
        // Simula il recupero del menu approvato per l'evento
        EventRepository eventRepo = EventRepository.getInstance();
        Event event = eventRepo.findById(eventId);
        if (event != null && !event.getServices().isEmpty()) {
            return event.getServices().get(0).getMenu();
        }
        return null;
    }
    
    public List<Recipe> viewMenuDetails(String menuId) {
        MenuRepository menuRepo = MenuRepository.getInstance();
        Menu menu = menuRepo.findById(menuId);
        if (menu == null) return new ArrayList<>();
        
        List<Recipe> recipes = new ArrayList<>();
        extractRecipesFromMenu(menu, recipes);
        return recipes;
    }
    
    public String addExtraTask(String preparationDetails) {
        // Crea un task extra (semplificato)
        String taskId = "EXTRA_" + System.currentTimeMillis();
        Recipe dummyRecipe = entityFactory.createRecipe("EXTRA_RECIPE_" + System.currentTimeMillis(), 
                                                        preparationDetails, "chef");
        dummyRecipe.setEstimatedTime(30); // Default 30 minuti
        
        Task extraTask = entityFactory.createTask(taskId, dummyRecipe, 1);
        tasks.add(extraTask);
        notifyObservers();
        return taskId;
    }
    
    public void sortTasks(String criterion) {
        switch (criterion.toLowerCase()) {
            case "difficulty" -> setSortingStrategy(new SortByDifficultyStrategy());
            case "priority" -> setSortingStrategy(new SortByPriorityStrategy());
            case "time" -> setSortingStrategy(new SortByTimeStrategy());
            default -> setSortingStrategy(new SortByPriorityStrategy());
        }
        
        sortingStrategy.sort(tasks);
        notifyObservers();
    }
    
    public void setSortingStrategy(TaskSortingStrategy strategy) {
        this.sortingStrategy = strategy;
    }
    
    public List<Shift> viewShiftsAndAvailability() {
        // Simula la restituzione dei turni disponibili
        List<Shift> shifts = new ArrayList<>();
        shifts.add(new Shift("SHIFT_1", "2024-01-15", "09:00-13:00", "Cucina Principale"));
        shifts.add(new Shift("SHIFT_2", "2024-01-15", "14:00-18:00", "Cucina Principale"));
        return shifts;
    }
    
    public String assignTaskToCook(String taskId, String cookId, String shiftId, 
                                 int timeEstimate, int quantity) {
        Task task = findTaskById(taskId);
        if (task == null) {
            return "Task non trovato";
        }
        
        Cook cook = new Cook(cookId, "Cook_" + cookId);
        task.assignTo(cook, shiftId);
        task.setEstimatedTime(timeEstimate);
        
        notifyTaskAssigned(task);
        return "Task assegnato con successo a " + cook.getName();
    }
    
    public void selectModularRecipe(String recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId);
        if (recipe != null) {
            // Crea task per la ricetta modulare
            String taskId = "TASK_" + System.currentTimeMillis();
            Task task = entityFactory.createTask(taskId, recipe, 1);
            tasks.add(task);
            notifyObservers();
        }
    }
    
    public void assignPreparationToCook(String preparationId, String cookId, String shiftId) {
        Task task = findTaskById(preparationId);
        if (task != null) {
            Cook cook = new Cook(cookId, "Cook_" + cookId);
            task.assignTo(cook, shiftId);
            notifyTaskAssigned(task);
        }
    }
    
    public List<String> splitPreparation(String preparationId, int quantityParts) {
        Task originalTask = findTaskById(preparationId);
        if (originalTask == null) return new ArrayList<>();
        
        List<String> partIds = new ArrayList<>();
        int quantityPerPart = originalTask.getQuantity() / quantityParts;
        
        for (int i = 0; i < quantityParts; i++) {
            String partId = preparationId + "_PART_" + (i + 1);
            Task partTask = entityFactory.createTask(partId, originalTask.getRecipe(), quantityPerPart);
            tasks.add(partTask);
            partIds.add(partId);
        }
        
        // Rimuovi il task originale
        tasks.remove(originalTask);
        notifyObservers();
        return partIds;
    }
    
    public void assignPreparationPartToShift(String partId, String shiftId) {
        Task part = findTaskById(partId);
        if (part != null) {
            // Assegna la parte al turno (semplificazione)
            part.setStatus(TaskStatus.ASSIGNED);
            System.out.println("Parte " + partId + " assegnata al turno " + shiftId);
        }
    }
    
    public String confirmTaskPlan() {
        long assignedTasks = tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.ASSIGNED)
                .count();
        return "Piano confermato. Task assegnati: " + assignedTasks + "/" + tasks.size();
    }
    
    public Map<String, Object> checkAssignment() {
        Map<String, Object> overview = new HashMap<>();
        
        List<Task> assignedTasks = tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.ASSIGNED || 
                               task.getStatus() == TaskStatus.COMPLETED)
                .toList();
        
        List<String> warnings = new ArrayList<>();
        
        // Controlla sovrapposizioni temporali
        Map<String, Integer> cookWorkload = new HashMap<>();
        for (Task task : assignedTasks) {
            if (task.getAssignedCook() != null) {
                String cookId = task.getAssignedCook().getCookId();
                cookWorkload.merge(cookId, task.getEstimatedTime(), Integer::sum);
                
                if (cookWorkload.get(cookId) > 480) { // 8 ore
                    warnings.add("Cuoco " + cookId + " sovraccarico");
                }
            }
        }
        
        overview.put("assignedTasks", assignedTasks);
        overview.put("warnings", warnings);
        overview.put("totalTasks", tasks.size());
        
        return overview;
    }
    
    public Map<String, Object> viewProgressStatus(String shiftId) {
        List<Task> shiftTasks = tasks.stream()
                .filter(task -> shiftId.equals(task.getShiftId()))
                .toList();
        
        long completedTasks = shiftTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.COMPLETED)
                .count();
        
        List<String> reportedIssues = new ArrayList<>();
        // Simula problemi segnalati
        if (completedTasks < shiftTasks.size() / 2) {
            reportedIssues.add("Ritardo nella preparazione");
        }
        
        Map<String, Object> status = new HashMap<>();
        status.put("completedTasks", completedTasks);
        status.put("totalTasks", shiftTasks.size());
        status.put("reportedIssues", reportedIssues);
        status.put("progress", (double) completedTasks / shiftTasks.size() * 100);
        
        return status;
    }
    
    public void modifyUpcomingShifts(Map<String, Object> changes) {
        System.out.println("Modifiche applicate ai turni futuri: " + changes);
        // In un sistema reale, qui si aggiornerebbero i turni nel repository
    }
    
    // Metodi dell'Observer Pattern
    @Override
    public void addObserver(TaskObserver observer) {
        observers.add(observer);
    }
    
    @Override
    public void removeObserver(TaskObserver observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers() {
        for (TaskObserver observer : observers) {
            observer.onTaskListUpdated(new ArrayList<>(tasks));
        }
    }
    
    private void notifyTaskAssigned(Task task) {
        for (TaskObserver observer : observers) {
            observer.onTaskAssigned(task);
        }
    }
    
    public void notifyTaskCompleted(Task task) {
        task.complete();
        for (TaskObserver observer : observers) {
            observer.onTaskCompleted(task);
        }
    }
    
    // Metodi di utilità
    private Task findTaskById(String taskId) {
        return tasks.stream()
                .filter(task -> task.getTaskId().equals(taskId))
                .findFirst()
                .orElse(null);
    }
    
    private void extractRecipesFromMenu(MenuComponent component, List<Recipe> recipes) {
        if (component instanceof MenuItem menuItem) {
            recipes.add(menuItem.getRecipe());
        } else if (component instanceof Menu menu) {
            for (MenuComponent section : menu.getSections()) {
                extractRecipesFromMenu(section, recipes);
            }
        } else if (component instanceof MenuSection section) {
            for (MenuComponent item : section.getItems()) {
                extractRecipesFromMenu(item, recipes);
            }
        }
    }
    
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}