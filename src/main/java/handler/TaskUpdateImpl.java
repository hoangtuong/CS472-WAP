package handler;

import database.DatabaseManager;
import model.Task;

import java.sql.SQLException;

public class TaskUpdateImpl implements TaskAction {
    @Override
    public void perform(Task task) throws SQLException {
        DatabaseManager.updateTask(task);
    }
}
