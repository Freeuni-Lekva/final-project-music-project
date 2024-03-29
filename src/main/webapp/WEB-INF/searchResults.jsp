<%@ page import="java.util.List" %>
<%@ page import="org.freeuni.musicforum.service.ServiceFactory" %>
<%@ page import="org.freeuni.musicforum.model.*" %>
<%@ page import="org.freeuni.musicforum.exception.NonexistentAlbumException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Feed</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/main_styles.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/searchAndFeedStyles.css" />
</head>
<body class = "background">
    <%@include file="upper_strip.jsp"%>
    <p class="space"></p>
    <%@include file="searchbar.jsp"%>
    <% List<User> users = (List<User>) request.getAttribute("filteredUsers");
       List<Album> albums = (List<Album>) request.getAttribute("filteredAlbums");
       List<Review> reviews = (List<Review>) request.getAttribute("filteredReviews");
       PublicUserData currentUser  = (PublicUserData) request.getSession().getAttribute("currentUser");
    %>

    <div class="feed_scroll_wrapper">
    <div class = "feed_scroll">
        <%if(users!=null){
            if(users.size()==0){%>
                <p class = "big_text">No Results Found</p>
        <%  }
            for(int i = 0; i<users.size(); i++){
                User user = users.get(i); %>
            <div class="feed_scroll_member">
                <img src = "${imagePrefix}<%=user.getProfileImageBase64()%>" class="feed_scroll_photobox">
                <div class="feed_scroll_infobox">
                    <p class="feed_main_text"> <a href="/profile?username=<%=user.getUsername()%>">
                        <%=user.getUsername()%>
                    </a></p>
                    <p class="infobox_space"></p>
                    <p class="text">Badge: <%=user.getBadge().toString()%></p>
                </div>
            </div>
            <p class="space"></p>
            <%}
        }%>

        <%if(albums!=null){
            if(albums.size()==0){%>
            <p class = "big_text">No Results Found</p>
        <%  }
            for(int i = 0; i<albums.size(); i++){
                Album album = albums.get(i); %>
            <div class="feed_scroll_member">
                <img src="${imagePrefix}<%=album.coverImageBase64()%>" alt="cover image" class="feed_scroll_photobox">
                <div class="feed_scroll_infobox">
                    <% int stars = ServiceFactory.getAlbumService().getAverageStarFor(album.id()); %>
                    <% for (int j = 0; j < stars; j++) { %>
                    <img src="/images/star_sel.png" class="vote_box">
                    <% } %>
                    <% for (int j = stars; j < 5; j++) { %>
                    <img src="/images/star_unsel.png" class="vote_box">
                    <% } %>
                    <p class="text"><a href="/album?albumId=<%=album.id()%>">
                        <%=album.albumName()%>
                    </a></p>
                    <p class="small_text">Artist: <%=album.artistName()%></p>
                    <p class="infobox_smaller_space"></p>
                    <%if(ServiceFactory.getUserService().isActive(album.username())){%>
                    <p class="small_text">Uploaded By: <a href="/profile?username=<%=album.username()%>">
                        <%=album.username()%>
                    </a></p>
                    <%} else{%>
                    <p class="small_text">Uploaded By: <%=album.username()%> </p>
                    <%}%>
                </div>
            </div>
            <p class="space"></p>
            <%}
        }%>

        <%if(reviews!=null){
            if(reviews.size()==0){%>
            <p class = "big_text">No Results Found</p>
        <%  }
            request.getSession().setAttribute("filteredReviews", reviews);
                for(int i = 0; i<reviews.size(); i++){
                    Review review = reviews.get(i);
                    Album album = null;
                    try {
                        album = ServiceFactory.getAlbumService().getAlbum(review.getAlbumId());
                    } catch (NonexistentAlbumException ex) {
                    } %>
                <div class="feed_scroll_member_for_reviews">
                    <div class="feed_scroll_photobox_for_reviews">
                        <p class="text"><a href="/album?albumId=<%=album.id()%>">
                            <%=album.albumName()%>
                        </a></p>
                        <p class="small_text">Artist: <%=album.artistName()%></p>
                        <img src="${imagePrefix}<%=album.coverImageBase64()%>" alt="cover image" width="200px" height="200px">
                    </div>

                    <div class="vote_box_for_feed">
                        <% VoteType vote = ServiceFactory.getVotingDataService().
                                getUserVoteForReview(currentUser.username(), review.getId());
                        %>
                        <p>
                        <form action="/upvote" method="post">
                            <input type="hidden" name="votingUser" value="<%=currentUser.username()%>">
                            <input type="hidden" name="reviewId" value="<%=review.getId()%>">
                            <input type="hidden" name="page" value="<%="searchresults"%>">
                            <% if (vote.equals(VoteType.UPVOTE)) { %>
                            <input type="image" src="/images/up_sel.png" width="20px" height="20px">
                            <% } else { %>
                            <input type="image" src="/images/up_unsel.png" width="20px" height="20px">
                            <% } %>
                        </form>
                        </p>

                        <p class="text"> <%=review.getPrestige()%> </p>

                        <p>
                        <form action="/downvote" method="post">
                            <input type="hidden" name="votingUser" value="<%=currentUser.username()%>">
                            <input type="hidden" name="reviewId" value="<%=review.getId()%>">
                            <input type="hidden" name="page" value="<%="searchresults"%>">
                            <% if (vote.equals(VoteType.DOWNVOTE)) { %>
                            <input type="image" src="/images/down_sel.png" width="20px" height="20px">
                            <% } else { %>
                            <input type="image" src="/images/down_unsel.png" width="20px" height="20px">
                            <% } %>
                        </form>
                        </p>
                    </div>

                    <div class="feed_scroll_infobox">
                        <p class="huge_space"></p>
                        <%if(ServiceFactory.getUserService().isActive(review.getAuthorUsername())){%>
                        <p class="small_text">Uploaded By: <a href="/profile?username=<%=review.getAuthorUsername()%>">
                            <%=review.getAuthorUsername()%>
                        </a></p>
                        <%} else {%>
                        <p class="small_text">Uploaded By: <%=review.getAuthorUsername()%></p>
                        <%}%>
                        <p class="small_text">Rating: </p>
                        <% int stars = review.getStarCount(); %>
                        <% for (int j = 0; j < stars; j++) { %>
                        <img src="/images/star_sel.png" class="vote_box">
                        <% } %>
                        <% for (int j = stars; j < 5; j++) { %>
                        <img src="/images/star_unsel.png" class="vote_box">
                        <% } %>
                        <p class="small_text"><%=review.getText()%></p>
                    </div>
                </div>
                <%}
            }%>
    </div>
    </div>
</body>
</html>
