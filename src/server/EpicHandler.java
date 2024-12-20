package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exception.NotFoundException;
import manager.TaskManager;
import task.Epic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class EpicHandler extends BaseHttpHandler {
    public EpicHandler(TaskManager taskManager, Gson gson) {
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

    }

    private void handleGet(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (Pattern.matches("^/epics$", path)) {
            try {
                String response = gson.toJson(taskManager.getEpics());
                sendText(exchange, response);
            } catch (Exception e) {
                sendInternalServerError(exchange);
            }
        }
        if (Pattern.matches("^/epics/\\d+$", path)) {
            try {
                String pathId = path.replaceFirst("/epics/", "");
                int id = parsePathID(pathId);
                if (id != -1) {
                    String response = gson.toJson(taskManager.getEpic(id));
                    sendText(exchange, response);
                }
            } catch (NotFoundException e) {
                sendNotFound(exchange, "Эпик не найден");
            } catch (Exception e) {
                sendInternalServerError(exchange);
            }
        }
        if (Pattern.matches("^/subtasks$", path)) {
            try {
                String pathId = path.replaceFirst("/epics/", "").replaceFirst("/subtasks$", "");
                int id = parsePathID(pathId);
                if (id != -1) {
                    Epic epic = taskManager.getEpic(id);
                    String response = gson.toJson(taskManager.getSubtasksFromEpic(epic));
                    sendText(exchange, response);
                }
            } catch (NotFoundException e) {
                sendNotFound(exchange, "Эпик не найден");
            } catch (Exception e) {
                sendInternalServerError(exchange);
            }
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {

        try {
            String epic = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Epic epic1 = gson.fromJson(epic, Epic.class);
            taskManager.createEpic(epic1);
            sendAdd(exchange, "Эпик создан");
        } catch (Exception e) {
            sendInternalServerError(exchange);
        }
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        try {
            if (Pattern.matches("^/epics$", path)) {
                try {


                    taskManager.deleteAllEpic();
                    sendText(exchange, "Удалены все Эпики");
                } catch (Exception e) {
                    sendInternalServerError(exchange);
                }
            }
            if (Pattern.matches("^/epics/\\d+$", path)) {
                String epicId = path.replaceFirst("/epics/", "");
                int id = parsePathID(epicId);
                if (id != -1) {
                    taskManager.deleteEpic(id);
                    sendText(exchange, "Удалён эпик с id " + id);
                }
            }

        } catch (NotFoundException e) {
            sendNotFound(exchange, "Эпик не найден");
        } catch (Exception e) {
            sendInternalServerError(exchange);
        }

    }

}
