<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
 <img src="${imagePrefix}${albumService.getAlbum(currId).coverImageBase64()}"  alt="cover image" width="450px" height="450px" />
 <ul> <c:forEach var="song" items="${albumService.getAlbum(currId).songs()}">

    <li> <audio src="${audioPrefix}<c:out value="${song.songBase64()}"/>" controls></audio> </li>
 </c:forEach>
 </ul>
</body>
</html>
