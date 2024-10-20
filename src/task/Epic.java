package task;

import java.util.HashMap;
import java.util.Map;

public class Epic extends Task {
    protected Map<Integer, Subtask> subtaskList = new HashMap<>();


    public Epic(String name, String description, Status status) {
        super(name, description, status);

    }

    public Epic(Integer id, String name, String description, Status status) {
        super(id, name, description, status);

    }

    public void setSubtaskList(Map<Integer, Subtask> subtaskList) {
        this.subtaskList = subtaskList;
    }

    public Map<Integer, Subtask> getSubtaskList() {
        return subtaskList;
    }


}
