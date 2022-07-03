package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.model.Badge;
import org.freeuni.musicforum.model.Gender;
import org.freeuni.musicforum.model.Password;
import org.freeuni.musicforum.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileReviewsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // just for testing
        req.setAttribute("user", new User("Lord", "Vader", null,
                Gender.OTHER, "Contort", new Password("Bondo"), Badge.NEWCOMER));
        // No direct access to url "/profile_reviews" should later be allowed.
        req.getRequestDispatcher("WEB-INF/profile_reviews.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

    }

}