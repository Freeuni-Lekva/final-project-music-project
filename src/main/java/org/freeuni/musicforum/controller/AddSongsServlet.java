package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.model.AlbumIdentifier;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@MultipartConfig
public class AddSongsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AlbumIdentifier id = (AlbumIdentifier) req.getSession().getAttribute("lastlyAddedAlbumId");
        int songAmount = Integer.parseInt(req.getParameter("songAmount"));

        for(int i = 1; i <= songAmount; i++) {
            String name = req.getParameter("name"+i);
            Part part = req.getPart("song"+i);
            System.out.println(name + " " + part.getSubmittedFileName());
        }

    }
}
