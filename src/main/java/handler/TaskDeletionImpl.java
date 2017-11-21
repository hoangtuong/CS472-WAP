package handler;

import database.DatabaseManager;

import java.sql.SQLException;

public class TaskDeletionImpl implements TaskAction {

    @Override
    public void perform(int taskId) throws SQLException {
        DatabaseManager.deleteTask(taskId);
    }
}
