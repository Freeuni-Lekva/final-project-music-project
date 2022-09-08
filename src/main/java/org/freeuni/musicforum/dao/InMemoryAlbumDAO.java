package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.filter.Filter;
import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.Review;
import org.freeuni.musicforum.model.SearchRequest;
import org.freeuni.musicforum.service.ServiceFactory;
import org.freeuni.musicforum.util.Utils;

import java.util.*;

public class InMemoryAlbumDAO implements AlbumDAO{

    Map<String, Album> albums;

    public InMemoryAlbumDAO() {
        albums = new HashMap<>();
        Album al = new Album("guri", "Body Riddle", "Clark", null, new ArrayList<>(), Utils.hashText("Body Riddle"+"Clark"),new Date());
        albums.put(Utils.hashText("Body Riddle"+"Clark"), al);

    }

    @Override
    public void add(Album album) {
        albums.put(album.id(), album);
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
