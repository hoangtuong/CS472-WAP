package utility;

import model.Task;

import java.util.ArrayList;

/** utility class to return mock data for testing
 *  @since 11/19/2017
 *  @author kl */
public class MockData {
    public ArrayList<Task> taskList = new ArrayList<>();

    public ArrayList<Task> retrieveTaskList() {

        taskList.add(new Task(101, "first task", "2017-11-19", "Personal", "Critical", "Created", 1));
        taskList.add(new Task(102, "second task", "2017-11-23", "Work", "Normal", "Created", 1));
        taskList.add(new Task(103, "third task", "2017-12-19", "Work", "Low", "Created", 1));

        return taskList;
    }
}
