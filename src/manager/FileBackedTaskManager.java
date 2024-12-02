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

            bw.write("id,type,name,description,status,epic\n");
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
        int id = Integer.parseInt(mass[0]);
        String type = mass[1];
        String name = mass[2];
        String description = mass[3];
        String status = mass[4];


        if (type.equals("TASK")) {
            Status taskStatus;
            if (status.equals("NEW")) {
                taskStatus = Status.NEW;
            } else if (status.equals("DONE")) {
                taskStatus = Status.DONE;
            } else {
                taskStatus = Status.IN_PROGRESS;
            }
            Task task = new Task(id, name, description, taskStatus);
            task.setType(TaskType.TASK);
            createTask(task);
            return task;
        } else if (type.equals("EPIC")) {
            Status taskStatus;
            if (status.equals("NEW")) {
                taskStatus = Status.NEW;
            } else if (status.equals("DONE")) {
                taskStatus = Status.DONE;
            } else {
                taskStatus = Status.IN_PROGRESS;
            }
            Epic epic = new Epic(id, name, description, taskStatus);
            epic.setType(TaskType.EPIC);
            createEpic(epic);
            return epic;
        } else {
            Status taskStatus;
            int epicId = Integer.parseInt(mass[5]);
            if (status.equals("NEW")) {
                taskStatus = Status.NEW;
            } else if (status.equals("DONE")) {
                taskStatus = Status.DONE;
            } else {
                taskStatus = Status.IN_PROGRESS;
            }
            Subtask subtask = new Subtask(id, name, description, taskStatus, getEpic(epicId));
            subtask.setType(TaskType.SUBTASK);
            createSubtask(subtask);
            return subtask;
        }


    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(Managers.getDefaultHistory(),Paths.get(file.getPath()));
        //теперь запись будет происходит в тот-же файл из которого была загрузка, а не в "file.csv"
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            for (int i = 1; i < lines.size(); i++) {
                fileBackedTaskManager.fromString(lines.get(i));
            }

        } catch (IOException e) {
            throw new ManagerLoadException(e);
        }


        return fileBackedTaskManager;
    }

    public class ManagerSaveException extends RuntimeException {


        public ManagerSaveException(final Throwable cause) {
            super(cause);
        }


    }

    public static class ManagerLoadException extends RuntimeException {


        public ManagerLoadException(final Throwable cause) {
            super(cause);
        }


    }
}
