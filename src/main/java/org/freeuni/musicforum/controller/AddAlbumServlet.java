package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.fileProcessor.FileProcessor;
import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.PublicUserData;
import org.freeuni.musicforum.model.Song;
import org.freeuni.musicforum.service.AlbumService;
import org.freeuni.musicforum.service.ServiceFactory;
import org.freeuni.musicforum.util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

@MultipartConfig
public class AddAlbumServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PublicUserData user = (PublicUserData) req.getSession().getAttribute("currentUser");
        if (user == null) {
            req.getRequestDispatcher("").forward(req, resp);
        } else {
            req.getSession().setAttribute("uploader", user.username());
            req.getRequestDispatcher("/WEB-INF/addAlbum.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AlbumService service = ServiceFactory.getAlbumService();

        String albumName = req.getParameter("albumName");
        String artistName = req.getParameter("artistName");
        int songAmount = Integer.parseInt(req.getParameter("songAmount"));
        String id = Utils.hashText(albumName + artistName);
        ArrayList<Song> songs = new ArrayList<>();

        Part part = req.getPart("coverImage");
        String nameForImage = albumName + "_" + artistName + "_cover";
        String pathFromWebFolder =  "images/album-covers";
        FileProcessor imageProcessor = new FileProcessor(part, nameForImage, req, pathFromWebFolder);


        String username = (String) req.getSession().getAttribute("uploader");
        Album newAlbum = new Album(username, albumName, artistName,
                imageProcessor.getBase64EncodedString(), songs, id, new Date());
        service.addNewAlbum(newAlbum);

        req.setAttribute("songAmount", songAmount);
        req.getSession().setAttribute("currAlbumId", id);
        req.getRequestDispatcher("/WEB-INF/addSongs.jsp").forward(req, resp);

    }





}
