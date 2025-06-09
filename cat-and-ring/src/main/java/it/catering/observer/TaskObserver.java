package it.catering.observer;

import it.catering.domain.Task;
import java.util.List;

public interface TaskObserver {
    void onTaskAssigned(Task task);
    void onTaskCompleted(Task task);
    void onTaskListUpdated(List<Task> tasks);
}

