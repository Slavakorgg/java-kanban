package task;

import manager.TaskType;

import java.util.HashMap;
import java.util.Map;

public class Epic extends Task {
    protected Map<Integer, Subtask> subtaskList = new HashMap<>();
    TaskType type;


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


}
