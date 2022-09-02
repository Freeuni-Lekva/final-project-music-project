package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.filter.Filter;
import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.Review;
import org.freeuni.musicforum.model.SearchRequest;
import org.freeuni.musicforum.service.ServiceFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryAlbumDAO implements AlbumDAO{

    Map<String, Album> albums;

    public InMemoryAlbumDAO() {
        albums = new HashMap<>();
    }

    @Override
    public void add(Album album) {
        albums.put(album.id().hashed(), album);
    }

    @Override
    public Album getById(String id) { return albums.get(id); }

    @Override
    public List<Album> getAllByUser(String username) {
        List<Album> albumsBy = new ArrayList<>();
        albums.forEach((k, v) -> {
            if (v.username().equals(username)) albumsBy.add(v);
        });
        return albumsBy;
    }

    @Override
    public int getAverageStar(String id) {
        List<Review> allReviews = ServiceFactory.getReviewService().getAllReviewsFor(id);
        if (allReviews.size() == 0) return 0;
        int totalStars = allReviews.stream().map(rev -> rev.getStarCount()).
                reduce(0, (sum, elem) -> sum + elem);
        double result = totalStars / allReviews.size();
        return (int) Math.round(result);
    }

    @Override
    public boolean exists(String id) { return albums.containsKey(id); }

    @Override
    public int calculatePrestigeFor(String id) {
        List<Review> allReviews = ServiceFactory.getReviewService().getAllReviewsFor(id);
        if (allReviews.size() == 0) return 0;
        int totalStars = allReviews.stream().map(rev -> rev.getStarCount()).
                reduce(0, (sum, elem) -> sum + elem);
        return totalStars - (3 * allReviews.size());
    }

    @Override
    public void delete(String id) {
        albums.remove(id);
    }

    @Override
    public List<Album> getFiltered(Filter f) {
        return albums.values().stream().filter(Album->f.doFilter(new SearchRequest(Album))).toList();
    }

}
