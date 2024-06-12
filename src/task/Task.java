package task;

import java.util.Objects;

public class Task {
    private Integer id;
    private String name;
    private String description;
    private Status status;

    public Task(String name, String descriprion, Status status) {
        this.name = name;
        this.description = descriprion;
        this.status = status;
    }

    public Task(Integer id, String name, String descriprion, Status status) {
        this.id = id;
        this.name = name;
        this.description = descriprion;
        this.status = status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = this.description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", descriprion='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
