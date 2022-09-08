<%@ page import="org.freeuni.musicforum.model.PublicUserData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/main_styles.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/upperStripStyles.css" />
</head>
<body>

<div class="upper_strip">
    <div class="frame_space_left"></div>
    <div class="frame_space_right"></div>
    <%PublicUserData pd = (PublicUserData) request.getSession().getAttribute("currentUser"); %>
    <div class = "image_wrapper">
        <a href="/feed"> <img src="/images/logo_small.png"  width="45px" height="45px" alt="logo_pic"> </a>
    </div>
    <div class = "website_title">
        <p><a href="/feed" class="website_title_text">Music Forum</a></p>
    </div>
    <div class="links_rec">
        <%if(pd!=null){%>
        <div class = "profile_link">
            <p class="link_text"> <a href="/profile?username=<%=pd.username()%>">Profile</a></p>
        </div>
        <div class = "log_out_link">
            <p class="link_text"> <a href="/loggedOut">Log Out</a></p>
        </div>
        <%}%>
    </div>
</div>

</body>
</html>
