package org.freeuni.musicforum.filter;


import org.freeuni.musicforum.model.SearchRequest;

public interface Filter {
      boolean doFilter(SearchRequest request);
}