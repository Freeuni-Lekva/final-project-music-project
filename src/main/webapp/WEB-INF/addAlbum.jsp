<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add New Album</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body class="background">


<div class="outer_rectangle">

<%--    <div class="login_image">--%>
<%--    </div>--%>

    <div class="page_label_box">
        <h1 style="text-align: center" class="page_label"> Music Forum </h1>
    </div>

    <p class="space"></p>
    <h2 style="text-align: center" class="big_text"> Add New Album </h2>
    <p class="big_space"></p>

    <form action="/addAlbum" method="post">
        <label for="albumName" class="text"> Name Of New Album </label>
        <br/>
        <input type="text" id="albumName" name="albumName" class="textbox">
        <p class="space"></p>

        <label for="artistName" class="text"> Name Of Artist </label>
        <br/>
        <input type="text" id="artistName" name="artistName" class="textbox">
        <p class="space"></p>

        <label for="coverImage" class="text"> Choose Album Cover</label>
        <p class="space"></p>
        <input type="file" id="coverImage" accept="image/*" class="small_text" class="button">
        <p class="space"></p>

        <input type="submit" value="Add New Album" class="text" class="button">

    </form>
</div>

</body>
</html>
