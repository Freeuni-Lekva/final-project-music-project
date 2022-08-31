package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.file.processor.FileProcessor;
import org.freeuni.musicforum.model.PublicUserData;
import org.freeuni.musicforum.service.ServiceFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class VoteServlet extends HttpServlet {

    public static void helper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String votingUser = req.getParameter("votingUser");
        String reviewId = req.getParameter("reviewId");
        String vote = (String) req.getAttribute("votetype");
        if (vote.equals("up")) {
            ServiceFactory.getVotingDataService().clickUpvote(votingUser, reviewId);
        } else {
            ServiceFactory.getVotingDataService().clickDownvote(votingUser, reviewId);
        }
        if (req.getParameter("page").equals("album")) {
            req.setAttribute("album", ServiceFactory.getAlbumService().
                    getAlbum(req.getParameter("albumId")));
            req.setAttribute("imagePrefix", FileProcessor.IMAGE_HTML_PREFIX_BASE64);
            req.setAttribute("audioPrefix", FileProcessor.AUDIO_HTML_PREFIX_BASE64);
            req.getRequestDispatcher("/WEB-INF/previewAlbum.jsp").forward(req, resp);
        } else if (req.getParameter("page").equals("profilereviews")) {
            String username = req.getParameter("username");
            PublicUserData userData = ServiceFactory.getUserService().getProfileData(username);
            req.setAttribute("user", userData);
            req.setAttribute("imagePrefix", FileProcessor.IMAGE_HTML_PREFIX_BASE64);
            req.getRequestDispatcher("WEB-INF/profile_reviews.jsp").forward(req, resp);
        }
    }

}
