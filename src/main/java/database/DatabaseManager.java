package database;

import model.Task;
import model.User;

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

    public static List<Task> getTaskListByUserId(Integer userId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        Statement st;
        st = dbConnection.createStatement();

        // Query statement
        String queryString = "SELECT * from task";
        if (userId != null) {
            queryString += " WHERE userId=" + userId.intValue();
        }

        ResultSet rs = st.executeQuery(queryString);
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
        return tasks;
    }

    public static void insertTask(Task task) throws SQLException {
        String query = "Insert into Task (task, dueDate, category, priority, status, userId)"
                    + " values (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
        preparedStmt.setString(1, task.getTask());
        preparedStmt.setString(2, task.getDueDate());
        preparedStmt.setString(3, task.getCategory());
        preparedStmt.setString(4, task.getPriority());
        preparedStmt.setString(5, "Created");
        preparedStmt.setInt(6, task.getUserId());

        int rowsUpdated = preparedStmt.executeUpdate();
        System.out.println(rowsUpdated + " rows updated");
        preparedStmt.close();
    }

    public static void updateTask(Task task) throws SQLException {
        String query = "Update Task set task = ?, dueDate = ?, category = ?, priority = ?, status = ?, userId = ? where id = ?";
        PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
        preparedStmt.setString(1, task.getTask());
        preparedStmt.setString(2, task.getDueDate());
        preparedStmt.setString(3, task.getCategory());
        preparedStmt.setString(4, task.getPriority());
        preparedStmt.setString(5, task.getStatus());
        preparedStmt.setInt(6, task.getUserId());
        preparedStmt.setInt(7, task.getId());
        preparedStmt.executeUpdate();
        preparedStmt.close();
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

    public static List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        Statement st;
        st = dbConnection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * from user");
        while (rs.next()) {
            User u = new User();
            u.setId(rs.getInt(1));
            u.setName(rs.getString(2));
            u.setEmail(rs.getString(3));

            users.add(u);
        }
        return users;
    }

    public static int insertUser(User user) throws SQLException {
        String query = "Insert into User (name, email)"
                + " values (?, ?)";
        PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
        preparedStmt.setString(1, user.getName());
        preparedStmt.setString(2, user.getEmail());
        int rowsUpdated = preparedStmt.executeUpdate();
        System.out.println(rowsUpdated + " rows updated");

        ResultSet rs = preparedStmt.getGeneratedKeys();
        int last_inserted_id = 0;
        if(rs.next())  {
            last_inserted_id = rs.getInt(1);
        }
        preparedStmt.close();
        return last_inserted_id;
    }

    public static void main(String []arg) {
//        List<Task> t = getTaskListByUserId(1);
//        System.out.println(t.size());
        try {
//            updateTask(new Task(4, "Yeah yeah", "2017-11-20 16:47:08", "Personal", "Minor", "Completed", 1));
            List<User> users = getUsers();
            System.out.println(users.get(0).getName());
        } catch (SQLException ex) {

        }
    }

}
