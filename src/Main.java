import manager.*;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;


public class Main {

    public static void main(String[] args) {

        File file = new File("src/loadFile.csv");
        System.out.println(FileBackedTaskManager.loadFromFile(file).getHistory());
        FileBackedTaskManager taskManager = new FileBackedTaskManager(Managers.getDefaultHistory());
        Epic epic1 = new Epic("Epic-1", "Epic-1", Status.IN_PROGRESS);
        Epic epic2 = new Epic("Epic-2", "Epic-2", Status.NEW);
        Task task1 = new Task("Task-1", "description for task-1", Status.NEW, LocalDateTime.of(2024, 3, 13, 14, 20), Duration.ofMinutes(10));


        Subtask subtask1 = new Subtask("Subtask-1", "Subtask-1 for Epic-1", Status.DONE, epic1, LocalDateTime.of(2024, 2, 10, 15, 40), Duration.ofMinutes(10));
        Subtask subtask2 = new Subtask("Subtask-2", "Subtask-2 for Epic-1", Status.IN_PROGRESS, epic1, LocalDateTime.of(2024, 1, 24, 22, 5), Duration.ofMinutes(10));
        Subtask subtask3 = new Subtask("Subtask-3", "Subtask-3 for Epic-1", Status.NEW, epic1, LocalDateTime.of(2024, 2, 5, 10, 0), Duration.ofMinutes(10));
        taskManager.createTask(task1);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);
        System.out.println(epic1);
        taskManager.deleteSubtask(4);
        System.out.println(epic1);
        System.out.println(taskManager.getHistory());


        System.out.println(taskManager.getPrioritizedTasks());


    }


}
