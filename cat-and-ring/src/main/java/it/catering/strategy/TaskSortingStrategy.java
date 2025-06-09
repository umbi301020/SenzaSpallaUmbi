package it.catering.strategy;

import it.catering.domain.Task;
import java.util.Comparator;
import java.util.List;

// Pattern Strategy - Strategia per ordinare i task
public interface TaskSortingStrategy {
    void sort(List<Task> tasks);
}



