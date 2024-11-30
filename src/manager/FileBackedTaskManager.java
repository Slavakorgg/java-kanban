package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    private String pathToFile = "src/file.csv";
    private Path file;


    public Path getFile() {
        return file;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public FileBackedTaskManager(HistoryManager defaultHistory, Path file) {
        super(defaultHistory);
        this.pathToFile = file.toAbsolutePath().toString();
        this.file = file;
    }

    public FileBackedTaskManager(HistoryManager defaultHistory) {
        super(defaultHistory);
        this.file = Paths.get(pathToFile);

    }


    @Override
    public Task createTask(Task task) {
        super.createTask(task);

        save();
        return task;
    }

    @Override
    public Task createEpic(Epic epic) {
        super.createEpic(epic);

        save();
        return epic;


    }

    @Override
    public Task createSubtask(Subtask subtask) {
        super.createSubtask(subtask);

        save();
        return subtask;

    }


    public String toString(Task task) {
        return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getDescription() + "," + task.getStatus();
    }


    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathToFile))) {

            bw.write("id,type,name,status,description,epic\n");
            for (Task task : getTasks()) {
                bw.write(toString(task) + "\n");
            }
            for (Task epic : getEpics()) {
                bw.write(toString(epic) + "\n");
            }
            for (Subtask subtask : getSubtasks()) {
                bw.write(toString(subtask) + "," + subtask.getEpic().getId() + "\n");
            }


        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }

    }

    public Task fromString(String value) {
        String[] mass = value.split(",");

        if (mass[1].equals("TASK")) {
            Status status;
            if (mass[4].equals("NEW")) {
                status = Status.NEW;
            } else if (mass[4].equals("DONE")) {
                status = Status.DONE;
            } else {
                status = Status.IN_PROGRESS;
            }
            Task task = new Task(Integer.parseInt(mass[0]), mass[2], mass[3], status);
            task.setType(TaskType.TASK);
            createTask(task);
            return task;
        } else if (mass[1].equals("EPIC")) {
            Status status;
            if (mass[4].equals("NEW")) {
                status = Status.NEW;
            } else if (mass[4].equals("DONE")) {
                status = Status.DONE;
            } else {
                status = Status.IN_PROGRESS;
            }
            Epic epic = new Epic(Integer.parseInt(mass[0]), mass[2], mass[3], status);
            epic.setType(TaskType.EPIC);
            createEpic(epic);
            return epic;
        } else {
            Status status;
            if (mass[4].equals("NEW")) {
                status = Status.NEW;
            } else if (mass[4].equals("DONE")) {
                status = Status.DONE;
            } else {
                status = Status.IN_PROGRESS;
            }
            Subtask subtask = new Subtask(Integer.parseInt(mass[0]), mass[2], mass[3], status, getEpic(Integer.parseInt(mass[5])));
            subtask.setType(TaskType.SUBTASK);
            createSubtask(subtask);
            return subtask;
        }


    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(Managers.getDefaultHistory());
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            for (String string : lines) {
                if (!string.equals(lines.get(0))) {
                    fileBackedTaskManager.fromString(string);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return fileBackedTaskManager;
    }

    public class ManagerSaveException extends RuntimeException {


        public ManagerSaveException(final Throwable cause) {
            super(cause);
        }


    }


}
