<%@ page import="org.freeuni.musicforum.model.PublicUserData" %>
<%@ page import="org.freeuni.musicforum.service.ServiceFactory" %>
<%@ page import="org.freeuni.musicforum.model.FriendshipStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/main_styles.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/profileStyles.css" />
</head>
<body>
<% PublicUserData user = (PublicUserData) request.getAttribute("user");
    PublicUserData currUser = (PublicUserData) request.getSession().getAttribute("currentUser");
    String filepath = (String) request.getAttribute("filepath");
    if(filepath==null) filepath = "WEB-INF/profile.jsp";
%>
<%@include file="upper_strip.jsp"%>
<p class="smaller_space"></p>

<div class = "profile_top">

    <div class = "profile_image_rec">
        <h2 class = "text">${user.firstName()} ${user.lastName()} </h2>
        <img src = "${imagePrefix}${user.profileImageBase64()}" width="200px" height="200px">
        <p class="space"></p>
        <c:if test="${currentUser != null && user.username().equals(currentUser.username())}">
        <form action="/uploadProfileImage" method="post" enctype="multipart/form-data">
            <input type="hidden" name="filepath" value="<%=filepath%>">
            <input type="file" id="profileImage" name="profileImage" accept="image/*" class="browse_files">
            <p class="space"></p>
            <%if(user.profileImageBase64()==null){%>
            <input type="submit" value="Upload Picture" class="upload_picture_button">
            <%}else{%>
            <input type="submit" value="Change Picture" class="upload_picture_button">
            <%}%>
        </form>
        </c:if>
    </div>

    <div class = "profile_info_rec">
        <p class = "huge_space"></p>
        <p class = "text">${user.username()}</p>
        <p class = "space"></p>
        <p class = "text">${user.badge().toString()}</p>
        <p class = "space"></p>
        <p class = "text">Prestige: ${userService.getUserPrestige(user.username())}</p>

        <c:if test="${currentUser != null && !user.username().equals(currentUser.username())}">
        <p class = "space"></p>
        <%FriendshipStatus fs = ServiceFactory.getUserService().getFriendshipStatus(currUser.username(), user.username());
            String buttonText = "Add Friend";
            if(fs!=null){buttonText = fs.getButtonText();}
        %>
        <form action = "/addFriend" method="post">
            <input type="hidden" name="filepath" value="<%=filepath%>">
            <input type="hidden" name="username" value=${user.username()}>
            <%if(fs!=null&&fs.equals(FriendshipStatus.FRIENDS)){%>
            <label for="friendButton" class="green_text">Friends</label>
            <p class="space"></p>
            <%}%>
            <input type="submit" id="friendButton" value="<%=buttonText%>" class="text" class="button">
        </form>
        <c:if test="${currentUser.badge().isAdministrator() && !user.badge().isAdministrator()}">
        <p class = "space"></p>
        <form action = "/banUser" method="post">
            <input type="hidden" name="username" value=${user.username()}>
            <input type="submit" id="banButton" value="Ban User" class="text" class="button">
        </form>
        </c:if>
        </c:if>
    </div>
</div>

</body>
</html>
