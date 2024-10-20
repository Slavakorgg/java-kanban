import manager.*;
import task.Epic;
import task.Status;
import task.Subtask;


public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        Epic epic1 = new Epic("Epic-1","Epic-1",Status.NEW);
        Epic epic2 = new Epic("Epic-2","Epic-2",Status.NEW);

        Subtask subtask1 = new Subtask("Subtask-1","Subtask-1 for Epic-1",Status.NEW,epic1);
        Subtask subtask2 = new Subtask("Subtask-2","Subtask-2 for Epic-1",Status.NEW,epic1);
        Subtask subtask3 = new Subtask("Subtask-3","Subtask-3 for Epic-1",Status.NEW,epic1);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);

        taskManager.getEpic(1);
        System.out.println(taskManager.getHistory());
        taskManager.getSubtask(3);
        System.out.println(taskManager.getHistory());
        taskManager.getSubtask(5);
        System.out.println(taskManager.getHistory());
        taskManager.getEpic(1);
        System.out.println(taskManager.getHistory());
        taskManager.deleteTask(4);
        System.out.println(taskManager.getHistory());
        taskManager.getSubtask(4);
        System.out.println(taskManager.getHistory());
        taskManager.getEpic(2);
        System.out.println(taskManager.getHistory());
        taskManager.deleteSubtask(4);
        System.out.println(taskManager.getHistory());
        taskManager.deleteEpic(1);
        System.out.println(taskManager.getHistory());

    }


}
