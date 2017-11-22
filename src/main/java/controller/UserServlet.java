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
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        User user = new User(username, email);
        PrintWriter out = response.getWriter();
        try {
            int userId = DatabaseManager.insertUser(user);
            user.setId(userId);
            String JSONUser = new Gson().toJson(user);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            System.out.println(JSONUser);
            out.write(JSONUser);
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
