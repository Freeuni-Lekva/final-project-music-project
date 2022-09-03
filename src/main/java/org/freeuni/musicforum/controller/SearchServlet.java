package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.file.processor.FileProcessor;
import org.freeuni.musicforum.filter.*;
import org.freeuni.musicforum.model.PublicUserData;
import org.freeuni.musicforum.service.ServiceFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Calendar;


public class SearchServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String searchBy = request.getParameter("searchBy");
        filterManager fm = customFilterManager(request);


        if(searchBy.equals("Users")){
            request.setAttribute("filteredUsers", ServiceFactory.getUserService().filter(fm));
        } else if(searchBy.equals("Albums")){
            request.setAttribute("filteredAlbums", ServiceFactory.getAlbumService().filter(fm));
        } else if(searchBy.equals("Reviews")){
            request.setAttribute("filteredReviews", ServiceFactory.getReviewService().filter(fm));
        }

        request.setAttribute("imagePrefix", FileProcessor.IMAGE_HTML_PREFIX_BASE64);
        request.getRequestDispatcher("/WEB-INF/searchResults.jsp").forward(request, response);

    }

    public filterManager customFilterManager(HttpServletRequest request){
        String scope = request.getParameter("scope");
        String time = request.getParameter("time");
        String key = request.getParameter("search-bar");
        PublicUserData currUser = (PublicUserData)request.getSession().getAttribute("currentUser");


        filterManager fm = new filterManager();

        if(scope.equals("Friends")){
            fm.add(new scopeFilter(currUser.username()));
        }

        Calendar calendar = Calendar.getInstance();

        if(time.equals("Last Week")){
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            fm.add(new timeFilter(calendar.getTime()));
        } else if(time.equals("Last Month")){
            calendar.add(Calendar.MONTH, -1);
            fm.add(new timeFilter(calendar.getTime()));
        }

        if(!key.equals("")){
            fm.add(new searchBarFilter(key));
        }

        return fm;
    }

}
