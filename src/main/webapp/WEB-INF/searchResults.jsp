<%@ page import="java.util.List" %>
<%@ page import="org.freeuni.musicforum.service.ServiceFactory" %>
<%@ page import="org.freeuni.musicforum.model.*" %>
<%@ page import="org.freeuni.musicforum.exception.NonexistentAlbumException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Feed</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body class = "background">
    <%@include file="searchbar.jsp"%>

    <div class = "feed_scroll">
        <% List<User> users = (List<User>) request.getAttribute("filteredUsers");
        if(users!=null){
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

        <% List<Album> albums = (List<Album>) request.getAttribute("filteredAlbums");
        if(albums!=null){
            for(int i = 0; i<albums.size(); i++){
                Album album = albums.get(i); %>
            <div class="feed_scroll_member">
                <img src="${imagePrefix}${album.coverImageBase64()}" class="feed_scroll_photobox">
                <div class="feed_scroll_infobox">
                    <% int stars = ServiceFactory.getAlbumService().getAverageStarFor(album.id().hashed()); %>
                    <% for (int j = 0; j < stars; j++) { %>
                    <img src="/images/star_sel.png" class="vote_box">
                    <% } %>
                    <% for (int j = stars; j < 5; j++) { %>
                    <img src="/images/star_unsel.png" class="vote_box">
                    <% } %>
                    <p class="text"><a href="/album?albumId=<%=album.id().hashed()%>">
                        <%=album.albumName()%>
                    </a></p>
                    <p class="small_text">Artist: <%=album.artistName()%></p>
                    <p class="infobox_smaller_space"></p>
                    <p class="small_text">Uploaded By: <a href="/profile?username=<%=album.username()%>">
                        <%=album.username()%>
                    </a></p>
                </div>
            </div>
            <p class="space"></p>
            <%}
        }%>

        <% List<Review> reviews = (List<Review>) request.getAttribute("filteredReviews");
            if(reviews!=null){
                for(int i = 0; i<reviews.size(); i++){
                    Review review = reviews.get(i);
                    Album album = null;
                    try {
                        album = ServiceFactory.getAlbumService().getAlbum(review.getAlbumId());
                    } catch (NonexistentAlbumException ex) {
                    } %>
                <div class="feed_scroll_member_for_reviews">
                    <%-- will add upvote and reply later --%>
                    <div class="feed_scroll_photobox_for_reviews">
                        <p class="text"><a href="/album?albumId=<%=album.id().hashed()%>">
                            <%=album.albumName()%>
                        </a></p>
                        <p class="small_text">Artist: <%=album.artistName()%></p>
                        <img src="${imagePrefix}${album.coverImageBase64()}" width="200px" height="200px">
                    </div>
                    <div class="feed_scroll_infobox">
                        <p class="huge_space"></p>
                        <p class="small_text">Uploaded By: <a href="/profile?username=<%=review.getAuthorUsername()%>">
                            <%=review.getAuthorUsername()%>
                        </a></p>
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
</body>
</html>
