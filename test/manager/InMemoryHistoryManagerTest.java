package manager;

import org.junit.jupiter.api.Test;
import task.Status;
import task.Task;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class InMemoryHistoryManagerTest {
    TaskManager taskManager = Managers.getDefault();
    HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    void HistoryManagerTest() {
        List<Task> historyList = new ArrayList<>();
        Task task1 = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        taskManager.createTask(task1);
        taskManager.getTask(1);
        historyList.add(task1);
        Task task2 = new Task(1, "Test 2 addNewTask", "Test 2 addNewTask description", Status.DONE);
        taskManager.updateTask(task2);
        taskManager.getTask(1);
        historyList.add(task2);
        assertEquals(taskManager.getHistoryManager(), historyList);


    }


}