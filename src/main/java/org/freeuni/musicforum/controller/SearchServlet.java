package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.filter.Filter;
import org.freeuni.musicforum.filter.userFilter;
import org.freeuni.musicforum.model.User;
import org.freeuni.musicforum.service.ServiceFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String key = request.getParameter("search-bar");
        Filter f = new userFilter(key);

        List<User> filtered = ServiceFactory.getUserService().getUserDao().getFiltered(f);




    }
}
