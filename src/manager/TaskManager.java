package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TaskManager{

    List<Task> getTasks();

    List<Subtask> getSubtasks();

    List<Task> getEpics();

    List<Task> getHistory();

    Task getTask(int taskId);

    Subtask getSubtask(int taskId);

    Epic getEpic(int taskId);

    HashMap<Integer, Subtask> getSubtasksFromEpic(Epic epic);

    Task createTask(Task task);

    Task createSubtask(Subtask subtask);

    Task createEpic(Epic epic);

    Task updateTask(Task task);

    Subtask updateSubtask(Subtask subtask);

    boolean deleteAllTasks();

    boolean deleteAllSubtasks();

    boolean deleteAllEpic();

    boolean deleteTask(int taskId);

    boolean deleteSubtask(int taskId);


    boolean deleteEpic(int taskId);

    int getNextId();

}


