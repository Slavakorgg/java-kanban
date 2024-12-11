package task;

import manager.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Subtask extends Task {
    Epic epic;
    TaskType type;

    @Override
    public TaskType getType() {
        return type;
    }


    public Subtask(String name, String description, Status status, Epic epic) {
        super(name, description, status);
        this.epic = epic;
        this.type = TaskType.SUBTASK;
    }

    public Subtask(Integer id, String name, String description, Status status, Epic epic) {
        super(id, name, description, status);
        this.epic = epic;
        this.type = TaskType.SUBTASK;

    }

    public Subtask(String name, String description, Status status, Epic epic, LocalDateTime startTime, Duration duration) {
        super(name, description, status, startTime, duration);
        this.epic = epic;
        this.type = TaskType.SUBTASK;

    }

    public Subtask(Integer id, String name, String description, Status status, Epic epic, LocalDateTime startTime, Duration duration) {
        super(id, name, description, status, startTime, duration);
        this.epic = epic;
        this.type = TaskType.SUBTASK;

    }

    @Override
    public void setType(TaskType type) {
        this.type = type;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", type=" + getType() +
                ", startTime=" + getStartTime().format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")) +
                ", duration=" + getDuration() +
                '}';
    }
}
