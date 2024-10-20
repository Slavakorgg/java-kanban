package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private int nextId;

    public InMemoryTaskManager(HistoryManager defaultHistory) {
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getTasks();
    }

    @Override
    public List<Task> getTasks() {
        for (Task task : new ArrayList<>(tasks.values())) {
            historyManager.add(task);
        }
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        for (Subtask subtask : new ArrayList<>(subtasks.values())) {
            historyManager.add(subtask);
        }

        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Task> getEpics() {
        for (Epic epic : new ArrayList<>(epics.values())) {
            historyManager.add(epic);
        }

        return new ArrayList<>(epics.values());
    }

    @Override
    public Task getTask(int taskId) {
        historyManager.add(tasks.get(taskId));

        return tasks.get(taskId);

    }

    @Override
    public Subtask getSubtask(int taskId) {
        historyManager.add(subtasks.get(taskId));

        return subtasks.get(taskId);

    }

    @Override
    public Epic getEpic(int taskId) {
        historyManager.add(epics.get(taskId));

        return epics.get(taskId);
    }

    @Override
    public HashMap<Integer, Subtask> getSubtasksFromEpic(Epic epic) {
        return (HashMap<Integer, Subtask>) epic.getSubtaskList();
    }

    @Override
    public Task createTask(Task task) {

        task.setId(getNextId());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Task createSubtask(Subtask subtask) {
        subtask.setId(getNextId());
        subtasks.put(subtask.getId(), subtask);
        subtask.getEpic().getSubtaskList().put(subtask.getId(), subtask);
        epicStatus(subtask.getEpic());
        return subtask;
    }

    @Override
    public Task createEpic(Epic epic) {
        epic.setId(getNextId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    private Status epicStatus(Epic epic) {
        List<Status> statusList = new ArrayList<>();
        for (Subtask subtask : epic.getSubtaskList().values()) {
            if (!statusList.contains(subtask.getStatus())) {
                statusList.add(subtask.getStatus());
            }
        }
        if (statusList.size() != 1) {
            epic.setStatus(Status.IN_PROGRESS);
        } else epic.setStatus(statusList.get(0));


        return epic.getStatus();
    }


    @Override
    public Task updateTask(Task task) {
        Integer taskId = task.getId();
        if (taskId == null || !tasks.containsKey(taskId)) {
            return null;
        }
        tasks.put(taskId, task);
        return task;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        Integer subtaskId = subtask.getId();
        if (subtaskId == null || !subtasks.containsKey(subtaskId)) {
            return null;
        }
        subtasks.put(subtaskId, subtask);
        subtask.getEpic().getSubtaskList().put(subtask.getId(), subtask);

        epicStatus(subtask.getEpic());

        return subtask;
    }

    @Override
    public boolean deleteAllTasks() {
        tasks.clear();

        return true;
    }

    @Override
    public boolean deleteAllSubtasks() {
        subtasks.clear();

        return true;
    }

    @Override
    public boolean deleteAllEpic() {
        deleteAllSubtasks();
        epics.clear();

        return true;
    }

    @Override
    public boolean deleteTask(int taskId) {
        if (!tasks.containsKey(taskId)) {
            return false;
        }
        if (historyManager.getTasks().contains(tasks.get(taskId))) {
            historyManager.remove(taskId);
        }
        tasks.remove(taskId);

        return true;
    }

    @Override
    public boolean deleteSubtask(int taskId) {
        if (!subtasks.containsKey(taskId)) {
            return false;
        }
        if (historyManager.getTasks().contains(subtasks.get(taskId))) {
            historyManager.remove(taskId);
        }


        subtasks.remove(taskId);
        return true;
    }

    @Override
    public boolean deleteEpic(int taskId) {
        // historyManager.remove(taskId);
        if (!epics.containsKey(taskId)) {
            return false;
        }
        for (Subtask subtask : getEpic(taskId).getSubtaskList().values()) {
            //   historyManager.remove(taskId);
            deleteSubtask(subtask.getId());
        }
        if (historyManager.getTasks().contains(epics.get(taskId))) {
            historyManager.remove(taskId);
        }
        epics.remove(taskId);
        return true;
    }

    @Override
    public int getNextId() {
        return ++nextId;

    }


}
