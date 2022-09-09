<%@ page import="org.freeuni.musicforum.service.ServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="org.freeuni.musicforum.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Album Page</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/main_styles.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/previewAlbumStyles.css" />
</head>

<body class="long_background" style="text-align:center">
<%@include file="upper_strip.jsp"%>

<p class="smaller_space"></p>

<c:set var="album" value="${album}" ></c:set>
<c:set var="uploader" value="${album.username()}"></c:set>
<c:set var="albumId" value="${album.id()}"></c:set>

<% PublicUserData currUser = (PublicUserData) request.getSession().getAttribute("currentUser"); %>
<% String uploader = ((Album) request.getAttribute("album")).username(); %>
<% String albumId = ((Album) request.getAttribute("album")).id(); %>

<div class="top_album">
    <div class="left_album">
        <p class="text">Uploaded by:
            <%if(ServiceFactory.getUserService().isActive(uploader)) {%>
                <a href="/profile?username=${uploader}"> ${uploader} </a>
            <%}else{%>
                ${uploader}
            <%}%>
        </p>
        <p class="space"></p>
        <p class="big_text">Artist: ${album.artistName()}</p>
        <p class="big_text">Album: ${album.albumName()}</p>

        <c:set var="stars" value="${albumService.getAverageStarFor(albumId)}"></c:set>
        <c:forEach begin="1" end="${stars}">
        <img src="/images/star_sel.png" style="width: 30px; height: 30px">
        </c:forEach>
        <c:forEach begin="${stars + 1}" end="5">
        <img src="/images/star_unsel.png" style="width: 30px; height: 30px">
        </c:forEach>
        <p class="space"></p>
        <img src="${imagePrefix}${album.coverImageBase64()}"  alt="cover image" width="300px" height="300px" />
    </div>

    <div class="right_album">
        <c:if test="${currentUser != null && currentUser.badge().isAdministrator()}">
        <form action = "/deleteAlbum" method="post">
            <input type="hidden" name="deleteAlbumId" value="${album.id()}">
            <input type="submit" id="deleteButton" value="Delete Album" class="text" class="button">
        </form>
        <p class = "space"></p>
        </c:if>
        <c:forEach var="song" items="${album.songs()}">
            <p class="space"></p>
            <p class="text">${song.name()}</p>
            <audio src="${audioPrefix}<c:out value="${song.songBase64()}"/>" controls></audio>
            <br/>
        </c:forEach>
    </div>
</div>

<div class="bottom_album">
    <p class="text">Reviews</p>
    <c:if test="${currentUser != null  && !reviewService.userHasReviewForAlbum(currentUser.username(), albumId)}">
    <div class="review_album_wrapper">
        <form action="/addReview" method="post">
            <input type="hidden" name="albumId" value="${album.id()}">
            <input type="hidden" name="authorUsername" value="${currentUser.username()}">
            <label for="stars">Rating (Between 0 and 5):</label>
            <input type="number" id="stars" name="stars" min="0" max="5" required>
            <p class="space"></p>
            <input type="text" id="reviewText" name="reviewText" class="huge_textbox">
            <p class="space"></p>
            <input type="submit" value="Add Review" class="text" class="button">
            <p class="space"></p>
        </form>
    </div>
    </c:if>
    <% List<Review> reviews = ServiceFactory.getReviewService().getAllReviewsFor(albumId);
     for (Review rev : reviews) { %>
    <div class="review_album_wrapper">
        <p class="text">
            <% String author = rev.getAuthorUsername();
            if(ServiceFactory.getUserService().isActive(author)) {%>
            <a href="/profile?username=<%=author%>"><%=author%></a>
            <%}else{%>
            <%=author%>
            <%}%>
        </p>
        <p class="space"></p>
        <div class="review_album_textbox">
        <p><%=rev.getText()%></p> </div>
        <p class="space"></p>
        <% VoteType vote = ServiceFactory.getVotingDataService().
                getUserVoteForReview(currUser.username(), rev.getId()); %>
        <p class="text">

        <div  class="review_album_vote_panel">
        <form action="/upvote" method="post" class="review_album_vote_form">
            <input type="hidden" name="votingUser" value="${currentUser.username()}">
            <input type="hidden" name="reviewId" value="<%=rev.getId()%>">
            <input type="hidden" name="page" value="album">
            <input type="hidden" name="albumId" value="${albumId}">
            <% if (vote.equals(VoteType.UPVOTE)) { %>
                <input type="image" src="/images/up_sel.png" class="review_album_vote">
            <% } else { %>
                <input type="image" src="/images/up_unsel.png" class="review_album_vote">
            <% } %>
        </form>
        <%=rev.getPrestige()%>
        <form action="/downvote" method="post" class="review_album_vote_form">
            <input type="hidden" name="votingUser" value="${currentUser.username()}">
            <input type="hidden" name="reviewId" value="<%=rev.getId()%>">
            <input type="hidden" name="page" value="album">
            <input type="hidden" name="albumId" value="${albumId}">
            <% if (vote.equals(VoteType.DOWNVOTE)) { %>
                <input type="image" src="/images/down_sel.png" class="review_album_vote">
            <% } else { %>
                <input type="image" src="/images/down_unsel.png" class="review_album_vote">
            <% } %>
        </form>
        </div>
        </p>
        <% if (currUser != null && (currUser.badge().isAdministrator() || author.equals(currUser.username()))) { %>
        <p class = "space"></p>
        <form action = "/deleteReview" method="post">
            <input type="hidden" name="deleteReviewId" value="<%=rev.getId()%>">
            <input type="hidden" name="albumId" value="${albumId}">
            <input type="submit" id="deleteReviewButton" value="Delete Review" class="text" class="button">
        </form>
        <% } %>
    </div>
    <% } %>
</div>

</body>
</html>
