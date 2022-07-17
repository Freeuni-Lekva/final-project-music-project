<%@ page import="org.freeuni.musicforum.service.ServiceFactory" %>
<%@ page import="org.freeuni.musicforum.model.Review" %>
<%@ page import="java.util.List" %>
<%@ page import="org.freeuni.musicforum.model.PublicUserData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body class = "background">
<div class = "profile_top">

    <% PublicUserData user = (PublicUserData) request.getAttribute("user"); %>

    <div class = "profile_image_rec">
        <h2 class = "text"><%=user.firstName()%> <%=user.lastName()%></h2>
        <img src="images/username_profile" alt="Upload your profile picture!" class="profile_image">
    </div>

    <div class = "profile_info_rec">
        <p class = "huge_space"></p>
        <p class = "text"><%=user.username()%></p>
        <p class = "space"></p>
        <p class = "text"><%=user.badge().name()%></p>
        <p class = "space"></p>
        <p class = "text">Prestige: <%=ServiceFactory.getUserService().getUserPrestige(user.username())%></p>
    </div>

</div>

<div class = "profile_bottom">
    <div class = "profile_filter">
        <div class = "profile_full_button">
            <form action="/profile" method="get">
                <input type="hidden" name="username" value="<%=user.username()%>" />
                <input type="submit" value="Albums" class="profile_part_button" class="text">
            </form>
            <input type="button" value="Reviews" class="profile_part_button" class="text" >
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
                    <%-- get photo from the album --%>
                    <p class="text"><%=rev.getAlbumId()%></p>
                    <img src ="/images/BLAH.png" alt="album image goes here">
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
