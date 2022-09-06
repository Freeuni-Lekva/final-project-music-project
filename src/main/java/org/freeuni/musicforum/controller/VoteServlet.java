package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.model.Review;
import org.freeuni.musicforum.service.AlbumService;
import org.freeuni.musicforum.service.ReviewService;
import org.freeuni.musicforum.service.ServiceFactory;
import org.freeuni.musicforum.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

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

        UserService us = ServiceFactory.getUserService();
        AlbumService as = ServiceFactory.getAlbumService();
        ReviewService rs = ServiceFactory.getReviewService();

        String albumUploader = as.getAlbum(rs.getReview(reviewId).getAlbumId()).username();
        String reviewUploader = rs.getReview(reviewId).getAuthorUsername();

        us.updateBadge(albumUploader);
        us.updateBadge(reviewUploader);

        if (req.getParameter("page").equals("album")) {
            req.setAttribute("album", as.getAlbum(req.getParameter("albumId")));
            req.getRequestDispatcher("/WEB-INF/previewAlbum.jsp").forward(req, resp);
        } else if (req.getParameter("page").equals("profilereviews")) {
            String username = req.getParameter("username");
            req.setAttribute("user", us.getProfileData(username));
            req.getRequestDispatcher("WEB-INF/profile_reviews.jsp").forward(req, resp);
        } else if(req.getParameter("page").equals("searchresults")){
            List<Review> reviewList = (List<Review>) req.getSession().getAttribute("filteredReviews");
            req.getSession().setAttribute("filteredReviews", null);
            req.setAttribute("filteredReviews", reviewList);
            req.getRequestDispatcher("WEB-INF/searchResults.jsp").forward(req, resp);
        }


    }

}
