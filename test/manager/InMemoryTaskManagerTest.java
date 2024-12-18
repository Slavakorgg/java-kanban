package manager;


import exception.IntersectionException;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.time.Duration;
import java.time.LocalDateTime;

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
    void addNewTask() throws IntersectionException {
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        taskManager.createTask(task);
        assertNotNull(taskManager.getTask(1), "Задача не найдена.");
    }

    @Test
    void addNewSubtask() throws IntersectionException {
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
    void ConflictBetweenId() throws IntersectionException {
        Task task1 = new Task(2, "Test addNewTask", "Test addNewTask description", Status.NEW);
        Task task2 = new Task("Test addNewTask1", "Test addNewTask description1", Status.DONE);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        assertEquals(taskManager.getTask(1), task1);
        assertEquals(taskManager.getTask(2), task2);
        assertNotEquals(taskManager.getTask(1), task2);

    }

    @Test
    void addTaskInManager() throws IntersectionException {
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        taskManager.createTask(task);
        assertEquals(taskManager.getTask(1).getName(), "Test addNewTask");
        assertEquals(taskManager.getTask(1).getDescription(), "Test addNewTask description");
        assertEquals(taskManager.getTask(1).getStatus(), Status.NEW);

    }

    @Test
    void addSubtaskInManager() throws IntersectionException {
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
    void deleteId() throws IntersectionException {
        Task task1 = new Task(1, "Task1", "1-1", Status.NEW);
        Task task2 = new Task(2, "Task 2", "2-1", Status.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.getTasks();
        taskManager.deleteTask(2);

        assertEquals(taskManager.getHistory().contains(task2), false);


    }

    @Test
    void deleteSubtaskFromEpic() throws IntersectionException {
        Epic epic1 = new Epic(1, "Test Epic", "Test epic description", Status.NEW);
        Subtask subtask1 = new Subtask(2, "Subtask for epic1", "description", Status.NEW, epic1, LocalDateTime.of(2024, 2, 10, 15, 40), Duration.ofMinutes(10));
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        taskManager.deleteSubtask(2);
        assertEquals(epic1.getSubtaskList().isEmpty(), false);

    }

    @Test
    void epicNewStatusTest() throws IntersectionException {
        Epic epic1 = new Epic("Test Epic", "Test epic description");
        Subtask subtask1 = new Subtask("Subtask-1 for epic1", "description", Status.NEW, epic1, LocalDateTime.of(2024, 2, 10, 15, 40), Duration.ofMinutes(10));
        Subtask subtask2 = new Subtask("Subtask-2 for epic1", "description", Status.NEW, epic1, LocalDateTime.of(2024, 3, 10, 15, 40), Duration.ofMinutes(10));
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        assertEquals(epic1.getStatus(), Status.NEW);

    }

    @Test
    void epicDoneStatusTest() throws IntersectionException {
        Epic epic1 = new Epic("Test Epic", "Test epic description");
        Subtask subtask1 = new Subtask("Subtask-1 for epic1", "description", Status.DONE, epic1, LocalDateTime.of(2024, 2, 10, 15, 40), Duration.ofMinutes(10));
        Subtask subtask2 = new Subtask("Subtask-2 for epic1", "description", Status.DONE, epic1, LocalDateTime.of(2024, 3, 10, 15, 40), Duration.ofMinutes(10));
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        assertEquals(epic1.getStatus(), Status.DONE);

    }

    @Test
    void epicMixedStatusTest() throws IntersectionException {
        Epic epic1 = new Epic("Test Epic", "Test epic description");
        Subtask subtask1 = new Subtask("Subtask-1 for epic1", "description", Status.NEW, epic1, LocalDateTime.of(2024, 2, 10, 15, 40), Duration.ofMinutes(10));
        Subtask subtask2 = new Subtask("Subtask-2 for epic1", "description", Status.DONE, epic1, LocalDateTime.of(2024, 3, 10, 15, 40), Duration.ofMinutes(10));
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        assertEquals(epic1.getStatus(), Status.IN_PROGRESS);

    }

    @Test
    void epicInProgressStatusTest() throws IntersectionException {
        Epic epic1 = new Epic("Test Epic", "Test epic description");
        Subtask subtask1 = new Subtask("Subtask-1 for epic1", "description", Status.IN_PROGRESS, epic1, LocalDateTime.of(2024, 2, 10, 15, 40), Duration.ofMinutes(10));
        Subtask subtask2 = new Subtask("Subtask-2 for epic1", "description", Status.IN_PROGRESS, epic1, LocalDateTime.of(2024, 3, 10, 15, 40), Duration.ofMinutes(10));
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        assertEquals(epic1.getStatus(), Status.IN_PROGRESS);

    }

    @Test
    public void intersectionTest() {
        Task task1 = new Task("Task-1", "description", Status.NEW, LocalDateTime.of(2024, 12, 12, 10, 0), Duration.ofMinutes(600));
        Task task2 = new Task("Task-2", "description", Status.NEW, LocalDateTime.of(2024, 12, 12, 10, 10), Duration.ofMinutes(10));

        assertThrows(IntersectionException.class, () -> {
            taskManager.createTask(task1);
            taskManager.createTask(task2);


        });

    }
}



