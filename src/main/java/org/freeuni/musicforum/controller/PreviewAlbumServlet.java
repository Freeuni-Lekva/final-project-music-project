package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.PublicUserData;
import org.freeuni.musicforum.model.Status;
import org.freeuni.musicforum.service.ServiceFactory;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class PreviewAlbumServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PublicUserData user = (PublicUserData) req.getSession().getAttribute("currentUser");
        if (user == null || user.status().equals(Status.BANNED)) {
            req.getRequestDispatcher("").forward(req, resp);
        } else {
            String albumId = req.getParameter("albumId");
            Album album = ServiceFactory.getAlbumService().getAlbum(albumId);
            req.setAttribute("album", album);
            req.getRequestDispatcher("/WEB-INF/previewAlbum.jsp").forward(req, resp);
        }
    }

}
