package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.model.AlbumIdentifier;
import org.freeuni.musicforum.model.PublicUserData;
import org.freeuni.musicforum.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteAlbumServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("deleteAlbumId");
        ServiceFactory.getAlbumService().deleteAlbum(id);
        PublicUserData currentUser = (PublicUserData) req.getSession().getAttribute("currentUser");
        req.setAttribute("user", currentUser);

        req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);

    }

}
