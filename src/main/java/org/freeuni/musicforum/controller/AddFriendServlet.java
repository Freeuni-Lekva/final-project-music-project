package org.freeuni.musicforum.controller;


import org.freeuni.musicforum.file.processor.FileProcessor;
import org.freeuni.musicforum.model.FriendshipStatus;
import org.freeuni.musicforum.model.PublicUserData;
import org.freeuni.musicforum.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddFriendServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PublicUserData currentUser = (PublicUserData) req.getSession().getAttribute("currentUser");
        PublicUserData user =  ServiceFactory.getUserService().getProfileData(req.getParameter("username"));

        FriendshipStatus fs = ServiceFactory.getUserService().getFriendshipStatus(currentUser.username(), user.username());

        if(fs==null){
            ServiceFactory.getUserService().sendFriendRequest(currentUser.username(), user.username());
        } else if (fs==FriendshipStatus.ACCEPT_REQUEST) {
            ServiceFactory.getUserService().acceptFriendRequest(currentUser.username(), user.username());
        } else if (fs==FriendshipStatus.FRIENDS){
            ServiceFactory.getUserService().removeFriendshipStatus(currentUser.username(), user.username());
        }


        req.setAttribute("user", user);
        req.setAttribute("imagePrefix", FileProcessor.IMAGE_HTML_PREFIX_BASE64);
        req.getRequestDispatcher(req.getParameter("filepath")).forward(req, resp);


    }

}
