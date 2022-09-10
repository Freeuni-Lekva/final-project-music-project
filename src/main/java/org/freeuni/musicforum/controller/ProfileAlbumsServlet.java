package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.exception.NoSuchUserExistsException;
import org.freeuni.musicforum.model.*;
import org.freeuni.musicforum.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileAlbumsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PublicUserData currUser = (PublicUserData) req.getSession().getAttribute("currentUser");
        if (currUser == null || currUser.status().equals(Status.BANNED)) {
            req.getRequestDispatcher("").forward(req, resp);
        } else {
            String username = req.getParameter("username");
            PublicUserData userData = ServiceFactory.getUserService().getProfileData(username);
            if (userData.status().equals(Status.BANNED)) {
                throw new NoSuchUserExistsException("User with username " + username + " does not exist.");
            }
            req.setAttribute("user", userData);
            req.getRequestDispatcher("WEB-INF/profile.jsp").forward(req, resp);
        }

    }

}
