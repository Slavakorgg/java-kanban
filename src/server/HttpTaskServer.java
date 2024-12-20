package server;

import com.google.gson.Gson;

import com.sun.net.httpserver.HttpServer;
import manager.*;


import java.io.IOException;
import java.net.InetSocketAddress;


public class HttpTaskServer {
    public static final int PORT = 8080;
    private HttpServer httpServer;
    /*  public static final Gson gson = new GsonBuilder()
              .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
              .registerTypeAdapter(Duration.class, new DurationAdapter())
              .create();*/
    private TaskManager taskManager;
    private Gson gson;

    public Gson getGson() {
        return gson;
    }

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        gson = Managers.getGson();
        httpServer = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler(taskManager, gson));
        httpServer.createContext("/subtasks", new SubtaskHandler(taskManager, gson));
        httpServer.createContext("/epics", new EpicHandler(taskManager, gson));
        httpServer.createContext("/history", new TaskHandler(taskManager, gson));
        httpServer.createContext("/prioritized", new TaskHandler(taskManager, gson));
    }

    public void start() {
        System.out.println("Сервер запущен на порту: " + PORT);
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
        System.out.println("Сервер остановлен на порту: " + PORT);
    }

    public static void main(String[] args) throws IOException {
        InMemoryTaskManager taskManager = new InMemoryTaskManager(Managers.getDefaultHistory());
        HttpTaskServer httpTaskServer = new HttpTaskServer(taskManager);
        httpTaskServer.start();


    }

}
