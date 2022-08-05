<%@ page import="org.freeuni.musicforum.service.ServiceFactory" %>
<%@ page import="org.freeuni.musicforum.model.Review" %>
<%@ page import="java.util.List" %>
<%@ page import="org.freeuni.musicforum.model.Album" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
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
                <div class = "scroll_member">
                    <div class = "scroll_member_photobox">
                        <% for (int i = 0; i < rev.getStarCount(); i++) { %>
                            <img src="/images/star_sel.png" class="vote_box">
                        <% } %>
                        <% for (int i = rev.getStarCount(); i < 5; i++) { %>
                            <img src="/images/star_unsel.png" class="vote_box">
                        <% } %>
                        <% Album album = null;
                        try {
                            album = ServiceFactory.getAlbumService().getAlbum(rev.getAlbumId());
                        } catch (Exception e) {
                        } %>
                        <% if (album != null) { %>
                            <p class="text"><a href="/album?albumId=<%=album.id()%>">
                                <%=album.albumName()%>
                            </a></p>
                            <img src ="${imagePrefix}<%=album.coverImageBase64()%>}" width="100px" height="100px">
                        <% } %>
                    </div>
                    <div class = "scroll_member_infobox">
                        <p class="space"></p>
                        <p class="small_text"><%=rev.getText()%></p>
                        <p class="space"></p>
                        <%-- get upvote/downvote info here --%>
                        <%-- this is default for when upvote and
                        downvote are unselected, forms must be
                        added as well. --%>
                        <p class="text">
                            <img src="/images/up_sel.png" class="vote_box">
                            <%=rev.getPrestige()%>
                        </p>
                    </div>
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>
