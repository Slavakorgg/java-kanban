package manager;

import exception.IntersectionException;
import org.junit.jupiter.api.Test;
import task.Status;

import task.Task;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class InMemoryHistoryManagerTest {
    TaskManager taskManager = Managers.getDefault();


    @Test
    void HistoryManagerTest() throws IntersectionException {
        List<Task> historyList = new ArrayList<>();
        Task task1 = new Task(1, "Task1", "1-1", Status.NEW);
        Task task2 = new Task(2, "Task 2", "2-1", Status.NEW);
        Task task3 = new Task(3, "Task 3", "3-1", Status.NEW);
        Task task4 = new Task(4, "Task 4", "4-1", Status.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        taskManager.createTask(task4);
        taskManager.getTasks();
        historyList.add(task1);
        historyList.add(task2);
        historyList.add(task3);
        historyList.add(task4);
        assertEquals(historyList, taskManager.getHistory());


    }

    @Test
    void HistoryManagerSizeTest() throws IntersectionException {
        Task task1 = new Task(1, "Task1", "1-1", Status.NEW);
        Task task2 = new Task(2, "Task 2", "2-1", Status.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.getTask(1);
        taskManager.getTask(1);
        taskManager.getTask(2);
        taskManager.getTasks();
        assertEquals(taskManager.getHistory().size(), 2);
    }

    @Test
    void LastTaskTest() throws IntersectionException {
        Task task1 = new Task(1, "Task1", "1-1", Status.NEW);
        Task task2 = new Task(2, "Task 2", "2-1", Status.NEW);
        Task task3 = new Task(3, "Task 3", "3-1", Status.NEW);
        Task task4 = new Task(4, "Task 4", "4-1", Status.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        taskManager.createTask(task4);
        taskManager.getTasks();
        taskManager.getTask(1);
        assertEquals(taskManager.getHistory().get(3), task1);

    }

    @Test
    void FirstTaskTest() throws IntersectionException {
        Task task1 = new Task(1, "Task1", "1-1", Status.NEW);
        Task task2 = new Task(2, "Task 2", "2-1", Status.NEW);
        Task task3 = new Task(3, "Task 3", "3-1", Status.NEW);
        Task task4 = new Task(4, "Task 4", "4-1", Status.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        taskManager.createTask(task4);
        taskManager.getTasks();
        taskManager.getTask(1);
        assertEquals(taskManager.getHistory().get(0), task2);
    }


}