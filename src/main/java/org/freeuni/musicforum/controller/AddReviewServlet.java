package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.file.processor.FileProcessor;
import org.freeuni.musicforum.model.Review;
import org.freeuni.musicforum.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddReviewServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String number = req.getParameter("stars");
        int stars = (number == null) ? 0 : Integer.parseInt(number);
        String reviewText = req.getParameter("reviewText");
        String albumId = req.getParameter("albumId");
        String reviewAuthor = req.getParameter("authorUsername");
        Review review = new Review(albumId,reviewAuthor, reviewText, stars);
        ServiceFactory.getReviewService().postReview(review);

        req.setAttribute("album", ServiceFactory.getAlbumService().getAlbum(albumId));
        req.setAttribute("imagePrefix", FileProcessor.IMAGE_HTML_PREFIX_BASE64);
        req.setAttribute("audioPrefix", FileProcessor.AUDIO_HTML_PREFIX_BASE64);
        req.getRequestDispatcher("/WEB-INF/previewAlbum.jsp").forward(req, resp);

    }

}
