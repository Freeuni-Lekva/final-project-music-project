<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>
<div class = "search-bar">
    <form action = "/searchResults" method = "post">
        <select id="scope" name="scope">
            <option>Friends</option>
            <option>Global</option>
        </select>
        <select id="time" name="time">
            <option>Last Week</option>
            <option>Last Month</option>
            <option>All Time</option>
        </select>
        <input type="text" id="search-bar" name="search-bar" class="searchbar">
        <select id="searchBy" name="searchBy">
            <option>Users</option>
            <option>Albums</option>
            <option>Reviews</option>
        </select>
        <input type="submit" value="search"  class="search_button">
    </form>
</div>
</body>
</html>
