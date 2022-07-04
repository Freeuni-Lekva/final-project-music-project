package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.dao.AlbumDAO;
import org.freeuni.musicforum.exception.AlbumExistsException;
import org.freeuni.musicforum.file.processor.FileProcessor;
import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.AlbumIdentifier;
import org.freeuni.musicforum.model.Song;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@MultipartConfig
public class AddAlbumServlet extends HttpServlet {

    private final String ALBUM_COVER_PATH = "src/main/webapp/";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/addAlbum.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AlbumDAO dao = (AlbumDAO) getServletContext().getAttribute("albumDAO");

        Collection<Part> parts  = req.getParts();
        String albumName = req.getParameter("albumName");
        String artistName = req.getParameter("artistName");
        AlbumIdentifier id = new AlbumIdentifier(albumName, artistName);

        if(dao.exists(id)) {
            throw new AlbumExistsException();
        }

        FileProcessor newImage = null;
        ArrayList<Song> songs = new ArrayList<>();
        for(Part part : parts) {
            if(part.getName().equals("coverImage")) {
                String nameForImage = albumName + "_" + artistName + "_cover";
                newImage = new FileProcessor(part, nameForImage, getPath(req, "images/album-covers"));
            }

            if(part.getName().equals("albumSongs")) {
                String originalName = part.getSubmittedFileName();
                String nameForSong = albumName + "_" + artistName + "_" + originalName.substring(0, originalName.lastIndexOf("."));
                FileProcessor newSong = new FileProcessor(part, nameForSong, getPath(req, "songs"));
                Song curr = new Song("ex", albumName, artistName, newSong.getBase64EncodedString(), 0);
                songs.add(curr);
            }
        }


        Album newAlbum = new Album(albumName, artistName,
                newImage.getBase64EncodedString(), songs, id);
        dao.add(newAlbum);

        req.setAttribute("currAlbum", id);
        req.getRequestDispatcher("/WEB-INF/previewAlbum.jsp").forward(req, resp);
    }



    private String getPath(HttpServletRequest req, String folder) {
        ServletContext context = req.getServletContext();
        String realPath = context.getRealPath("");
        String realPathWithoutTarget = realPath.substring(0, realPath.indexOf("target"));
        String pathFromContextRoot = ALBUM_COVER_PATH + folder;
        return realPathWithoutTarget + pathFromContextRoot;
    }
}
