<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
 <img src="${base64SrcPrefix}${albumDAO.getById(currAlbum).coverImageBase64()}"  alt="cover image" width="450px" height="450px" />
</body>
</html>
