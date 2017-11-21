package handler;

import java.sql.SQLException;
import java.util.List;

public interface TaskAction {
    void perform(int taskId) throws SQLException;
}
