package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.model.*;
import org.freeuni.musicforum.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileReviewsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        PublicUserData userData = ServiceFactory.getUserService().getProfileData(username);
        req.setAttribute("user", userData);
        req.getRequestDispatcher("WEB-INF/profile_reviews.jsp").forward(req, resp);

    }

}