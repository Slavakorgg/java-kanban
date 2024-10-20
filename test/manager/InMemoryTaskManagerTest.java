package manager;


import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    TaskManager taskManager = Managers.getDefault();
    Epic testEpic = new Epic("Test epic", "Test", Status.DONE);


    @Test
    void TaskByOneId() {
        Task task1 = new Task(1, "Test addNewTask", "Test addNewTask description", Status.NEW);
        Task task2 = new Task(1, "Test addNewTask2", "Test addNewTask description2", Status.IN_PROGRESS);


        assertEquals(task1, task2);
    }

    @Test
    void SubtaskByOneId() {
        Subtask subtask1 = new Subtask(1, "Test addNewTask", "Test addNewTask description", Status.NEW, testEpic);
        Subtask subtask2 = new Subtask(1, "Test addNewTask2", "Test addNewTask description2", Status.DONE, testEpic);

        assertEquals(subtask1, subtask2);
    }

    @Test
    void EpicByOneId() {
        Epic epic1 = new Epic(1, "Test addNewTask", "Test addNewTask description", Status.NEW);
        Epic epic2 = new Epic(1, "Test addNewTask2", "Test addNewTask description2", Status.DONE);
        assertEquals(epic1, epic2);

    }

    //"проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи".
    // В моей реализации не предусмотрено добавление задач в эпик напрямую. Сабтаски добавляются в эпик в момент создания или обновления сабтаска.
    // В эпики можно положить только сабтаски.

    //проверьте, что объект Subtask нельзя сделать своим же эпиком;
    /*@Test
    void SubtaskInSubtask(){
        Subtask subtask1 = new Subtask("Test addNewTask", "Test addNewTask description", Status.NEW,testEpic);
        Subtask subtask2 = new Subtask(subtask1.getId(),"Test addNewTask2", "Test addNewTask description2", Status.NEW,subtask1);
        taskManager.updateSubtask(subtask2);
        taskManager.createSubtask(subtask2);

    }*/
    // Такая проверка не скомпилируется


    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        taskManager.createTask(task);
        assertNotNull(taskManager.getTask(1), "Задача не найдена.");
    }

    @Test
    void addNewSubtask() {
        Subtask subtask = new Subtask("Test addNewTask", "Test addNewTask description", Status.NEW, testEpic);
        taskManager.createSubtask(subtask);
        assertNotNull(taskManager.getSubtask(1), "Задача не найдена.");
    }

    @Test
    void addNewEpic() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description", Status.NEW);
        taskManager.createEpic(epic);
        assertNotNull(taskManager.getEpic(1));
    }

    @Test
    void ConflictBetweenId() {
        Task task1 = new Task(2, "Test addNewTask", "Test addNewTask description", Status.NEW);
        Task task2 = new Task("Test addNewTask1", "Test addNewTask description1", Status.DONE);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        assertEquals(taskManager.getTask(1), task1);
        assertEquals(taskManager.getTask(2), task2);
        assertNotEquals(taskManager.getTask(1), task2);

    }

    @Test
    void addTaskInManager() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        taskManager.createTask(task);
        assertEquals(taskManager.getTask(1).getName(), "Test addNewTask");
        assertEquals(taskManager.getTask(1).getDescription(), "Test addNewTask description");
        assertEquals(taskManager.getTask(1).getStatus(), Status.NEW);

    }

    @Test
    void addSubtaskInManager() {
        Subtask subtask = new Subtask(1, "Test addNewTask", "Test addNewTask description", Status.NEW, testEpic);
        taskManager.createSubtask(subtask);
        assertEquals(taskManager.getSubtask(1).getName(), "Test addNewTask");
        assertEquals(taskManager.getSubtask(1).getDescription(), "Test addNewTask description");
        assertEquals(taskManager.getSubtask(1).getStatus(), Status.NEW);
        assertEquals(taskManager.getSubtask(1).getEpic(), testEpic);

    }

    @Test
    void addEpicInManager() {
        Epic epic = new Epic(1, "Test addNewTask", "Test addNewTask description", Status.DONE);
        taskManager.createEpic(epic);
        assertEquals(taskManager.getEpic(1).getName(), "Test addNewTask");
        assertEquals(taskManager.getEpic(1).getDescription(), "Test addNewTask description");
        assertEquals(taskManager.getEpic(1).getStatus(), Status.DONE);

    }

    @Test
    void deleteId() {
        Task task1 = new Task(1, "Task1", "1-1", Status.NEW);
        Task task2 = new Task(2, "Task 2", "2-1", Status.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.getTasks();
        taskManager.deleteTask(2);

        assertEquals(taskManager.getHistory().contains(task2), false);


    }

    @Test
    void deleteSubtaskFromEpic() {
        Epic epic1 = new Epic(1, "Test Epic", "Test epic description", Status.NEW);
        Subtask subtask1 = new Subtask(2, "Subtask for epic1", "description", Status.NEW, epic1);
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        taskManager.deleteSubtask(2);
        assertEquals(epic1.getSubtaskList().isEmpty(), false);

    }


}