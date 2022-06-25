<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log In</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body class="background">

    <img src="image.jpg" alt="Company Logo And Design Should Be Here"
         class="login_image">

    <div class="outer_rectangle">

        <div class="page_label_box">
            <h1 class="page_label">Music Forum</h1>
        </div>
        <h3 class="big_text">Express your wildest musical opinions here!</h3><br>

        <form action="/" method="post">
            <label for="username" class="text">Username:</label>
            <input type="text" id="username" name="username">
            <br><br>
            <label for="password" class="text">Password: </label>
            <input type="password" id="password" name="password">
            <br><br>
            <input type="submit" value="Login" class="text">
        </form>

        <br>
        <p class="text">Don't have an account?</p>
        <a href="/register" class="text">Sign Up</a>

    </div>

</body>
</html>
