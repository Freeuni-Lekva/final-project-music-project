<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log In</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body style="background-color: #e4f2ff">

    <img src="image.jpg" alt="Company Logo And Design Should Be Here"
         class="login_image">

    <div class="outer_rectangle">

        <div class="page_label_box">
            <h1 class="page_label">Music Forum</h1>
        </div>
        <h3>Express your wildest musical opinions here!</h3><br>

        <form action="/" method="post">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username">
            <br>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password">
            <br>
            <input type="submit" value="Login">
        </form>

        <hr/>
        <p>Don't have an account?</p>
        <a href="/register">Sign Up</a>

    </div>

</body>
</html>
