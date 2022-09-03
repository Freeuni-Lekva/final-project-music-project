package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.model.PublicUserData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PublicUserData currentUser = (PublicUserData) req.getSession().getAttribute("currentUser");
        if(currentUser!=null){
            req.getSession().invalidate();
        }
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);

    }
}
