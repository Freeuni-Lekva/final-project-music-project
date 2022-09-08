<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log In</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/main_styles.css" />
</head>
<body class="background">

    <div class="login_image">
        <p class="huge_space"></p>
        <img src="/images/logo_big.png" alt="logo_pic">
    </div>

    <div class="outer_rectangle">

        <div class="page_label_box">
            <h1 class="page_label">Music Forum</h1>
        </div>

        <p class="space"></p>
        <h3 class="big_text">Express your wildest musical opinions here!</h3>
        <p class="big_space"></p>

        <form action="/" method="post">
            <label for="username" class="text">Username:</label>
            <input type="text" id="username" name="username" class="textbox">
            <p class="space"></p>
            <label for="password" class="text">Password: </label>
            <input type="password" id="password" name="password" class="textbox">
            <p class="space"></p>
            <input type="submit" value="Login" class="text" class="button">
        </form>

        <% if (request.getAttribute("incorrectLogin") != null) { %>
        <p class="space"></p>
            <p class="small_text">Your username or password was incorrect, please try again.</p>
        <% } %>

        <p class="space"></p>
        <p class="text">Don't have an account?</p>
        <a href="/register" class="text">Sign Up</a>

    </div>

</body>
</html>
