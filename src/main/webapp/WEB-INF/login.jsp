<%--
  Created by IntelliJ IDEA.
  User: itodik
  Date: 22.06.22
  Time: 22:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log In</title>
</head>
<body style="background-color: #e4f2ff">

    <img src="image.jpg" alt="Company Logo And Design Should Be Here"
         style="float: left; margin-right: 10px; width: 300px; height: 500px">

    <div style="width:500px;height:500px;
    background-color:#ffffff;float:left;text-align:center">

        <div style="width:500px;height:50px;float:top";>
            <h1 style="color:#81d8d0">Music Forum</h1>
        </div>
        <h3>Express your wildest musical opinions here!</h3><br>
        <form action="/" method="get">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username"><br>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password"><br>
            <input type="submit" value="Login">
        </form>
        <hr/>
        <p>Don't have an account?</p>
        <a href="/register">Sign Up</a>

    </div>

</body>
</html>
