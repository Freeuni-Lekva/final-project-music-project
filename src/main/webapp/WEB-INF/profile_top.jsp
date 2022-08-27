<%@ page import="org.freeuni.musicforum.model.PublicUserData" %>
<%@ page import="org.freeuni.musicforum.service.ServiceFactory" %>
<%@ page import="org.freeuni.musicforum.model.FriendshipStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body>
<% PublicUserData user = (PublicUserData) request.getAttribute("user");
    PublicUserData currUser = (PublicUserData) request.getSession().getAttribute("currentUser");
    String filepath = (String) request.getAttribute("filepath");
    if(filepath==null) filepath = "WEB-INF/profile.jsp";
%>

<div class = "profile_top">

    <div class = "profile_image_rec">
        <h2 class = "text"><%=user.firstName()%> <%=user.lastName()%></h2>
        <img src = "${imagePrefix}<%=user.profileImageBase64()%>" width="200px" height="200px">
        <p class="space"></p>
        <%if(currUser != null && user.username().equals(currUser.username())){%>
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
        <%}%>
    </div>

    <div class = "profile_info_rec">
        <p class = "huge_space"></p>
        <p class = "text"><%=user.username()%></p>
        <p class = "space"></p>
        <p class = "text"><%=user.badge().toString()%></p>
        <p class = "space"></p>
        <p class = "text">Prestige: <%=ServiceFactory.getUserService().getUserPrestige(user.username())%></p>

        <%if(currUser != null && !user.username().equals(currUser.username())){%>
        <p class = "space"></p>
        <%FriendshipStatus fs = ServiceFactory.getUserService().getFriendshipStatus(currUser.username(), user.username());
            String buttonText = "Add Friend";
            if(fs!=null){buttonText = fs.getButtonText();}
        %>
        <form action = "/addFriend" method="post">
            <input type="hidden" name="filepath" value="<%=filepath%>">
            <input type="hidden" name="username" value=<%=user.username()%>>
            <%if(fs!=null&&fs.equals(FriendshipStatus.FRIENDS)){%>
            <label for="friendButton" class="green_text">Friends</label>
            <p class="space"></p>
            <%}%>
            <input type="submit" id="friendButton" value="<%=buttonText%>" class="text" class="button">
        </form>
        <% if (currUser.badge().isAdministrator()) { %>
        <p class = "space"></p>
        <form action = "/banUser" method="post">
            <input type="hidden" name="username" value="<%=user.username()%>">
            <input type="submit" id="banButton" value="Ban User" class="text" class="button">
        </form>
        <%}
        }%>
    </div>
</div>

</body>
</html>
