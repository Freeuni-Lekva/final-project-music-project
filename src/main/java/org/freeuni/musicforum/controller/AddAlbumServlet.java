package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.file.processor.FileProcessor;
import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.AlbumIdentifier;
import org.freeuni.musicforum.model.Song;
import org.freeuni.musicforum.service.AlbumService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;

@MultipartConfig
public class AddAlbumServlet extends HttpServlet {

    private final String ALBUM_COVER_PATH = "src/main/webapp/";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/addAlbum.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AlbumService service = (AlbumService) getServletContext().getAttribute("albumService");

        String albumName = req.getParameter("albumName");
        String artistName = req.getParameter("artistName");
        int songAmount = Integer.parseInt(req.getParameter("songAmount"));
        if(songAmount < 0) songAmount = 0;
        AlbumIdentifier id = new AlbumIdentifier(albumName, artistName);

        ArrayList<Song> songs = new ArrayList<>();

        Part part = req.getPart("coverImage");
        String nameForImage = albumName + "_" + artistName + "_cover";
        String uploadPath = getPath(req, "images/album-covers");
        FileProcessor imageProcessor = new FileProcessor(part, nameForImage, uploadPath);

        
        Album newAlbum = new Album(albumName, artistName,
                imageProcessor.getBase64EncodedString(), songs, id);
        service.addNewAlbum(newAlbum);

        req.setAttribute("songAmount", songAmount);
        req.getSession().setAttribute("lastlyAddedAlbumId", id);
        req.getRequestDispatcher("/WEB-INF/addSongs.jsp").forward(req, resp);
    }



    private String getPath(HttpServletRequest req, String folder) {
        ServletContext context = req.getServletContext();
        String realPath = context.getRealPath("");
        String realPathWithoutTarget = realPath.substring(0, realPath.indexOf("target"));
        String pathFromContextRoot = ALBUM_COVER_PATH + folder;
        return realPathWithoutTarget + pathFromContextRoot;
    }
}
