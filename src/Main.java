

import exception.IntersectionException;


import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IntersectionException, IOException, InterruptedException {

        // Я не могу понять в чём ошибка. Пишет Failed making field 'java.time.format.DateTimeFormatter#printerParser' accessible.
        // Вроде DateTimeAdapter написан правильно и подключил его к gson...


        /* TaskManager taskManager = new InMemoryTaskManager(Managers.getDefaultHistory());
        HttpTaskServer httpTaskServer = new HttpTaskServer(taskManager);
        Gson gson = httpTaskServer.getGson();
        Task task = new Task("Test 2", "Testing task 2",
                        Status.NEW);
        String taskJson = gson.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response); */




          /* File file = new File("src/loadFile.csv");
        System.out.println(FileBackedTaskManager.loadFromFile(file).getHistory());
        FileBackedTaskManager taskManager = new FileBackedTaskManager(Managers.getDefaultHistory());
        Epic epic1 = new Epic("Epic-1", "Epic-1", Status.IN_PROGRESS);
        Epic epic2 = new Epic("Epic-2", "Epic-2", Status.NEW);
        Task task1 = new Task("Task-1", "description for task-1", Status.NEW, LocalDateTime.of(2024, 3, 13, 14, 20), Duration.ofMinutes(10));


        Subtask subtask1 = new Subtask("Subtask-1", "Subtask-1 for Epic-1", Status.DONE, epic1, LocalDateTime.of(2024, 2, 10, 15, 40), Duration.ofMinutes(10));
        Subtask subtask2 = new Subtask("Subtask-2", "Subtask-2 for Epic-1", Status.IN_PROGRESS, epic1, LocalDateTime.of(2024, 1, 24, 22, 5), Duration.ofMinutes(10));
        Subtask subtask3 = new Subtask("Subtask-3", "Subtask-3 for Epic-1", Status.NEW, epic1, LocalDateTime.of(2024, 2, 5, 10, 0), Duration.ofMinutes(10));
        taskManager.createTask(task1);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);
        System.out.println(epic1);
        taskManager.deleteSubtask(4);
        System.out.println(epic1);
        System.out.println(taskManager.getHistory());
        System.out.println(task1);
        System.out.println(taskManager.getPrioritizedTasks());*/


    }


}
