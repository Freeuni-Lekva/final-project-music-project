<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body class = "background">
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
            <%-- add a list of albums added by this user --%>
        </div>
    </div>
</body>
</html>
