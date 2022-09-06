package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.fileProcessor.FileProcessor;
import org.freeuni.musicforum.model.PublicUserData;
import org.freeuni.musicforum.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@MultipartConfig
public class UploadProfileImageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PublicUserData currentUser = (PublicUserData) req.getSession().getAttribute("currentUser");
        String username = currentUser.username();

        Part part = req.getPart("profileImage");

        if(part.getSize()>0){
            String nameForImage = username+"_image";
            String pathFromWebFolder = "images/profile-images";
            FileProcessor imageProcessor = new FileProcessor(part, nameForImage, req, pathFromWebFolder);
            String fileName = imageProcessor.getFullName();

            ServiceFactory.getUserService().updateProfilePicture(username, imageProcessor.getBase64EncodedString(), fileName);
            req.getSession().setAttribute("currentUser", ServiceFactory.getUserService().getProfileData(username)); //reset current user
        }

        req.setAttribute("user", req.getSession().getAttribute("currentUser"));
        req.getRequestDispatcher(req.getParameter("filepath")).forward(req, resp);

    }

}

