package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.dao.AlbumDAO;
import org.freeuni.musicforum.exception.AlbumExistsException;
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
        AlbumService service = (AlbumService) getServletContext().getAttribute("albumService");

        Collection<Part> parts  = req.getParts();
        String albumName = req.getParameter("albumName");
        String artistName = req.getParameter("artistName");
        AlbumIdentifier id = new AlbumIdentifier(albumName, artistName);

        FileProcessor imageProcessor = null;
        FileProcessor songProcessor = null;
        ArrayList<Song> songs = new ArrayList<>();

        for(Part part : parts) {
            if(part.getName().equals("coverImage")) {
                String nameForImage = albumName + "_" + artistName + "_cover";
                String uploadPath = getPath(req, "images/album-covers");
                imageProcessor = new FileProcessor(part, nameForImage, uploadPath);
            }
            if(part.getName().equals("albumSongs")) {
                String originalName = part.getSubmittedFileName();
                String nameForSong = albumName + "_" + artistName + "_" + originalName.substring(0, originalName.lastIndexOf("."));
                String uploadPath = getPath(req, "songs");
                songProcessor = new FileProcessor(part, nameForSong, uploadPath);
                Song curr = new Song("ex", albumName, artistName, songProcessor.getBase64EncodedString(), 0);
                songs.add(curr);
            }
        }

        
        Album newAlbum = new Album(albumName, artistName,
                imageProcessor.getBase64EncodedString(), songs, id);
        service.addNewAlbum(newAlbum);

        req.setAttribute("imagePrefix", imageProcessor.IMAGE_HTML_PREFIX_BASE64);
        req.setAttribute("audioPrefix", songProcessor.AUDIO_HTML_PREFIX_BASE64);
        req.setAttribute("currId", id);
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
