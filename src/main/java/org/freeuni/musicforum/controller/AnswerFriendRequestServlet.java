package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.fileProcessor.FileProcessor;
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
        
        PublicUserData currentUser = (PublicUserData) req.getSession().getAttribute("currentUser");
        String username = req.getParameter("username");
        String action = req.getParameter("action");

        if (action.equals("Accept Request")) {
            ServiceFactory.getUserService().acceptFriendRequest(currentUser.username(), username);
        } else if (action.equals("Delete Request")) {
            ServiceFactory.getUserService().removeFriendRequest(currentUser.username(), username);
        }


        req.setAttribute("imagePrefix", FileProcessor.IMAGE_HTML_PREFIX_BASE64);
        req.setAttribute("user", currentUser);
        req.getRequestDispatcher(req.getParameter("filepath")).forward(req, resp);

    }

}
