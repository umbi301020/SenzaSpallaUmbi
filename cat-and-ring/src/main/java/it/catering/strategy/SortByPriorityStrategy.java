public class SortByPriorityStrategy implements TaskSortingStrategy {
    @Override
    public void sort(List<Task> tasks) {
        // Ordina per importanza (task con hot dishes prima)
        tasks.sort((t1, t2) -> {
            boolean t1HasHot = t1.getRecipe().getTags().contains("caldo");
            boolean t2HasHot = t2.getRecipe().getTags().contains("caldo");
            if (t1HasHot && !t2HasHot) return -1;
            if (!t1HasHot && t2HasHot) return 1;
            return Integer.compare(t1.getEstimatedTime(), t2.getEstimatedTime());
        });
    }
}