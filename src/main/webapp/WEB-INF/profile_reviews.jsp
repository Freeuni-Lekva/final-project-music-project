<%@ page import="org.freeuni.musicforum.service.ServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="org.freeuni.musicforum.exception.NonexistentAlbumException" %>
<%@ page import="org.freeuni.musicforum.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/main_styles.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/profileStyles.css" />
</head>
<body class = "background">
    <%request.setAttribute("filepath", "WEB-INF/profile_reviews.jsp");%>
    <%@include file="profile_top.jsp"%>
    <%boolean currentUsersPage = currUser!=null&&currUser.username().equals(user.username());
        String fullClassName = "profile_full_two_buttons";
        String partClassName = "profile_part_two_buttons";
    %>
    <div class = "profile_bottom">
        <%if(currentUsersPage) {
               fullClassName = "profile_full_three_buttons";
               partClassName = "profile_part_three_buttons";
        }%>
        <div class = "profile_filter" >
            <div class = "<%=fullClassName%>" >
                <form action="/profile" method="get">
                    <input type="hidden" name="username" value="<%=user.username()%>" />
                    <input type="submit" value="Albums" class="<%=partClassName%>" class="text">
                </form>
                <input type="button" value="Reviews" class="<%=partClassName%>" class="text">
                <% if(currentUsersPage){ %>
                <form action="/profile_friends" method="get">
                    <input type="submit" value="Friends" class="<%=partClassName%>" class="text">
                </form>
                <%}%>

            </div>
        </div>
        <div class = "profile_scroll">
            <% List<Review> reviews = ServiceFactory.getReviewService().getAllReviewsBy(user.username()); %>
            <% for (Review rev : reviews) { %>
                <%-- Later add href to the album page here and ask for actual album name instead of id --%>
                <div class = "profile_scroll_member_reviews">
                    <div class = "profile_scroll_member_photobox">
                        <% for (int i = 0; i < rev.getStarCount(); i++) { %>
                            <img src="/images/star_sel.png" class="profile_scroll_member_stars">
                        <% }
                            for (int i = rev.getStarCount(); i < 5; i++) { %>
                            <img src="/images/star_unsel.png" class="profile_scroll_member_stars">
                        <% }
                         Album album = null;
                            try {
                                album = ServiceFactory.getAlbumService().getAlbum(rev.getAlbumId());
                            } catch (NonexistentAlbumException ex) {
                            }
                        if (album != null) {%>
                            <p class="text"><a href="/album?albumId=<%=album.id()%>">
                                <%=album.albumName()%>
                            </a></p>
                            <img src ="${imagePrefix}<%=album.coverImageBase64()%>" width="100px" height="100px">
                        <%}%>
                    </div>
                    <div class = "profile_scroll_member_reviews_infobox">
                        <div class="review_album_textbox">
                        <p class="space"></p>
                        <p class="small_text"><%=rev.getText()%></p>
                        <p class="space"></p>
                        </div>
                        <% VoteType vote = ServiceFactory.getVotingDataService().
                                getUserVoteForReview(currUser.username(), rev.getId()); %>
                        <p class="text">
                        <div  class="review_album_vote_panel">
                        <form action="/upvote" method="post" class="review_album_vote_form">
                            <input type="hidden" name="votingUser" value="<%=currUser.username()%>">
                            <input type="hidden" name="reviewId" value="<%=rev.getId()%>">
                            <input type="hidden" name="page" value="<%="profilereviews"%>">
                            <input type="hidden" name="username" value="<%=user.username()%>">
                            <% if (vote.equals(VoteType.UPVOTE)) { %>
                            <input type="image" src="/images/up_sel.png" class="review_album_vote">
                            <% } else { %>
                            <input type="image" src="/images/up_unsel.png" class="review_album_vote">
                            <% } %>
                        </form>

                        <%=rev.getPrestige()%>

                        <form action="/downvote" method="post" class="review_album_vote_form">
                            <input type="hidden" name="votingUser" value="<%=currUser.username()%>">
                            <input type="hidden" name="reviewId" value="<%=rev.getId()%>">
                            <input type="hidden" name="page" value="<%="profilereviews"%>">
                            <input type="hidden" name="username" value="<%=user.username()%>">
                            <% if (vote.equals(VoteType.DOWNVOTE)) { %>
                            <input type="image" src="/images/down_sel.png" class="review_album_vote">
                            <% } else { %>
                            <input type="image" src="/images/down_unsel.png" class="review_album_vote">
                            <% } %>
                        </form>
                    </div>
                        </p>
                        <% if (currUser != null &&
                        (currUser.badge().isAdministrator() || currUser.username().equals(user.username()))) { %>
                        <p class = "space"></p>
                        <form action = "/deleteReview" method="post">
                            <input type="hidden" name="deleteReviewId" value="<%=rev.getId()%>">
                            <input type="hidden" name="albumId" value="<%album.id();%>">
                            <input type="submit" id="deleteReviewButton" value="Delete Review" class="text" class="button">
                        </form>
                        <% } %>
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>
