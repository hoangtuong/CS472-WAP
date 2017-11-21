package database;

import model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    public static Connection dbConnection = getConnection();

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/tasklist?user=root&password=");
            return connection;
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnection() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Task> getTaskListByUserId(int userId) {
        List<Task> tasks = new ArrayList<>();
        Statement st;
        try {
            st = dbConnection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from task WHERE userId=" + userId);
            while (rs.next()) {
                Task t = new Task();
                t.setId(rs.getInt(1));
                t.setTask(rs.getString(2));
                t.setDueDate(rs.getString(3));
                t.setCategory(rs.getString(4));
                t.setPriority(rs.getString(5));
                t.setStatus(rs.getString(6));
                t.setUserId(rs.getInt(7));
                tasks.add(t);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return tasks;
    }

    public static void insertTask(Task task) {
        try {
            String query = "Insert into Task (id, task, dueDate, category, priority, userId)"
                    + " values (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
            preparedStmt.setInt(1, task.getId());
            preparedStmt.setString(2, task.getTask());
            preparedStmt.setString(3, task.getDueDate());
            preparedStmt.setString(4, task.getCategory());
            preparedStmt.setString(5, task.getPriority());
            preparedStmt.setInt(6, task.getUserId());

            int rowsUpdated = preparedStmt.executeUpdate();
            System.out.println(rowsUpdated + " rows updated");
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateTask(Task task) {
        try {
            String query = "Update Task set task = ?, dueDate = ?, category = ?, priority = ?, status = ? where id = ? and userId = ?";
            PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
            preparedStmt.setString(1, task.getTask());
            preparedStmt.setString(2, task.getDueDate());
            preparedStmt.setString(3, task.getCategory());
            preparedStmt.setString(4, task.getPriority());
            preparedStmt.setString(5, task.getStatus());
            preparedStmt.setInt(6, task.getId());
            preparedStmt.setInt(7, task.getUserId());
            preparedStmt.executeUpdate();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTask(int taskId) throws SQLException {
        String query = "Delete from task where id = ?";
        PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
        preparedStmt.setInt(1, taskId);
        preparedStmt.execute();
        preparedStmt.close();
    }

    public static void completeTask(int taskId) throws SQLException {
        String query = "Update task set status = 'Completed' where id = ?";
        PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
        preparedStmt.setInt(1, taskId);
        preparedStmt.executeUpdate();
        preparedStmt.close();
    }

    public static void main(String []arg) {
//        List<Task> t = getTaskListByUserId(1);
//        System.out.println(t.size());
        updateTask(new Task(4, "Yeah yeah", "2017-11-20 16:47:08", "Personal", "Minor", "Completed", 1));
    }

}
