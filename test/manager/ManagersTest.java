package manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagersTest {

    @Test
    void taskManagerTest() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager.getTasks());
        assertNotNull(taskManager.getSubtasks());
        assertNotNull(taskManager.getEpics());
        assertNotNull(taskManager.getHistory());
        assertEquals(taskManager.getTasks().size(), 0);
        assertEquals(taskManager.getSubtasks().size(), 0);
        assertEquals(taskManager.getEpics().size(), 0);
        assertEquals(taskManager.getHistory().size(), 0);
        assertEquals(taskManager.getNextId(), 1);


    }
    @Test
    void historyManagerTest(){
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager.getTasks());
        assertEquals(historyManager.getTasks().size(),0);

    }
}
