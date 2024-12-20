package manager;

import exception.IntersectionException;
import task.Epic;

import task.Subtask;
import task.Task;


import java.util.HashMap;
import java.util.List;
import java.util.Set;


public interface TaskManager {

    List<Task> getTasks();

    List<Subtask> getSubtasks();

    List<Task> getEpics();

    List<Task> getHistory();

    Task getTask(int taskId);

    Subtask getSubtask(int taskId);

    Epic getEpic(int taskId);

    HashMap<Integer, Subtask> getSubtasksFromEpic(Epic epic);

    Task createTask(Task task) throws IntersectionException;

    Task createSubtask(Subtask subtask) throws IntersectionException;

    Task createEpic(Epic epic);

    Task updateTask(Task task) throws IntersectionException;

    Subtask updateSubtask(Subtask subtask);

    boolean deleteAllTasks();

    boolean deleteAllSubtasks();

    boolean deleteAllEpic();

    boolean deleteTask(int taskId);

    boolean deleteSubtask(int taskId);


    boolean deleteEpic(int taskId);

    int getNextId();
    Set<Task> getPrioritizedTasks();

}


