<%@ page import="org.freeuni.musicforum.service.ServiceFactory" %>
<%@ page import="org.freeuni.musicforum.model.PublicUserData" %>
<%@ page import="org.freeuni.musicforum.model.FriendshipStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body class = "background">
    <% PublicUserData user = (PublicUserData) request.getAttribute("user"); %>
    <% String buttonText = (String) request.getAttribute("buttonText"); %>

    <div class = "profile_top">

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

            <%  PublicUserData currUser = (PublicUserData) request.getServletContext().getAttribute("currentUser");
                if(currUser != null && !user.username().equals(currUser.username())){%>
            <p class = "space"></p>
            <% FriendshipStatus fs = ServiceFactory.getUserService().getFriendshipStatus(currUser.username(), user.username());
                String text = "Add Friend";
                if(fs!=null){text = fs.toString();}
                if(buttonText!=null) text = buttonText;
            %>
            <form action = "/addFriend" method="post">
                <input type="hidden" name="filepath" value="/WEB-INF/profile.jsp">
                <input type="hidden" name="username" value=<%=user.username()%>>
                <input type="submit" value="<%=text%>" class="text" class = "button">
            </form>
            <%}%>

    </div>
 </div>


    <div class = "profile_bottom">
        <div class = "profile_filter">
            <div class = "profile_full_button">
                <input type="button" value="Albums" class="profile_part_button" class="text">
                <form action="/profile_reviews" method="get">
                    <input type="hidden" name="username" value="<%=user.username()%>" />
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
