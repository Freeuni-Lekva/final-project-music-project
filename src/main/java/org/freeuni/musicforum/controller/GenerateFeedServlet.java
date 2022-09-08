package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.Activity.ActivityLog;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GenerateFeedServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

       req.setAttribute("newAlbum", ActivityLog.ActivityType.NEW_ALBUM);
       req.setAttribute("newReview", ActivityLog.ActivityType.NEW_REVIEW);

        req.getRequestDispatcher("/WEB-INF/feed.jsp")
                .forward(req, resp);

    }
}
