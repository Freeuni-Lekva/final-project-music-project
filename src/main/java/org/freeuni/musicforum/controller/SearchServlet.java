package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.filter.*;
import org.freeuni.musicforum.filter.Filter;
import org.freeuni.musicforum.model.User;
import org.freeuni.musicforum.service.ServiceFactory;
import org.freeuni.musicforum.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SearchServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String searchBy = request.getParameter("searchBy");


        filterManager fm = customFilterManager(request);

        if(searchBy.equals("Users")){
            request.setAttribute("filteredUsers", ServiceFactory.getUserService().filter(fm));
        } else if(searchBy.equals("Albums")){
            //request.setAttribute("filteredAlbums", ServiceFactory.getAlbumService().filter(fm));
        } else if(searchBy.equals("Reviews")){
            //request.setAttribute("filteredReviews", ServiceFactory.getReviewService().filter(fm));
        }


    }

    public filterManager customFilterManager(HttpServletRequest request){
        String scope = request.getParameter("scope");
        String time = request.getParameter("time");
        String key = request.getParameter("search-bar");


        filterManager fm = new filterManager();

        if(scope.equals("Friends")){
            // fm.add(new scopeFilter(request.getSession().getParameter("currentUser")));
        }


        if(time.equals("Last Week")){
            fm.add(new timeFilter(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth())));
        } else if(time.equals("Last Month")){
            fm.add(new timeFilter(LocalDate.now().with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY))));
        }

        if(!key.equals("")){
            fm.add(new searchBarFilter(key));
        }

        return fm;
    }

}
