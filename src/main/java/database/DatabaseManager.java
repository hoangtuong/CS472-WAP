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
            connection = DriverManager.getConnection("jdbc:mysql://localhost/tasklist?user=root&password=root");
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
                t.setUserId(rs.getInt(6));
                tasks.add(t);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return tasks;
    }

    public static void main(String []arg) {
        List<Task> t = getTaskListByUserId(1);
        System.out.println(t.size());

    }

}
