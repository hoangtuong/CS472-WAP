package controller;

import com.google.gson.Gson;
import database.DatabaseManager;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userJsonString = request.getParameter("user");
        User user = new Gson().fromJson(userJsonString, User.class);
        PrintWriter out = response.getWriter();
        try {
            DatabaseManager.insertUser(user);
            out.print("SUCCESS: add user: " + user.toString());
        } catch (SQLException ex) {
            out.print("ERROR: add user: " + user.toString());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String JSONUsers = new Gson().toJson(DatabaseManager.getUsers());
            response.setContentType("application/json");
            out.write(JSONUsers);

        } catch (SQLException ex) {
            out.write("ERROR: Get users from DB");
        }
    }
}
