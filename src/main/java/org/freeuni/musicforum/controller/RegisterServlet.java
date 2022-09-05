package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.exception.UnsuccessfulSignupException;
import org.freeuni.musicforum.file.processor.FileProcessor;
import org.freeuni.musicforum.model.*;
import org.freeuni.musicforum.service.ServiceFactory;
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

        req.getRequestDispatcher("/WEB-INF/register.jsp")
                .forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
                firstName, lastName, birthDate, gender, username,
                new Password(password), new Badge(Badge.BadgeEnum.NEWCOMER)
        );

        UserService userService = ServiceFactory.getUserService();
        req.setAttribute("imagePrefix", FileProcessor.IMAGE_HTML_PREFIX_BASE64);
        try {
            userService.signUp(newUser);
            req.getSession().setAttribute("currentUser", userService.getProfileData(username));
            GenerateFeedServlet feedServlet = new GenerateFeedServlet();
            feedServlet.doGet(req, resp);
        }
        catch(UnsuccessfulSignupException e) {
            req.setAttribute("incorrectRegister", e.getMessage());
            doGet(req, resp);
        }

    }

}
