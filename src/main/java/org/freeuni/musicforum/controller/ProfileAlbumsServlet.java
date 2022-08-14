package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.file.processor.FileProcessor;
import org.freeuni.musicforum.model.*;
import org.freeuni.musicforum.service.ServiceFactory;
import org.freeuni.musicforum.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ProfileAlbumsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        PublicUserData userData = ServiceFactory.getUserService().getProfileData(username);
        req.setAttribute("user", userData);
        req.setAttribute("imagePrefix", FileProcessor.IMAGE_HTML_PREFIX_BASE64);
        req.getRequestDispatcher("WEB-INF/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

    }

}
