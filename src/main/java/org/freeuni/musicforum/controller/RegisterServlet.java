package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.dao.InMemoryUserDAO;
import org.freeuni.musicforum.exception.UserAlreadyExistsException;
import org.freeuni.musicforum.model.Badge;
import org.freeuni.musicforum.model.Gender;
import org.freeuni.musicforum.model.User;
import org.freeuni.musicforum.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //
        req.getRequestDispatcher("/WEB-INF/register.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        Date birthDate = null;
        try {
            birthDate = dateFormat.parse(req.getParameter("birthDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Gender gender = Gender.valueOf(req.getParameter("gender"));
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User newUser = new User(
                firstName, lastName, birthDate, gender, username, password, Badge.NEWCOMER
        );

        UserService userService = new UserService(new InMemoryUserDAO()); //will be changed to sql
        try {
            userService.signUp(newUser);
            System.out.println(newUser);
            req.getRequestDispatcher("/WEB-INF/feed.jsp")
                    .forward(req, resp);
        }
        catch(UserAlreadyExistsException e) {
            //req.setAttribute("incorrectRegister", true);
            doGet(req, resp);
        }

    }
}
