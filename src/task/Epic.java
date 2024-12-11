package task;

import manager.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Epic extends Task {
    protected Map<Integer, Subtask> subtaskList = new HashMap<>();
    private LocalDateTime endTime;
    TaskType type;
    Duration duration = Duration.ZERO;
    LocalDateTime startTime;

    @Override
    public Duration getDuration() {
        return duration;
    }

    public Epic(String name, String description) {
        super(name, description);
    }

    @Override
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public LocalDateTime getEndTime() {
        return getStartTime().plus(getDuration());
    }

    @Override
    public LocalDateTime getStartTime() {

        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
        this.type = TaskType.EPIC;

    }


    @Override
    public void setType(TaskType type) {
        this.type = type;
    }

    @Override
    public TaskType getType() {
        return type;
    }

    public Epic(Integer id, String name, String description, Status status) {
        super(id, name, description, status);
        this.type = TaskType.EPIC;

    }

    public void setSubtaskList(Map<Integer, Subtask> subtaskList) {
        this.subtaskList = subtaskList;
    }

    public Map<Integer, Subtask> getSubtaskList() {
        return subtaskList;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", type=" + getType() +
                ", startTime=" + getStartTime() +
                ", duration=" + getDuration() +
                '}';
    }
}
