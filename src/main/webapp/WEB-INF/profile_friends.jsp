<%@ page import="org.freeuni.musicforum.model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body class = "background">
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

        <div class = "profile_scroll">
            <%List<PublicUserData> users = ServiceFactory.getUserService().getUsersFriends(currUser.username());
            for(PublicUserData friend : users){%>

            <%--later display user picture and add links--%>
            <p class="small_text"><%=friend.username()%></p>
            <p class="space"></p>

            <%}%>

        </div>

    </div>

</body>
</html>
