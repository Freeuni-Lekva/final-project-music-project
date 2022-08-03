<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Album Page</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body class="long_background" style="text-align:center">

<c:set var="album" value="${albumService.getAlbum(currAlbumId.hashed())}"></c:set>
<p class="big_text">${album.artistName()}</p>
<p class="text">${album.albumName()}</p>
<p class="space"></p>
 <img src="${imagePrefix}${album.coverImageBase64()}"  alt="cover image" width="450px" height="450px" />

 <ul> <c:forEach var="song" items="${album.songs()}">
     <p class="space"></p>
    <li>
        <p class="text">${song.name()}</p>
        <audio src="${audioPrefix}<c:out value="${song.songBase64()}"/>" controls></audio>
    </li>

 </c:forEach>
 </ul>
</body>
</html>
