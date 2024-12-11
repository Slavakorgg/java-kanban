package manager;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileBackedTaskManagerTest {
    @Test
    void saveInFile() throws IOException {


        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(Managers.getDefaultHistory(),
                Files.createTempFile("testFile", ".csv"));
        Epic epic1 = new Epic("Epic-1", "Epic-1", Status.IN_PROGRESS);
        Epic epic2 = new Epic("Epic-2", "Epic-2", Status.NEW);
        Task task1 = new Task("Task-1", "description for task-1", Status.NEW, LocalDateTime.of(2024, 1, 13, 14, 20), Duration.ofMinutes(10));


        Subtask subtask1 = new Subtask("Subtask-1", "Subtask-1 for Epic-1", Status.DONE, epic1, LocalDateTime.of(2024, 2, 10, 15, 40), Duration.ofMinutes(10));
        Subtask subtask2 = new Subtask("Subtask-2", "Subtask-2 for Epic-1", Status.IN_PROGRESS, epic1, LocalDateTime.of(2024, 2, 24, 22, 5), Duration.ofMinutes(10));
        Subtask subtask3 = new Subtask("Subtask-3", "Subtask-3 for Epic-1", Status.NEW, epic1, LocalDateTime.of(2024, 3, 5, 10, 0), Duration.ofMinutes(10));
        fileBackedTaskManager.createTask(task1);
        fileBackedTaskManager.createEpic(epic1);
        fileBackedTaskManager.createEpic(epic2);

        fileBackedTaskManager.createSubtask(subtask1);
        fileBackedTaskManager.createSubtask(subtask2);
        fileBackedTaskManager.createSubtask(subtask3);
        List<String> newFile = Files.readAllLines(fileBackedTaskManager.getFile());
        List<String> testFile = Files.readAllLines(Paths.get("test/manager/TestFile.csv"));
        for (int i = 0; i < testFile.size(); i++) {
            assertTrue(newFile.get(i).equals(testFile.get(i)), newFile.get(i) + " : " + testFile.get(i));
        }
        assertEquals(newFile.size(), 7);

    }


    @Test
    void loadFromFile() throws IOException {
        List<String> loadFile = null;

        loadFile = Files.readAllLines(Paths.get("test/manager/TestFile.csv"));

        FileBackedTaskManager.loadFromFile(new File("test/manager/TestFile.csv"));

        List<String> newFile = Files.readAllLines(Paths.get("test/manager/TestFile.csv"));

        for (int i = 0; i < loadFile.size(); i++) {
            assertTrue(newFile.get(i).equals(loadFile.get(i)), newFile.get(i) + " : " + loadFile.get(i));

            assertEquals(newFile.size(), loadFile.size());
        }
    }

    @Test
    void loadFromEmptyFile() throws IOException {
        FileBackedTaskManager.loadFromFile(new File("test/manager/emptyFile.csv"));


        List<String> newFile = Files.readAllLines(Paths.get("test/manager/emptyFile.csv"));

        assertEquals(newFile.size(), 0);

    }
}





