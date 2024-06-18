package manager;

import task.Task;

import java.util.List;

public interface HistoryManager {
    Task add(Task task);
    List<Task> getHistory();
}
