<%@ page import="org.freeuni.musicforum.service.ServiceFactory" %>
<%@ page import="org.freeuni.musicforum.model.Review" %>
<%@ page import="java.util.List" %>
<%@ page import="org.freeuni.musicforum.model.Album" %>
<%@ page import="org.freeuni.musicforum.model.PublicUserData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Album Page</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body class="long_background" style="text-align:center">

<c:set var="album" value="${album}"></c:set>

<%PublicUserData currUser = (PublicUserData) request.getSession().getAttribute("currentUser"); %>
<% String uploader = ((Album) request.getAttribute("album")).username(); %>
<% String albumId = ((Album) request.getAttribute("album")).id().hashed(); %>

<div class="top_album">
    <div class="left_album">
        <p class="text">Uploaded by:
            <%if(ServiceFactory.getUserService().userExists(uploader)) {%>
                <a href="/profile?username=${album.username()}"><%=uploader%></a>
            <%}else{%>
                <%=uploader%>
            <%}%>
        </p>
        <p class="space"></p>
        <p class="big_text">Artist: ${album.artistName()}</p>
        <p class="big_text">Album: ${album.albumName()}</p>

        <% int stars = ServiceFactory.getAlbumService().
                getAverageStarFor((String) request.getAttribute("currAlbumId")); %>
        <% for (int i = 0; i < stars; i++) { %>
        <img src="/images/star_sel.png" style="width: 30px; height: 30px">
        <% } %>
        <% for (int i = stars; i < 5; i++) { %>
        <img src="/images/star_unsel.png" style="width: 30px; height: 30px">
        <% } %>
        <p class="space"></p>
        <img src="${imagePrefix}${album.coverImageBase64()}"  alt="cover image" width="300px" height="300px" />
    </div>

    <div class="right_album">
        <% if (currUser != null && currUser.badge().isAdministrator()) { %>
        <form action = "/deleteAlbum" method="post">
            <input type="hidden" name="deleteAlbumId" value="${album.id().hashed()}">
            <input type="submit" id="deleteButton" value="Delete Album" class="text" class="button">
        </form>
        <p class = "space"></p>
        <% } %>
        <ul> <c:forEach var="song" items="${album.songs()}">
            <p class="space"></p>
            <li>
                <p class="text">${song.name()}</p>
                <audio src="${audioPrefix}<c:out value="${song.songBase64()}"/>" controls></audio>
            </li>
        </c:forEach>
        </ul>
    </div>
</div>

<div class="bottom_album">
    <p class="text">Reviews</p>
    <% if (currUser != null && !currUser.username().equals(uploader) &&
    !ServiceFactory.getReviewService().userHasReviewForAlbum(currUser.username(), albumId)) {%>
    <div class="review_album">
        <form action="/addReview" method="post">
            <input type="hidden" name="albumId" value="${album.id().hashed()}">
            <input type="hidden" name="authorUsername" value="<%=currUser.username()%>">
            <label for="stars">Rating (Between 0 and 5):</label>
            <input type="number" id="stars" name="stars" min="0" max="5">
            <p class="space"></p>
            <input type="text" id="reviewText" name="reviewText" class="huge_textbox">
            <p class="space"></p>
            <input type="submit" value="Add Review" class="text" class="button">
            <p class="space"></p>
        </form>
    </div>
    <%}
    List<Review> reviews = ServiceFactory.getReviewService().
        getAllReviewsFor(albumId); %>
    <% for (Review rev : reviews) { %>
    <div class="review_album">
        <p class="text">
            <% String author = rev.getAuthorUsername();
            if(ServiceFactory.getUserService().userExists(author)) {%>
            <a href="/profile?username=<%=rev.getAuthorUsername()%>"><%=rev.getAuthorUsername()%></a>
            <%}else{%>
            <%=rev.getAuthorUsername()%>
            <%}%>
        </p>
        <p class="space"></p>
        <p class="small_text"><%=rev.getText()%></p>
        <p class="space"></p>
        <p class="text">
            <img src="/images/up_sel.png" class="vote_box">
            <%=rev.getPrestige()%>
        </p>
    </div>
    <% } %>
</div>

</body>
</html>
