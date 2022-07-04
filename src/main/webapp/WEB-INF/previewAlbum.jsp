<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
 <img src="${base64SrcPrefix}${albumDAO.getById(currAlbum).coverImageBase64()}"  alt="cover image" width="450px" height="450px" />
 <ul> <c:forEach var="song" items="${albumDAO.getById(currAlbum).songs()}">

    <li> <audio src="data:audio/mpeg;base64,<c:out value="${song.songBase64()}"/>" controls></audio> </li>
 </c:forEach>
 </ul>
</body>
</html>
