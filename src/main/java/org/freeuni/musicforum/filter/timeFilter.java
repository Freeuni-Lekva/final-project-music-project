package org.freeuni.musicforum.filter;

import org.freeuni.musicforum.model.SearchRequest;

import java.time.LocalDate;
import java.util.Date;

public class timeFilter implements Filter{

    private Date dateToCompare;

    public timeFilter(Date dateToCompare){
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
