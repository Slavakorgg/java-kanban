package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private int nextId;

    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public List<Task> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public Task getTask(int taskId) {
        return tasks.get(taskId);
    }

    public Subtask getSubtask(int taskId) {
        return subtasks.get(taskId);

    }

    public Epic getEpic(int taskId) {
        return epics.get(taskId);
    }

    public HashMap<Integer, Subtask> getSubtasksFromEpic(Epic epic) {
        return (HashMap<Integer, Subtask>) epic.getSubtaskList();
    }

    public Task createTask(Task task) {
        task.setId(getNextId());
        tasks.put(task.getId(), task);
        return task;
    }

    public Task createSubtask(Subtask subtask) {
        subtask.setId(getNextId());
        subtasks.put(subtask.getId(), subtask);
        subtask.getEpic().getSubtaskList().put(subtask.getId(), subtask);
        epicStatus(subtask.getEpic());
        return subtask;
    }

    public Task createEpic(Epic epic) {
        epic.setId(getNextId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Status epicStatus(Epic epic) {
        int newStatus = 0;
        int done = 0;
        for (Subtask subtask : epic.getSubtaskList().values()) {

            switch (subtask.getStatus()) {
                case NEW:

                    newStatus++;
                    break;
                case DONE:

                    done++;
                    break;
            }

            if (newStatus == epic.getSubtaskList().size() || epic.getSubtaskList().isEmpty()) {
                epic.setStatus(Status.NEW);
            } else if (done == epic.getSubtaskList().size()) {
                epic.setStatus(Status.DONE);
            } else epic.setStatus(Status.IN_PROGRESS);
        }


        return epic.getStatus();
    }


    public Task updateTask(Task task) {
        Integer taskId = task.getId();
        if (taskId == null || !tasks.containsKey(taskId)) {
            return null;
        }
        tasks.put(taskId, task);
        return task;
    }

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

    public boolean deleteAllTasks() {
        tasks.clear();

        return true;
    }

    public boolean deleteAllSubtasks() {
        subtasks.clear();

        return true;
    }

    public boolean deleteAllEpic() {
        deleteAllSubtasks();
        epics.clear();

        return true;
    }

    public boolean deleteTask(int taskId) {
        if (!tasks.containsKey(taskId)) {
            return false;
        }
        tasks.remove(taskId);
        return true;
    }

    public boolean deleteSubtask(int taskId) {
        if (!subtasks.containsKey(taskId)) {
            return false;
        }
        subtasks.remove(taskId);
        return true;
    }

    public boolean deleteEpic(int taskId) {
        if (!epics.containsKey(taskId)) {
            return false;
        }
        for (Subtask subtask : getEpic(taskId).getSubtaskList().values()) {
            deleteSubtask(subtask.getId());
        }
        epics.remove(taskId);
        return true;
    }

    public int getNextId() {
        return nextId++;

    }

}
