package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.dao.AlbumDAO;
import org.freeuni.musicforum.file.processor.ImageProcessor;
import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.AlbumIdentifier;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@MultipartConfig
public class AddAlbumServlet extends HttpServlet {

    private final String ALBUM_COVER_PATH = "src/main/webapp/images/album-covers";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/addAlbum.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String albumName = req.getParameter("albumName");
        String artistName = req.getParameter("artistName");
        AlbumIdentifier id = new AlbumIdentifier(albumName, artistName);
        AlbumDAO dao = (AlbumDAO) getServletContext().getAttribute("albumDAO");
        if(dao.exists(id)) {
            throw new IllegalArgumentException();
        }
        String nameForImage = albumName + "_" + artistName + "_cover";
        ImageProcessor newImage = new ImageProcessor(req.getPart("coverImage"), nameForImage, getPath(req));
        Album newAlbum = new Album(albumName, artistName,
                newImage.getBase64EncodedString(), null, id);
        dao.add(newAlbum);

        req.setAttribute("currAlbum", id);
        req.getRequestDispatcher("/WEB-INF/previewAlbum.jsp").forward(req, resp);
    }


    private String getPath(HttpServletRequest req) {
        ServletContext context = req.getServletContext();
        String realPath = context.getRealPath("");
        String realPathWithoutTarget = realPath.substring(0, realPath.indexOf("target"));
        String pathFromContextRoot = ALBUM_COVER_PATH;
        return realPathWithoutTarget + pathFromContextRoot;
    }
}
