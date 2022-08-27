package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.file.processor.FileProcessor;
import org.freeuni.musicforum.model.PublicUserData;
import org.freeuni.musicforum.service.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@MultipartConfig
public class UploadProfileImageServlet extends HttpServlet {

    private final String PATH_TO_USERS = "src/main/webapp/";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PublicUserData currentUser = (PublicUserData) req.getSession().getAttribute("currentUser");
        String username = currentUser.username();

        Part part = req.getPart("profileImage");

        if(part.getSize()>0){
            String nameForImage = username+"_image";
            String uploadPath = getPath(req, "images/profile-images");
            FileProcessor imageProcessor = new FileProcessor(part, nameForImage, uploadPath);
            String fileName = imageProcessor.getFullName();

            ServiceFactory.getUserService().updateProfilePicture(username, imageProcessor.getBase64EncodedString(), fileName);
            req.getSession().setAttribute("currentUser", ServiceFactory.getUserService().getProfileData(username)); //reset current user
        }

        req.setAttribute("user", req.getSession().getAttribute("currentUser"));
        req.setAttribute("imagePrefix", FileProcessor.IMAGE_HTML_PREFIX_BASE64);
        req.getRequestDispatcher(req.getParameter("filepath")).forward(req, resp);

    }

    private String getPath(HttpServletRequest req, String folder) {
        ServletContext context = req.getServletContext();
        String realPath = context.getRealPath("");
        String realPathWithoutTarget = realPath.substring(0, realPath.indexOf("target"));
        String pathFromContextRoot = PATH_TO_USERS + folder;
        return realPathWithoutTarget + pathFromContextRoot;
    }
}

