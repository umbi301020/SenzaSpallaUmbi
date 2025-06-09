public class SortByDifficultyStrategy implements TaskSortingStrategy {
    @Override
    public void sort(List<Task> tasks) {
        tasks.sort(Comparator.comparing((Task t) -> t.getRecipe().getEstimatedTime()).reversed());
    }
}