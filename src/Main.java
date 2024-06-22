import manager.*;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        Task task1 = new Task(2,"Мытьё посуды", "Вымыть всю грязную посуду", Status.NEW);
        Task task2 = new Task(1,"Стирка", "Постирать грязные вещи", Status.NEW);
        Epic epic1 = new Epic("Покупка продуктов", "Сходить в магазин и купить продукты", Status.NEW);
        Epic epic2 = new Epic("Уход за цветами", "Полить цветы", Status.NEW);
        Subtask subtask1 = new Subtask("Список покупок", "Составить список покупок", Status.NEW, epic1);
        Subtask subtask2 = new Subtask("Покупка товаров", "Найти и оплатить товары из списка", Status.NEW, epic1);
        Subtask subtask3 = new Subtask("Поливка цветов", "Набрать воду и полить цветы", Status.NEW, epic2);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);
        System.out.println("Создание задач");
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());
        System.out.println();
        Subtask newSubtask1 = new Subtask(subtask1.getId(), "Список покупок", "Составить список покупок", Status.DONE, epic1);
        Subtask newSubtask2 = new Subtask(subtask2.getId(), "Покупка товаров", "Найти и оплатить товары из списка", Status.IN_PROGRESS, epic1);
        Subtask newSubtask3 = new Subtask(subtask3.getId(), "Поливка цветов", "Набрать воду и полить цветы", Status.DONE, epic2);
        taskManager.updateSubtask(newSubtask1);
        taskManager.updateSubtask(newSubtask2);
        taskManager.updateSubtask(newSubtask3);
        System.out.println("Обновление задач");
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());
        System.out.println();
        taskManager.deleteTask(0);
        taskManager.deleteEpic(2);
        System.out.println("Удаление задач");
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());
        System.out.println("История просмотра задач");
        taskManager.getTasks();
        System.out.println(taskManager.getHistory().size());
        System.out.println(taskManager.getHistory());





    }


}
