<%@ page import="org.freeuni.musicforum.service.ServiceFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add Songs To The Album</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/main_styles.css" />
</head>
<body class="long_background">

    <div class="centered_rectangle">

        <div class="album_page_label_box">
            <h1 class="page_label"> Music Forum </h1>
        </div>


        <% request.setAttribute("albumService", ServiceFactory.getAlbumService()); %>
        <c:set var="album" value="${albumService.getAlbum(currAlbumId)}"></c:set>

        <p class="space"></p>
        <h2 style="text-align: center" class="big_text">
            Add Songs To "${album.albumName()}"
                      By "${album.artistName()}"
        </h2>
        <p class="big_space"></p>

        <form action="/addSongs" method="post" enctype="multipart/form-data">


            <input type="hidden" name="songAmount" value="${songAmount}"/>

            <label class="text"> Choose Songs For Album </label>
            <p class="space"></p>
            <ol> <c:forEach begin="1" end="${songAmount}" var="num">
                <li>
                    <label for="name" class="text"> Name of The Song </label>
                    <input minlength="1" type="text" id="name" name="name${num}" class="textbox" required/>
                    <p class="space"></p>
                </li>
            </c:forEach>
            </ol>
            <input type="submit" value="Add Songs" class="text" class="button"/>
            <p class="big_space"></p>
        </form>
    </div>
</body>
</html>
