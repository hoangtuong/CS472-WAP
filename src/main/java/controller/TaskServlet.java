package controller;

import com.google.gson.Gson;
import database.DatabaseManager;
import handler.TaskAction;
import handler.TaskCompleteImpl;
import handler.TaskDeletionImpl;
import handler.TaskUpdateImpl;
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
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = (String)request.getParameter("action");

        //temporary do the default add task
        if (action == null){
            String task = request.getParameter("task");
            String dueDate = request.getParameter("dueDate");
            String category = request.getParameter("category");
            String priority = request.getParameter("priority");
            int userId = Integer.parseInt(request.getParameter("userId"));
            Task taskModel = new Task(10+userId,task,dueDate,category,priority,"null",userId);
            DatabaseManager.insertTask(taskModel);
        }
        TaskAction handler = actionHandler.get(action);
        int taskId = Integer.parseInt(request.getParameter("taskId"));
        PrintWriter out = response.getWriter();
        try {
            handler.perform(taskId);
            out.write("SUCCESS: Perform " + action + " task ID: " + taskId);
        } catch (SQLException ex) {
            ex.printStackTrace();
            out.write("ERROR: Perform " + action + " task ID: " + taskId);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String JSONtasks;
//        List<Task> taskList = new MockData().retrieveTaskList();
        int userId = Integer.parseInt(request.getParameter("userId"));
        List<Task> taskList = DatabaseManager.getTaskListByUserId(userId);
        JSONtasks = new Gson().toJson(taskList);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.write(JSONtasks);
    }
}
