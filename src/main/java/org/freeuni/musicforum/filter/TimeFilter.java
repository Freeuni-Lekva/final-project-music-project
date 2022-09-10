package org.freeuni.musicforum.filter;

import org.freeuni.musicforum.model.SearchRequest;

import java.util.Date;

public class TimeFilter implements Filter{

    private Date dateToCompare;

    public TimeFilter(Date dateToCompare){
        this.dateToCompare = dateToCompare;
    }

    @Override
    public boolean doFilter(SearchRequest request) {
        Date requestDate = request.getDate();
        if(requestDate!=null){
            return dateToCompare.before(requestDate);
        }
        return true;

    }
}
