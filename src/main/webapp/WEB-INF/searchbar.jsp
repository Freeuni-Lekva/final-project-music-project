<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>

<div class = "search_bar">
    <form action = "/searchResults" method = "post">
        <div class="search_field_wrapper">
            <select id="scope" name="scope" class="search_button">
                <option>Global</option>
                <option>Friends</option>
            </select>
            <select id="time" name="time" class="search_button">
                <option>All Time</option>
                <option>Last Week</option>
                <option>Last Month</option>
            </select>
            <input type="text" id="search-bar" name="search-bar" class="search_field">
            <select id="searchBy" name="searchBy" class="search_button">
                <option>Albums</option>
                <option>Users</option>
                <option>Reviews</option>
            </select>
            <input type="submit" value="search" class="search_button">
        </div>
    </form>
    <% if(request.getAttribute("filteredAlbums")!=null){
        request.getSession().setAttribute("listToSortAlbums", request.getAttribute("filteredAlbums"));%>
    <div class="sort_button_wrapper">
        <form action="/sortedResults" method="post">
            <select id="sortAlbumsBy" name="sortAlbumsBy" class="search_button">
                <option>Stars</option>
                <option>Newest</option>
            </select>
            <input type="submit" value="sort"  class="search_button">
        </form>
    </div>
    <% } else if(request.getAttribute("filteredReviews")!=null){
        request.getSession().setAttribute("listToSortReviews", request.getAttribute("filteredReviews"));%>
    <div class="sort_button_wrapper">
        <form  action="/sortedResults" method="post">
            <select id="sortReviewsBy" name="sortReviewsBy" class="search_button">
                <option>Upvotes</option>
                <option>Newest</option>
            </select>
            <input type="submit" value="sort" class="search_button">
        </form>
    </div>
    <% }%>
</div>

<p class="space"></p>

</body>
</html>
