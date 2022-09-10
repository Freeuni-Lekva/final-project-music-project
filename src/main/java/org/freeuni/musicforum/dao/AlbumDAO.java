package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.filter.Filter;
import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.Review;
import org.freeuni.musicforum.service.ServiceFactory;

import java.util.List;

public interface AlbumDAO {
    void add(Album album);

    Album getById(String id);

    List<Album> getAllByUser(String username);

    default int getAverageStar(String id) {
        List<Review> allReviews = ServiceFactory.getReviewService().getAllReviewsFor(id);
        if (allReviews.size() == 0) return 0;
        int totalStars = allReviews.stream().map(rev -> rev.getStarCount()).
                reduce(0, (sum, elem) -> sum + elem);
        double result = (double) totalStars / allReviews.size();
        return (int) Math.round(result);
    }

    boolean exists(String id);

    default int calculatePrestigeFor(String id) {
        List<Review> allReviews = ServiceFactory.getReviewService().getAllReviewsFor(id);
        if (allReviews.size() == 0) return 0;
        int totalStars = allReviews.stream().map(rev -> rev.getStarCount()).
                reduce(0, (sum, elem) -> sum + elem);
        return totalStars - (3 * allReviews.size());
    }

    void delete(String id);

    List<Album> getFiltered(Filter f);
}
