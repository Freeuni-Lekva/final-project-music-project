<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
body class = "background">
<div class = "profile_top">

    <div class = "profile_image_rec">
        <h2 class = "text">First Name, Last Name</h2>
        <img src="images/username_profile" alt="Upload your profile picture!" class="profile_image">
    </div>

    <div class = "profile_info_rec">
        <p class = "huge_space"></p>
        <p class = "text">Username</p>
        <p class = "space"></p>
        <p class = "text">Birthday</p>
        <p class = "space"></p>
        <p class = "text">Badge</p>
        <p class = "space"></p>
        <p class = "text">Prestige: number</p>
    </div>

</div>

<div class = "profile_bottom">
    <div class = "profile_filter">
        <div class = "profile_full_button">
            <form action="/profile" method="post">
                <input type="button" value="Albums" class="profile_part_button" class="text">
            </form>
            <input type="button" value="Reviews" class="profile_part_button" class="text" >
        </div>
    </div>
    <div class = "profile_scroll">
        <p class = "big_text">Test</p>
    </div>
</div>
</body>
</html>
