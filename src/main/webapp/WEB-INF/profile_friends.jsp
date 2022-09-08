<%@ page import="org.freeuni.musicforum.model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/main_styles.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/profileStyles.css" />
</head>
<body class = "background">
    <%request.setAttribute("filepath", "WEB-INF/profile_friends.jsp");%>
    <%@include file="profile_top.jsp"%>
    <div class = "profile_bottom">
        <div class = "profile_filter" >
            <div class = "profile_full_three_buttons" >
                <form action="/profile" method="get">
                    <input type="hidden" name="username" value="<%=user.username()%>" />
                    <input type="submit" value="Albums" class="profile_part_three_buttons" class="text">
                </form>
                <form action="/profile_reviews" method="get">
                    <input type="hidden" name="username" value="<%=user.username()%>" />
                    <input type="submit" value="Reviews" class="profile_part_three_buttons" class="text" >
                </form>
                <input type="button" value="Friends" class="profile_part_three_buttons" class="text">
            </div>
        </div>

        <div class = "friends_scroll">
            <%List<PublicUserData> usersFriends = ServiceFactory.getUserService().getUsersFriends(currUser.username());
              List<PublicUserData> usersRequests = ServiceFactory.getUserService().getUsersFriendRequests(currUser.username()); %>

            <div class = "friends_scroll_column">
                <p class = "text" > Friend List: </p>
                <p class = "space"></p>
                <%for(PublicUserData friendData : usersFriends){%>
                <div class = "friends_scroll_member">
                    <img src = "${imagePrefix}<%=friendData.profileImageBase64()%>" class = "friends_scroll_member_photo">
                    <div class = "friends_scroll_member_info">
                        <p class="profile_username_text"> <a href="/profile?username=<%=friendData.username()%>">
                            <%=friendData.username()%>
                        </a></p>
                    </div>
                </div>
                <%}%>
            </div>

            <div class = "friends_scroll_column">
                <p class = "text">Friend Requests:</p>
                <p class = "space"></p>
                <%for(PublicUserData requestData : usersRequests){%>
                <div class = "friends_scroll_member">
                    <img src = "${imagePrefix}<%=requestData.profileImageBase64()%>" class = "friends_scroll_member_photo">
                    <div class = "friends_scroll_member_info">
                        <p class="profile_username_text"> <a href="/profile?username=<%=requestData.username()%>">
                            <%=requestData.username()%>
                        </a></p>
                        <p class="bigger_space"></p>
                        <form action = "/answerFriendRequest" method="post">
                            <input type="hidden" name="filepath" value="<%=request.getAttribute("filepath")%>">
                            <input type="hidden" name="username" value=<%=requestData.username()%>>
                            <input type="submit" name="action" value="Accept Request" class="green_text" class="button">
                            <input type="submit" name="action" value="Delete Request" class="red_text" class="button">
                        </form>
                    </div>
                </div>
                <p class="space"></p>
                <%}%>
            </div>
        </div>
    </div>
</body>
</html>
