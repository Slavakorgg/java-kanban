package manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class ManagersTest {

    @Test
    void ManagersTest() {
        TaskManager taskManager1 = Managers.getDefault();
        assertInstanceOf(InMemoryTaskManager.class, taskManager1);
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertInstanceOf(InMemoryHistoryManager.class, historyManager);

    }
}
