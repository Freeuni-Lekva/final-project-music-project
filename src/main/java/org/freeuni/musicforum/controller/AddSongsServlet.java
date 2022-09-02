package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.file.processor.FileProcessor;
import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.Song;
import org.freeuni.musicforum.service.AlbumService;

import javax.servlet.ServletContext;
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

    private final String PATH_TO_ALBUMS = "src/main/webapp/";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AlbumService service = (AlbumService) getServletContext().getAttribute("albumService");

        String id = (String) req.getSession().getAttribute("currAlbumId");
        Album album = service.getAlbum(id);
        int songAmount = Integer.parseInt(req.getParameter("songAmount"));

        List<Song> songs = new ArrayList<>();

        for(int i = 1; i <= songAmount; i++) {
            String name = req.getParameter("name"+i);
            String fullName = album.artistName() + "_" + album.albumName() + "_" + name;
            if(service.doesSongExist(id, name)) continue;

            Part part = req.getPart("song"+i);
            String path = getPath(req, "songs");
            FileProcessor songProcessor = new FileProcessor(part, name, path);

            Song song = new Song(name, fullName, album.albumName(), album.artistName(), songProcessor.getBase64EncodedString());
            songs.add(song);
        }

        service.addSongs(id, songs);

        req.setAttribute("album", album);
        req.setAttribute("imagePrefix", FileProcessor.IMAGE_HTML_PREFIX_BASE64);
        req.setAttribute("audioPrefix", FileProcessor.AUDIO_HTML_PREFIX_BASE64);
        req.getRequestDispatcher("/WEB-INF/previewAlbum.jsp").forward(req, resp);

    }

    private String getPath(HttpServletRequest req, String folder) {
        ServletContext context = req.getServletContext();
        String realPath = context.getRealPath("");
        String realPathWithoutTarget = realPath.substring(0, realPath.indexOf("target"));
        String pathFromContextRoot = PATH_TO_ALBUMS + folder;
        return realPathWithoutTarget + pathFromContextRoot;
    }
}
