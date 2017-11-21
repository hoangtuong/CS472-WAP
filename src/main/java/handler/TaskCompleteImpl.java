package handler;

import database.DatabaseManager;
import model.Task;

import java.sql.SQLException;

public class TaskCompleteImpl implements TaskAction {

    @Override
    public void perform(Task task) throws SQLException {
        DatabaseManager.completeTask(task.getId());
    }
}
