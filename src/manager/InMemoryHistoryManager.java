package manager;

import task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private final List<Task> history = new ArrayList<>();



    @Override
    public Task add(Task task) {
        if (history.size()==10){
            history.remove(0);
        }
        history.add(task);
        return null;
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}
