<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body class="background">

<img src="image.jpg" alt="Company Logo And Design Should Be Here"
     class="login_image">

<div class="outer_rectangle">

    <div class="page_label_box">
        <h1 class="page_label">Music Forum</h1>
    </div>

    <p class="space"></p>
    <h3 class="big_text">Join the community!</h3>
    <p class="big_space"></p>

    <form action="/register" method="post">
        <label for="firstName" class="text">First Name:</label>
        <input type="text" id="firstName" name="firstName" class="textbox">
        <p class="space"></p>
        <label for="lastName" class="text">Last Name:</label>
        <input type="text" id="lastName" name="lastName" class="textbox">
        <p class="space"></p>
        <label for="birthDate" class="text">Date of Birth:</label>
        <input type="date" id="birthDate" name="birthDate">
        <p class="space"></p>
        <label for="gender" class="text">Gender:</label>
        <select name="gender" id="gender">
            <option>OTHER</option>
            <option>MAN</option>
            <option>WOMAN</option>
        </select>
        <p class="space"></p>
        <label for="username" class="text">Username:</label>
        <input type="text" id="username" name="username" class="textbox">
        <p class="space"></p>
        <label for="password" class="text">Password:</label>
        <input type="password" id="password" name="password" class="textbox">
        <p class="space"></p>
        <input type="submit" value="Sign Up" class="text" class="button">
    </form>

    <p class="space"></p>

    <p class="text">Already have an account?</p>
    <a href="/" class="text">Login</a>

</div>

</body>
</html>
