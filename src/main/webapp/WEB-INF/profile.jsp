<%@ page import="org.freeuni.musicforum.model.Album" %>
<%@ page import="java.util.List" %>
<%@ page import="org.freeuni.musicforum.model.Song" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body class = "background">
    <%request.setAttribute("filepath", "WEB-INF/profile.jsp");%>
    <%@include file="profile_top.jsp"%>
    <% boolean currentUsersPage = currUser!=null&&currUser.username().equals(user.username());
       String fullClassName = "profile_full_two_buttons";
       String partClassName = "profile_part_two_buttons";
    %>
    <div class = "profile_bottom">
        <%if(currentUsersPage) {
                fullClassName = "profile_full_three_buttons";
                partClassName = "profile_part_three_buttons";
        }%>
        <div class = "profile_filter">
            <div class = "<%=fullClassName%>">
                <input type="button" value="Albums" class="<%=partClassName%>" class="text">
                <form action="/profile_reviews" method="get">
                    <input type="hidden" name="username" value="<%=user.username()%>" />
                    <input type="submit" value="Reviews" class="<%=partClassName%>" class="text" >
                </form>
                <% if(currentUsersPage){ %>
                <form action="/profile_friends" method="get">
                    <input type="submit" value="Friends" class="<%=partClassName%>" class="text">
                </form>
                <%}%>
            </div>
        </div>
        <div class = "profile_scroll">
            <% List<Album> albums = ServiceFactory.getAlbumService().getAllAlbumsUploadedBy(user.username()); %>
            <% for (Album alb : albums) { %>
            <%-- Later add href to the album page here --%>
            <div class = "scroll_member">
                <div class = "scroll_member_photobox">
                    <p class="text"><%=alb.artistName()%></p>
                    <p class="small_text"><%=alb.albumName()%></p>
                    <img src="/images/album-covers/<%=alb.fileName()%>" width="100px" height="100px">
                </div>
                <div class = "scroll_member_infobox">
                    <% int stars = ServiceFactory.getAlbumService().getAverageStarFor(alb.id()); %>
                    <% for (int i = 0; i < stars; i++) { %>
                        <img src="/images/star_sel.png" class="vote_box">
                    <% } %>
                    <% for (int i = stars; i < 5; i++) { %>
                        <img src="/images/star_unsel.png" class="vote_box">
                    <% } %>
                    <% List<Song> songs = alb.songs(); %>
                    <% for (Song song : songs) { %>
                        <p class="text"><%=song.name()%></p>
                        <p class="space"></p>
                    <% } %>
                </div>
            </div>
            <% } %>
        </div>
    </div>
</body>
</html>
