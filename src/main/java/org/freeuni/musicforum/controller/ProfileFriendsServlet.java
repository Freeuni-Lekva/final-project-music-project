package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.model.PublicUserData;
import org.freeuni.musicforum.model.Status;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ProfileFriendsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PublicUserData currentUser = (PublicUserData) req.getSession().getAttribute("currentUser");
        if (currentUser == null || currentUser.status().equals(Status.BANNED)) {
            req.getRequestDispatcher("").forward(req, resp);
        } else {
            req.setAttribute("user", currentUser);
            req.getRequestDispatcher("WEB-INF/profile_friends.jsp").forward(req, resp);
        }
    }

}
