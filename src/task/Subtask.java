package task;

import manager.TaskType;

public class Subtask extends Task {
    Epic epic;

    @Override
    public TaskType getType() {
        return type;
    }

    TaskType type;


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


}
