package com.example.myusers.web;

import com.example.myusers.dao.UserDAO;
import com.example.myusers.model.User;



import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println(userDAO.getAllUsers());
        String action = request.getParameter("action");
        switch (action == null ? "All" : action) {
            case "delete":
                deleteUser(request, response);
                break;
            case "create":
                addNewUser(request, response);
                break;
            case "update":
                updateUser(request, response);
                break;
            default:
               showAllUsers(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
        user.setAge(Integer.parseInt(request.getParameter("age")));
        if (!request.getParameter("id").equals("")) {
            user.setId(Long.valueOf(request.getParameter("id")));
            userDAO.update(user);
        } else {
            userDAO.save(user);
        }
        response.sendRedirect("user");

    }

    public void showAllUsers(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = userDAO.getAllUsers();
        request.setAttribute("user", users);
        try {
            request.getRequestDispatcher("user.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addNewUser(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("user", new User());
        try {
            request.getRequestDispatcher("new-user.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(
                "user",
                userDAO.getUserById
                        (Long.parseLong(request.getParameter("id"))));
        try {
            request.getRequestDispatcher("new-user.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(HttpServletRequest request, HttpServletResponse response) {
        userDAO.delete(Long.parseLong(request.getParameter("id")));
        try {
            response.sendRedirect("user");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}