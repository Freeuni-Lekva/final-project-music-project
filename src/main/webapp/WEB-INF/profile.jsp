<%@ page import="org.freeuni.musicforum.model.User" %>
<%@ page import="org.freeuni.musicforum.service.ServiceFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body class = "background">
    <div class = "profile_top">

        <% User user = (User) request.getAttribute("user"); %>

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
            <p class = "text">Prestige: <%=ServiceFactory.getUserService().getUserPrestige(user)%></p>
        </div>

    </div>

    <div class = "profile_bottom">
        <div class = "profile_filter">
            <div class = "profile_full_button">
                <input type="button" value="Albums" class="profile_part_button" class="text">
                <form action="/profile_reviews" method="get">
                    <input type="submit" value="Reviews" class="profile_part_button" class="text" >
                </form>
            </div>
        </div>
        <div class = "profile_scroll">
            <%-- add a list of albums added by this user --%>
        </div>
    </div>
</body>
</html>
