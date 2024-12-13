package manager;

import exception.IntersectionException;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private int nextId;

    public InMemoryTaskManager(HistoryManager defaultHistory) {
    }

    Comparator<Task> comparator = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {

            return o1.getStartTime().compareTo(o2.getStartTime());
        }
    };
    private Set<Task> prioritizedTasks = new TreeSet<>(comparator);

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
    public Task createTask(Task task) throws IntersectionException {
        taskIntersection(task);


        task.setId(getNextId());
        tasks.put(task.getId(), task);


        return task;
    }

    @Override
    public Task createSubtask(Subtask subtask) throws IntersectionException {
        taskIntersection(subtask);

        subtask.setId(getNextId());

        subtasks.put(subtask.getId(), subtask);
        subtask.getEpic().getSubtaskList().put(subtask.getId(), subtask);
        if (subtask.getStartTime() != null) { //для создания сабтасок без времени в конструкторе.
            // Может есть смысл в принципе убрать возможность создавать таски без времени, но пока не уверен
            updateStartTimeForEpic(subtask.getEpic());

        }
        if (subtask.getDuration() != null) {  //для создания сабтасок без времени в конструкторе
            plusEpicDuration(subtask.getEpic(), subtask);
        }
        epicStatus(subtask.getEpic());

        return subtask;
    }

    @Override
    public Task createEpic(Epic epic) {
        epic.setId(getNextId());
        epic.setStartTime(LocalDateTime.of(2024, 1, 1, 0, 0)); // у меня сначала создаётся эпик, а потом уже сабтаски.
        // Поэтому старт тайм эпика сделал таким, чтоб он не был null. При добавлении первой сабтаски с временем старттайм изменится.
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
        if (subtask == firsSubtaskFromEpic(subtask.getEpic())) {
            updateStartTimeForEpic(subtask.getEpic());
        }

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
        if (subtasks.get(taskId) == firsSubtaskFromEpic(subtasks.get(taskId).getEpic())) {
            updateStartTimeForEpic(subtasks.get(taskId).getEpic());
        }
        minusEpicDuration(subtasks.get(taskId).getEpic(), subtasks.get(taskId));


        subtasks.remove(taskId);
        return true;
    }

    @Override
    public boolean deleteEpic(int taskId) {

        if (!epics.containsKey(taskId)) {
            return false;
        }


        for (Subtask subtask : getEpic(taskId).getSubtaskList().values()) {

            deleteSubtask(subtask.getId());
        }
        if (historyManager.getTasks().contains(epics.get(taskId))) {
            historyManager.remove(taskId);
        }
        epics.remove(taskId);
        return true;
    }

    public Subtask firsSubtaskFromEpic(Epic epic) {
        List<Subtask> subtaskList = new ArrayList<>();
        for (Subtask subtask : epic.getSubtaskList().values()) {
            subtaskList.add(subtask);
        }
        return subtaskList.getFirst();

    }

    public void plusEpicDuration(Epic epic, Subtask subtask) {
        Duration duration = epic.getDuration().plus(subtask.getDuration());
        epic.setDuration(duration);

    }

    public void minusEpicDuration(Epic epic, Subtask subtask) {
        Duration duration = epic.getDuration().minus(subtask.getDuration());
        epic.setDuration(duration);
    }

    public void updateStartTimeForEpic(Epic epic) {
        if (!epic.getSubtaskList().isEmpty()) {

            LocalDateTime startTime = firsSubtaskFromEpic(epic).getStartTime();
            epic.setStartTime(startTime);
        }


    }


    public boolean taskIntersection(Task newTask) throws IntersectionException {
        if (newTask.getStartTime() == null || newTask.getDuration() == null) {
            return false;
        }
        boolean intersection = getPrioritizedTasks().stream()
                .anyMatch(task -> newTask.getStartTime().isBefore(task.getEndTime()) && newTask.getEndTime().isAfter(task.getStartTime()));


        if (intersection) {
            throw new IntersectionException("Задача " + newTask.getName() + " пересекается с уже существующей");
        }


        return intersection;


    }

    public Set<Task> getPrioritizedTasks() {
        List<Task> allTasks = new ArrayList<>();
        allTasks.addAll(getTasks());
        allTasks.addAll(getSubtasks());
        List<Task> notNullTasks = allTasks
                .stream()
                .filter(task -> task.getStartTime() != null)
                .collect(Collectors.toList());
        prioritizedTasks.addAll(notNullTasks);
        return prioritizedTasks;

    }


    @Override
    public int getNextId() {
        return ++nextId;

    }


}
