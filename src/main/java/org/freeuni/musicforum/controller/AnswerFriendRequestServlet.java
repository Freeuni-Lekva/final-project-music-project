package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.model.FriendshipStatus;
import org.freeuni.musicforum.model.PublicUserData;
import org.freeuni.musicforum.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AnswerFriendRequestServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PublicUserData currentUser = (PublicUserData) req.getServletContext().getAttribute("currentUser");
        String username = req.getParameter("username");
        String action = req.getParameter("action");
        FriendshipStatus fs = ServiceFactory.getUserService().getFriendshipStatus(currentUser.username(), username);


        if (action.equals("Accept Request")) {
            ServiceFactory.getUserService().acceptFriendRequest(currentUser.username(), username);
        } else if (action.equals("Delete Request")) {
            ServiceFactory.getUserService().removeFriendshipStatus(currentUser.username(), username);
        }


        req.setAttribute("user", currentUser);
        req.getRequestDispatcher(req.getParameter("filepath")).forward(req, resp);

    }

}
