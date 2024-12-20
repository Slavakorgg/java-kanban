package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exception.IntersectionException;
import exception.NotFoundException;
import manager.TaskManager;
import task.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class TaskHandler extends BaseHttpHandler {
    public TaskHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET": {


                    handleGet(exchange);
                    break;
                }
                case "POST": {
                    handlePost(exchange);
                    break;

                }
                case "DELETE": {
                    handleDelete(exchange);
                    break;

                }
                default:
                    sendNotFound(exchange, "Некорректный запрос");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }

    }

    private int parsePathID(String path) {
        try {
            return Integer.parseInt(path);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (Pattern.matches("^/tasks$", path)) {
            try {
                String response = gson.toJson(taskManager.getTasks());
                sendText(exchange, response);
            } catch (Exception e) {
                sendInternalServerError(exchange);
            }
        }
        if (Pattern.matches("^/tasks/\\d+$", path)) {
            try {
                String pathId = path.replaceFirst("/tasks/", "");
                int id = parsePathID(pathId);
                if (id != -1) {
                    String response = gson.toJson(taskManager.getTask(id));
                    sendText(exchange, response);
                }
            } catch (NotFoundException e) {
                sendNotFound(exchange, "Задача не найдена");
            } catch (Exception e) {
                sendInternalServerError(exchange);
            }
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (Pattern.matches("^/tasks$", path)) {
            try {
                InputStream inputStream = exchange.getRequestBody();
                String task = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                Task task1 = gson.fromJson(task, Task.class);
                taskManager.createTask(task1);
                sendAdd(exchange, "Задача создана");
            } catch (IntersectionException e) {
                sendHasInteractions(exchange, " Задача пересекается по времени с уже существующей");

            } catch (Exception e) {
                sendInternalServerError(exchange);
            }
        }
        if (Pattern.matches("^/tasks/\\d+$", path)) {
            try {
                String taskId = path.replaceFirst("/task", "");
                int id = parsePathID(taskId);
                if (id != -1) {
                    InputStream inputStream = exchange.getRequestBody();
                    String task = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    Task task1 = gson.fromJson(task, Task.class);
                    taskManager.updateTask(task1);
                    sendAdd(exchange, "Задача обновлена");
                }

            } catch (IntersectionException e) {
                sendHasInteractions(exchange, "Обновлённая задача пересекается по времени с уже существующей");
            } catch (Exception e) {
                sendInternalServerError(exchange);
            }
        }

    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (Pattern.matches("^/tasks$", path)) {
            try {
                taskManager.deleteAllTasks();
                sendText(exchange, "Всё задачи удалены");
            } catch (Exception e) {
                sendInternalServerError(exchange);
            }
        }
        if (Pattern.matches("^/tasks/\\d+$", path)) {
            String taskId = path.replaceFirst("/task", "");
            int id = parsePathID(taskId);
            if (id != -1) {
                try {
                    taskManager.deleteTask(id);
                    sendText(exchange, "Задача с id " + id + " удалена");
                } catch (NotFoundException e) {
                    sendNotFound(exchange, "Задача не найдена");
                } catch (Exception e) {
                    sendInternalServerError(exchange);
                }
            }

        }

    }

}
