package org.freeuni.musicforum.filter;

import org.freeuni.musicforum.model.SearchRequest;

import java.time.LocalDate;
import java.util.Date;

public class timeFilter implements Filter{

    private LocalDate dateToCompare;

    public timeFilter(LocalDate dateToCompare){
        this.dateToCompare = dateToCompare;
    }

    @Override
    public boolean doFilter(SearchRequest request) {
        // implementation using getDate
        return false;
    }
}
