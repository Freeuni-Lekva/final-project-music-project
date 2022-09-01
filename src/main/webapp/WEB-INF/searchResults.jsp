<%@ page import="org.freeuni.musicforum.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="org.freeuni.musicforum.model.Album" %>
<%@ page import="org.freeuni.musicforum.model.Review" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Feed</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body>
    <%@include file="searchbar.jsp"%>
    <% List<User> users = (List<User>) request.getAttribute("filteredUsers");
       if(users!=null){ %>

    <%}%>

    <% List<Album> albums = (List<Album>) request.getAttribute("filteredAlbums");
        if(albums!=null){ %>

    <%}%>

    <% List<Review> reviews = (List<Review>) request.getAttribute("filteredReviews");
        if(reviews!=null){ %>

    <%}%>

</body>
</html>
