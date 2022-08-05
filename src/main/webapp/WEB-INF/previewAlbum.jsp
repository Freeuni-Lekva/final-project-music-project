<%@ page import="org.freeuni.musicforum.service.ServiceFactory" %>
<%@ page import="org.freeuni.musicforum.model.Review" %>
<%@ page import="java.util.List" %>
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

<div class="top_album">
    <div class="left_album">
        <p class="text">Uploaded by: <a href="/profile?username=${album.username()}">${album.username()}</a></p>
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
    <% List<Review> reviews = ServiceFactory.getReviewService().
        getAllReviewsFor((String) request.getAttribute("currAlbumId")); %>
    <% for (Review rev : reviews) { %>
    <div class="review_album">
        <p class="text">
            <a href="/profile?username=<%=rev.getAuthorUsername()%>"><%=rev.getAuthorUsername()%></a>
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
