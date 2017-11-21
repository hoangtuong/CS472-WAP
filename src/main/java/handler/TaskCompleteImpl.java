package handler;

import database.DatabaseManager;

import java.sql.SQLException;

public class TaskCompleteImpl implements TaskAction {

    @Override
    public void perform(int taskId) throws SQLException {
        DatabaseManager.completeTask(taskId);
    }
}
