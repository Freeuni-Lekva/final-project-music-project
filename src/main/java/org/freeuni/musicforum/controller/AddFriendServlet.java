package org.freeuni.musicforum.controller;


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

        PublicUserData currentUser = (PublicUserData) req.getServletContext().getAttribute("currentUser");
        PublicUserData user =  ServiceFactory.getUserService().getProfileData(req.getParameter("username"));

        FriendshipStatus fs = ServiceFactory.getUserService().getFriendshipStatus(currentUser.username(), user.username());
        String buttonText = FriendshipStatus.FRIENDS.toString();

        if(fs==null){
            buttonText = FriendshipStatus.REQUEST_SENT.toString();
            ServiceFactory.getUserService().sendFriendRequest(currentUser.username(), user.username());
        } else if (fs==FriendshipStatus.ACCEPT_REQUEST) {
            ServiceFactory.getUserService().acceptFriendRequest(currentUser.username(), user.username());
        } else if (fs==FriendshipStatus.REQUEST_SENT){
            buttonText = FriendshipStatus.REQUEST_SENT.toString();;
        }


        req.setAttribute("buttonText", buttonText);
        req.setAttribute("user", user);
        req.getRequestDispatcher(req.getParameter("filepath")).forward(req, resp);


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }


}
