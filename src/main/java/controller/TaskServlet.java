package controller;

import com.google.gson.Gson;
import database.DatabaseManager;
import handler.*;
import model.Task;
import utility.MockData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/TaskServlet")
public class TaskServlet extends HttpServlet {
    private Map<String, TaskAction> actionHandler = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();
        actionHandler.put("delete", new TaskDeletionImpl());
        actionHandler.put("update", new TaskUpdateImpl());
        actionHandler.put("complete", new TaskCompleteImpl());
        actionHandler.put("insert", new TaskInsertImpl());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = (String)request.getParameter("action");
        String taskString = request.getParameter("task");
        TaskAction handler = actionHandler.get(action);
        Task task = new Gson().fromJson(taskString, Task.class);
        PrintWriter out = response.getWriter();
        try {
            handler.perform(task);
            out.write("SUCCESS: Perform " + action + " task ID: " + task.getId());
        } catch (SQLException ex) {
            ex.printStackTrace();
            out.write("ERROR: Perform " + action + " task ID: " + task.getId());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String userIdPara = request.getParameter("userId");
        Integer userId = null;
        if (userIdPara != null && !userIdPara.equals("")) {
            userId = Integer.parseInt(userIdPara);
        }
        try {
            List<Task> taskList = DatabaseManager.getTaskListByUserId(userId);
            String JSONtasks = new Gson().toJson(taskList);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.write(JSONtasks);
        } catch (SQLException ex) {
            out.write("ERROR: Get tasks from user: + " + userId);
        }

    }
}
