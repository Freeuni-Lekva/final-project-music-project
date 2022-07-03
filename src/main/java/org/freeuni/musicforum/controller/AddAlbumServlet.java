package org.freeuni.musicforum.controller;

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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/addAlbum.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String albumName = req.getParameter("albumName");
        String artistName = req.getParameter("artistName");
        AlbumIdentifier id = new AlbumIdentifier(albumName+artistName);
        String nameForImage = albumName + "_" + artistName + "_cover";
        downloadImage(req, resp, nameForImage);


    }

    private void downloadImage(HttpServletRequest req, HttpServletResponse resp, String imageName) throws ServletException, IOException {
        Part imagePart = req.getPart("coverImage");
        String originalName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
        String imageExtension = originalName.substring(originalName.lastIndexOf('.'));
        String fullName = imageName + imageExtension;
        InputStream fileContent = imagePart.getInputStream();
        String uploadPath = getPath(req);
        File uploads = new File(uploadPath);
        File file = new File(uploads, fullName);
        Files.copy(fileContent, file.toPath());

    }

    private String getPath(HttpServletRequest req) {
        ServletContext context = req.getServletContext();
        String realPath = context.getRealPath("");
        String realPathWithoutTarget = realPath.substring(0, realPath.indexOf("target"));
        String pathFromContextRoot = "src/main/webapp/images/album-covers";
        return realPathWithoutTarget + pathFromContextRoot;
    }
}
