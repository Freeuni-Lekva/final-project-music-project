
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Feed</title>
    <link rel="icon" href="/images/logo_small.png"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body>

<div class = "search-bar">
    <form action = "/searchResults" method = "post">
        <input type="text" id="search-bar" name="search-bar" class="searchbar">
        <input type="submit" value="search"  class="search_button">
    </form>
</div>


</body>
</html>
