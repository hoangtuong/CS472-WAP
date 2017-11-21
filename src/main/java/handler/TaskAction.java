package handler;

import model.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskAction {
    void perform(Task task) throws SQLException;
}
