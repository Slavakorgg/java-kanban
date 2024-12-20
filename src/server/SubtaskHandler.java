package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exception.IntersectionException;
import exception.NotFoundException;
import manager.TaskManager;
import task.Subtask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class SubtaskHandler extends BaseHttpHandler {
    public SubtaskHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    private int parsePathID(String path) {
        try {
            return Integer.parseInt(path);
        } catch (NumberFormatException e) {
            return -1;
        }
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
                default: {
                    sendNotFound(exchange, "Некорректный запрос");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }

    }

    private void handleGet(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (Pattern.matches("^/subtasks$", path)) {
            try {
                String response = gson.toJson(taskManager.getSubtasks());
                sendText(exchange, response);
            } catch (Exception e) {
                sendInternalServerError(exchange);
            }
        }
        if (Pattern.matches("^/subtasks/\\\\d+$", path)) {
            try {
                String pathId = path.replaceFirst("/subtasks/", "");
                int id = parsePathID(pathId);
                if (id != -1) {
                    String response = gson.toJson(taskManager.getSubtask(id));
                    sendText(exchange, response);
                }
            } catch (NotFoundException e) {
                sendNotFound(exchange, "Подзадача не найдена");
            } catch (Exception e) {
                sendInternalServerError(exchange);
            }
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (Pattern.matches("^/subtasks$", path)) {
            try {
                String subtask = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Subtask subtask1 = gson.fromJson(subtask, Subtask.class);
                taskManager.createTask(subtask1);
                sendAdd(exchange, "Подзадача создана");
            } catch (IntersectionException e) {
                sendHasInteractions(exchange, " Подзадача пересекается по времени с уже существующей");

            } catch (NotFoundException e) {
                sendNotFound(exchange, "Эпик не найден");
            } catch (Exception e) {
                sendInternalServerError(exchange);
            }
        }
        if (Pattern.matches("^/subtasks/\\d+$", path)) {
            try {
                String taskId = path.replaceFirst("/subtask", "");
                int id = parsePathID(taskId);
                if (id != -1) {
                    String subtask = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    Subtask subtask1 = gson.fromJson(subtask, Subtask.class);
                    taskManager.updateTask(subtask1);
                    sendAdd(exchange, "Подзадача обновлена");
                }

            } catch (IntersectionException e) {
                sendHasInteractions(exchange, "Обновлённая подзадача пересекается по времени с уже существующей");
            } catch (Exception e) {
                sendInternalServerError(exchange);
            }
        }

    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (Pattern.matches("^/subtasks$", path)) {
            try {
                taskManager.deleteAllSubtasks();
                sendText(exchange, "Всё подзадачи удалены");
            } catch (Exception e) {
                sendInternalServerError(exchange);
            }
        }
        if (Pattern.matches("^/subtasks/\\d+$", path)) {
            String subtaskId = path.replaceFirst("/subtask", "");
            int id = parsePathID(subtaskId);
            if (id != -1) {
                try {
                    taskManager.deleteSubtask(id);
                    sendText(exchange, "Подзадача с id " + id + " удалена");
                } catch (NotFoundException e) {
                    sendNotFound(exchange, "Подзадача не найдена");
                } catch (Exception e) {
                    sendInternalServerError(exchange);
                }
            }

        }

    }
}
