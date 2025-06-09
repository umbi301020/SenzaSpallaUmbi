public class SortByTimeStrategy implements TaskSortingStrategy {
    @Override
    public void sort(List<Task> tasks) {
        tasks.sort(Comparator.comparing(Task::getEstimatedTime));
    }
}