<%@ page import="org.freeuni.musicforum.model.PublicUserData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>


</head>
<body>

<div class="upper_strip">
    <div class="frame_space_left"></div>
    <div class="frame_space_right"></div>
    <%PublicUserData pd = (PublicUserData) request.getSession().getAttribute("currentUser"); %>
    <div class = "image_wrapper">
        <img src="/images/logo_small.png" alt="logo_pic">
    </div>
    <div class = "website_title">
        <p class="website_title_text">Music Forum</p>
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

<p class="space"></p>

<div class = "search_bar">
    <div class="frame_space_left"></div>
    <div class="frame_space_right"></div>
    <form action = "/searchResults" method = "post">
        <select id="scope" name="scope" class="search_button">
            <option>Global</option>
            <option>Friends</option>
        </select>
        <select id="time" name="time" class="search_button">
            <option>All Time</option>
            <option>Last Week</option>
            <option>Last Month</option>
        </select>
        <input type="text" id="search-bar" name="search-bar" class="search_field">
        <select id="searchBy" name="searchBy" class="search_button">
            <option>Albums</option>
            <option>Users</option>
            <option>Reviews</option>
        </select>
        <input type="submit" value="search"  class="search_button">
    </form>
</div>

<p class="space"></p>

</body>
</html>
