package manager;

import task.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new LinkedList<>();


    @Override
    public void add(Task task) {
        final int size = 10;
        if (history.size() == size) {
            history.remove(0);
        }
        history.add(task);

    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}
