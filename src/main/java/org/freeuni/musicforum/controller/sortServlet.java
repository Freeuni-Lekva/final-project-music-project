package org.freeuni.musicforum.controller;

import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.Review;
import org.freeuni.musicforum.service.AlbumService;
import org.freeuni.musicforum.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class sortServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String sortAlbumsBy = req.getParameter("sortAlbumsBy");
        String sortReviewsBy = req.getParameter("sortReviewsBy");

        if(sortAlbumsBy!=null){
            List<Album> albums = (List<Album>) req.getSession().getAttribute("listToSortAlbums");
            AlbumService al  = ServiceFactory.getAlbumService();
            if(sortAlbumsBy.equals("Stars")){
                albums = albums.stream().sorted((o1, o2) -> al.getAverageStarFor(o2.id())-al.getAverageStarFor(o1.id())).toList();
            } else{
                albums = albums.stream().sorted((o1, o2) -> o2.uploadDate().compareTo(o1.uploadDate())).toList();
            }
            req.setAttribute("filteredAlbums", albums);
        }

        if(sortReviewsBy!=null){
            List<Review> reviews = (List<Review>) req.getSession().getAttribute("listToSortReviews");
            if(sortReviewsBy.equals("Upvotes")){
                reviews = reviews.stream().sorted((o1, o2) -> o2.getPrestige()-o1.getPrestige()).toList();
            } else{
                reviews = reviews.stream().sorted((o1, o2) -> o2.getUploadDate().compareTo(o1.getUploadDate())).toList();
            }
            req.setAttribute("filteredReviews", reviews);

        }

        req.getSession().setAttribute("listToSortAlbums",null);
        req.getSession().setAttribute("listToSortReviews",null);
        req.getRequestDispatcher("/WEB-INF/searchResults.jsp").forward(req, resp);

    }



}
