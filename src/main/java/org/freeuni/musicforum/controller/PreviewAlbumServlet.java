package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.file.processor.FileProcessor;
import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.service.ServiceFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class PreviewAlbumServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String albumId = req.getParameter("albumId");
        Album album = ServiceFactory.getAlbumService().getAlbum(albumId);
        req.setAttribute("album", album);
        req.setAttribute("imagePrefix", FileProcessor.IMAGE_HTML_PREFIX_BASE64);
        req.setAttribute("audioPrefix", FileProcessor.AUDIO_HTML_PREFIX_BASE64);
        req.getRequestDispatcher("/WEB-INF/previewAlbum.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
