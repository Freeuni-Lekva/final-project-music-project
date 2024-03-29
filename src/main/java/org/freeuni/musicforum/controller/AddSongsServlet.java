package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.fileProcessor.FileProcessor;
import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.Song;
import org.freeuni.musicforum.service.AlbumService;
import org.freeuni.musicforum.service.ServiceFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig
public class AddSongsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AlbumService service = ServiceFactory.getAlbumService();

        String id = (String) req.getSession().getAttribute("currAlbumId");
        Album album = service.getAlbum(id);
        int songAmount = Integer.parseInt(req.getParameter("songAmount"));


        for(int i = 1; i <= songAmount; i++) {
            String name = req.getParameter("name"+i);
            String fullName = album.artistName() + "_" + album.albumName() + "_" + name;
            if(service.doesSongExist(id, fullName)) continue;

            Song song = new Song(name, fullName, album.albumName(), album.artistName());
            ArrayList<Song> songs = new ArrayList<>();
            songs.add(song);
            service.addSongs(id, songs);
        }



        req.setAttribute("album", service.getAlbum(id));
        req.getRequestDispatcher("/WEB-INF/previewAlbum.jsp").forward(req, resp);

    }

}
